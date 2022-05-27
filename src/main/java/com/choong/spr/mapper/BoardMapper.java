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
	void deleteBoardByMember(String id);
	
	// 검색
	//List<BoardDto> listBoardByKeyword(String keyword);

}
