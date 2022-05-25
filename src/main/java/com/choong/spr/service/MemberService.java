package com.choong.spr.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.choong.spr.domain.MemberDto;
import com.choong.spr.mapper.MemberMapper;
@Service
public class MemberService {
	@Autowired
	MemberMapper mapper;
	
	// 회원가입
	public boolean addMember(MemberDto member) {
		return mapper.insertMember(member) == 1;
	}
	// 아이디 중복여부
	public boolean hasMemberId(String id) {
		return mapper.countMemberId(id) > 0;
	}
	// 이메일 중복 여부
	public boolean hasMemberEmail(String email) {
		return mapper.countMemberEmail(email) > 0;
	}
	// 닉네임 중복 여부
	public boolean hasMemberNickName(String nickName) {
		return mapper.countMemberNickName(nickName) > 0;
	}
	// 회원정보 가오기
	public List<MemberDto> listMember() {
		return mapper.listMember();
	}
	// 특정 회원정보
	public MemberDto getMemberById(String id) {
		return mapper.selectMemberById(id);
	}
	// 회원정보 삭제
	public boolean removeMember(MemberDto dto) {
		MemberDto member = mapper.selectMemberById(dto.getId());
		if(member.getPassword().equals(dto.getPassword())) {
			return mapper.deleteMemberById(dto.getId()) == 1;
		}
		
		return false;
	}

}
