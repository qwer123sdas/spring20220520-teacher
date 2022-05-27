package com.choong.spr.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.choong.spr.domain.MemberDto;
import com.choong.spr.mapper.BoardMapper;
import com.choong.spr.mapper.MemberMapper;
@Service
public class MemberService {
	@Autowired
	MemberMapper mapper;
	
	@Autowired
	BoardMapper boardMapper;
	
	@Autowired  // 비밀번호 암호화(security-context.xml에서 bean 지정)
	private BCryptPasswordEncoder passwordEncoder;
	
	// 회원가입
	public boolean addMember(MemberDto member) {
		
		// 평문 암호를 암호화(encoding)
		String encodePassword = passwordEncoder.encode(member.getPassword());
		// 암호화된 패스워드를 다시 셋팅
		member.setPassword(encodePassword);
		// insert member
		int cnt1 = mapper.insertMember(member);
		// add auth(권한 주기)
		int cnt2 = mapper.insertAuth(member.getId(), "ROLE_USER");  //'ROLE_'를 통해 보안~~에 줌~~ 
		
		return cnt1 == 1 && cnt2 == 1;
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
	@Transactional
	public boolean removeMember(MemberDto dto) {
		MemberDto member = mapper.selectMemberById(dto.getId());
		
		String rawPW = dto.getPassword();
		String encodePW = member.getPassword();
		
		if(passwordEncoder.matches(rawPW, encodePW)) {
			
			// 회원 관련 게시글 삭제
			boardMapper.deleteBoardByMember(dto.getId());
			// 회원 관련 권한 삭제
			int authDelete = mapper.deleteAuthByMemberId(dto.getId());
			
			int memberDelete = mapper.deleteMemberById(dto.getId());
			return  memberDelete == 1 && authDelete == 1;
		}
		
		return false;
	}
	
	// 회원정보 수정
	public boolean modifyMember(MemberDto dto, String oldPassword) {
		// db에서 member읽어서 
		MemberDto oldMember = mapper.selectMemberById(dto.getId());
		
		String encodePW = oldMember.getPassword();
		
		// 기존 password가 일치할 때만 계속 진행
		if(passwordEncoder.matches(oldPassword, encodePW)) {
			// 암호 인코딩 저장
			dto.setPassword(passwordEncoder.encode(dto.getPassword()));
			
			return mapper.updateMember(dto) == 1;
		}
		return false;
	}

}
