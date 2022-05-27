package com.choong.spr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.choong.spr.domain.MemberDto;

public interface MemberMapper {
	// 회원가입
	int insertMember(MemberDto member);
	// 아이디 중복여부
	int countMemberId(String id);
	// 이메일 중복여부
	int countMemberEmail(String email);
	// 닉네임 중복여부
	int countMemberNickName(String nickName);
	//회원들 정보 가져오기
	List<MemberDto> listMember();
	// 특정 회원정보 
	MemberDto selectMemberById(String id);
	
	// 회원정보 삭제
	int deleteAuthByMemberId(String id);
	
	int deleteMemberById(String id);
	
	// 회원정보 수정
	int updateMember(MemberDto dto);
	
	// 회원권한 주기
	int insertAuth(@Param("id") String id, @Param("auth")String auth);
}
