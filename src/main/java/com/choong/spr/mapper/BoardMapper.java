package com.choong.spr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.choong.spr.domain.BoardDto;

public interface BoardMapper {

	List<BoardDto> selectBoardAll(@Param("type") String type, @Param("keyword") String keyword);
	
	int insertBoard(BoardDto board);

	BoardDto selectBoardById(int id);

	int updateBoard(BoardDto dto);

	int deleteBoard(int id);
	
	// 회원 관련 게시글 삭제
	void deleteBoardByMemberId(String memberId);
	
	
	// 게시물관련 번호
	List<BoardDto> listByMemberId(String memberId);

	// 파일 등록
	void insertFile(@Param("boardId")int boardId, @Param("fileName")String fileName);
	// 파일목록 읽기
	String selectFileByBoardId(int boardId);
	// 파일테이블 삭제
	void deleteFileByBoardId(int boardId);

	
	// 검색
	//List<BoardDto> listBoardByKeyword(String keyword);

}
