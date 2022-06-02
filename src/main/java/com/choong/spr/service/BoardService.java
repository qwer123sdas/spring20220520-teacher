package com.choong.spr.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.PostConstruct;

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
	
	public List<BoardDto> listBoard(String type, String keyword) {
		// TODO Auto-generated method stub
		return mapper.selectBoardAll(type, "%"+keyword+"%");
	}
	
	
	@Transactional
	public boolean insertBoard(BoardDto board, MultipartFile file) {
//		board.setInserted(LocalDateTime.now());
		
		// 게시글 등록
		int cnt = mapper.insertBoard(board);
		
		// 파일 등록
		if(file.getSize() > 0) {
			mapper.insertFile(board.getId(), file.getOriginalFilename());
			// saveFile(board.getId(), file); // 현재는 desk탑에 저장하지만 aws에 저장하도록 해야 함.
			saveFileAwsS3(board.getId(), file); // aws s3에 업로드(저장)
		}
		
		return cnt == 1;
	}
	
	
	// aws s3에 업로드(저장)
	private void saveFileAwsS3(int id, MultipartFile file) {
		// board/37/12344.png
		String key = "board/" + id + "/" + file.getOriginalFilename();
		
		PutObjectRequest putObjectRequest = PutObjectRequest.builder()
				.acl(ObjectCannedACL.PUBLIC_READ) 		 // acl : 권한 설정
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
	
	
	
	// desk탑에 업로드(저장)
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
		
	}

	public BoardDto getBoardById(int id) {
		// TODO Auto-generated method stub
		return mapper.selectBoardById(id);
	}

	public boolean updateBoard(BoardDto dto) {
		// TODO Auto-generated method stub
		return mapper.updateBoard(dto) == 1;
	}

	@Transactional
	public boolean deleteBoard(int id) {
		// 파일 목록 읽기(결과 : 파일 이름)
		String fileName = mapper.selectFileByBoardId(id);
		
		// 실제 파일 삭제
		if(fileName != null && !fileName.isEmpty()) {
			String folder = "C:/imgtmp/board/" + id + "/";
			String path = folder+ fileName;
			File file = new File(path);
			file.delete();
			
			// 파일 담고 있던 폴더 지우기
			File dirFolder= new File(folder);
			dirFolder.delete();
		}
		// 파일테이블 삭제
		mapper.deleteFileByBoardId(id);
		
		
		// 댓글 삭제
		replyMapper.deleteByBoardId(id);
		
		
		return mapper.deleteBoard(id) == 1;
	}
	// 검색기능
	/*	public List<BoardDto> searchBoard(String keyword) {
			                                 // 와일드 카드
			return mapper.listBoardByKeyword("%"+keyword+"%");
		}*/
}





