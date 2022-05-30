package com.choong.spr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.choong.spr.domain.ReplyDto;

public interface ReplyMapper {

	int insertReply(ReplyDto dto);

	List<ReplyDto> selectAllBoardId(@Param("boardId")int boardId, @Param("memberId")String memberId);

	int updateReply(ReplyDto dto);

	int deleteReply(int id);

	void deleteReplyByBoardId(int boardId);

	// 아이디로 유저이름 가져오기
	ReplyDto selectReplyById(int id);
	
	// 멤버 아이디와 관련된 댓글 지우기
	void deleteReplyByMemberId(String memberId);

	void deleteByBoardId(int id);

}
