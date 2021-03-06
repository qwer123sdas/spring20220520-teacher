package com.choong.spr.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.choong.spr.domain.BoardDto;
import com.choong.spr.mapper.BoardMapper;
import com.choong.spr.mapper.ReplyMapper;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
@Service
public class BoardService {

	@Autowired
	private BoardMapper mapper;
	
	@Autowired
	private ReplyMapper replyMapper;
	
	@Value("${aws.s3.bucketName}")
	private String bucketName;
	
	private S3Client s3; 
	
	
	
	@PostConstruct
	public void init() {
		Region region = Region.AP_NORTHEAST_2;
		this.s3 = S3Client.builder()
							.region(region)
							.build();
	}
	
	@PreDestroy // 빈이 사라지기 전
	public void destroy() {
		this.s3.close();
	}
	
	// 검색
	public List<BoardDto> listBoard(String type, String keyword) {
		return mapper.selectBoardAll(type, "%"+keyword+"%");
	}
	
	
	@Transactional
	public boolean insertBoard(BoardDto board, MultipartFile[] files) {
		//board.setInserted(LocalDateTime.now());
		
		// 게시글 등록
		int cnt = mapper.insertBoard(board);
		
		// 여러 파일 등록
		addFiles(board.getId(), files);
		
		return cnt == 1;
	}
	
	
	// 파일 추가 메소드
	private void addFiles(int id, MultipartFile[] files) {
		if(files != null) {
			for(MultipartFile file : files) {
				if(file.getSize() > 0) {
					mapper.insertFile(id, file.getOriginalFilename()); // db에 저장
					// saveFile(board.getId(), file); // 현재는 desk탑에 저장하지만 aws에 저장하도록 해야 함.
					saveFileAwsS3(id, file); // aws s3에 업로드(저장)
				}
			}
		}
	}

	// 특정 1가지 게시물 데이터 가져오기
	public BoardDto getBoardById(int id) {
		BoardDto board = mapper.selectBoardById(id);
		
		// 여러가지 업로드된 데이터 가져오기
		List<String> fileNames = mapper.selectFileNameByBoard(id);
		board.setFileName(fileNames);
		
		return board;
	}
	
	
	// 게시글 수정
	@Transactional
	public boolean updateBoard(BoardDto dto, 
								MultipartFile[] addFileList, 
								List<String> removeFileList) {
		// File테이블에 있는 것 삭제
		if(removeFileList != null) {
			// aws의 s3에서 파일 삭제 + 파일테이블 삭제
			for(String fileName : removeFileList) {
				deleteFromAwsS3(dto.getId(), fileName);
				mapper.deleteFileByBoardAndFileName(dto.getId(), fileName);
			}
		}
			

		
		// File 테이블에 추가된 파일 insert하기 + s3에  upload하기
		if(addFileList != null) {
			// File 테이블에 추가된 파일 insert
			// s3에 upload
			addFiles(dto.getId(), addFileList);
		}
		
		
		// Board 테이블 update
		int cnt = mapper.updateBoard(dto);
		return cnt == 1;
	}

	@Transactional
	public boolean deleteBoard(int id) {
		// 파일 목록 읽기(결과 : 파일 이름)
		List<String> fileList = mapper.selectFileNameByBoard(id);
		
		/* 실제 파일 삭제
		if(fileName != null && !fileName.isEmpty()) {
			String folder = "C:/imgtmp/board/" + id + "/";
			String path = folder+ fileName;
			File file = new File(path);
			file.delete();
			
			// 파일 담고 있던 폴더 지우기
			File dirFolder= new File(folder);
			dirFolder.delete();
		}*/
		
		// aws의 s3에서 파일 삭제
		removeFiles(id, fileList);
		
		
		// 댓글 삭제
		replyMapper.deleteByBoardId(id);
		
		
		return mapper.deleteBoard(id) == 1;
	}
	
	
	
	

	
	
	

	// aws s3에 업로드(저장)
	private void saveFileAwsS3(int id, MultipartFile file) {
		// board/37/12344.png
		String key = "board/" + id + "/" + file.getOriginalFilename();
		
		PutObjectRequest putObjectRequest = PutObjectRequest.builder()
				.acl(ObjectCannedACL.PUBLIC_READ) 		 // acl : 다른사람 접근권한 설정
				.bucket(bucketName) 					// bucket 위치 설정
				.key(key)								// 키
				.build(); 								 // 이를 통해 PutObjectRequest객체 만듬
		
		RequestBody requestBody;
		try {
			requestBody = RequestBody.fromInputStream(file.getInputStream(), file.getSize());
			s3.putObject(putObjectRequest, requestBody);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e); // 트랜잭션 때문에 모두 실패????????????
		}
		
	}
	
	// aws의 s3에서 파일 삭제 + 파일테이블 삭제 메소드--------------------------
	private void removeFiles(int id, List<String> fileList) {
		for(String fileName : fileList) {
			deleteFromAwsS3(id, fileName);
		}
		
		
		// 파일테이블 삭제
		mapper.deleteFileByBoardId(id);
	}
	// ++ aws의 s3에서 파일 삭제 메소드
	private void deleteFromAwsS3(int id, String fileName) {
		String key = "board/" + id + "/" + fileName;
		
		DeleteObjectRequest deleteBucketRequest;
		deleteBucketRequest = DeleteObjectRequest.builder()
				.bucket(bucketName)
				.key(key)
				.build();
		
		s3.deleteObject(deleteBucketRequest);
		
	}
	
	/* desk탑에 업로드(저장)
	private void saveFile(int id, MultipartFile file) {
		String pathStr = "C:/imgtmp/board/" + id + "/"; // 새로운 폴더 만드는 코드
		File path = new File(pathStr);
		path.mkdirs(); // 새로운 파일을 저장경로에 만듬
		
		// 작성할 파일
		File des = new File(pathStr + file.getOriginalFilename()); //  getOriginalFilename는 contain path information depending on the browser used.
		                                                          // 즉, 크롬 ^ 익스플로러 등에 따라 다르게 설정을 해주어야 함.
		try {
			// 실제로 파일 저장하는 코드
			file.transferTo(des);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}*/
	

	
	// 검색기능
	/*	public List<BoardDto> searchBoard(String keyword) {
			                                 // 와일드 카드
			return mapper.listBoardByKeyword("%"+keyword+"%");
		}*/
}
	
	
	
	





