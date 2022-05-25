package com.choong.spr.controller;

import java.lang.ProcessBuilder.Redirect;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.choong.spr.domain.MemberDto;
import com.choong.spr.service.MemberService;

@Controller
@RequestMapping("member")
public class MemberController {
	
	@Autowired
	private MemberService service;
	
	@GetMapping("signup")
	public void signupForm() {
		
	}
	
	@PostMapping("signup")
	public String signupProcess(MemberDto member, RedirectAttributes rttr) {
		boolean success = service.addMember(member);
		if(success) {
			rttr.addFlashAttribute("message", "회원가입이 완료되었습니다.");
			return "redirect:/board/list";
		}else {
			rttr.addFlashAttribute("message", "회원가입을 실패했습니다.");
			rttr.addFlashAttribute("member", member);
			return "redirect:/member/signup";
		}
	}
	
	@GetMapping(path="check", params ="id")
	@ResponseBody
	public String idCheck(String id) {
		boolean exist  = service.hasMemberId(id);
		if(exist) {
			return "notOk";
		}else {
			return "ok";
		}
	}
	
	@GetMapping(path="check", params ="email")
	@ResponseBody
	public String emailCheck(String email) {
		boolean exist  = service.hasMemberEmail(email);
		if(exist) {
			return "notOk";
		}else {
			return "ok";
		}
	}
	
	@GetMapping(path="check", params = "nickName")
	@ResponseBody
	public String nickNameCheck(String nickName) {
		boolean result = service.hasMemberNickName(nickName);
		if(result) {
			return "notOk";
		}else {
			return "ok";
		}
	}
	
	// 회원정보 가져오기
	@GetMapping("list")
	public void list(Model model) {
		List<MemberDto> list =service.listMember();
		model.addAttribute("memberList", list);
	}
	
	// 특정 회원 정보
	@GetMapping("get")
	public void getMember(String id, Model model) {
		MemberDto member = service.getMemberById(id);
		model.addAttribute("member", member);
	}
	
	// 회원 정보 삭제
	@PostMapping("remove")
	public String removeMember(MemberDto dto, RedirectAttributes rttr) {
		boolean success = service.removeMember(dto);
		
		if(success) {
			rttr.addFlashAttribute("message", "회원 탈퇴 되었습니다.");
			return "redirect:/board/list";
		}else {
			rttr.addAttribute("id", dto.getId()); // addFlashAttribute는 model로 보내지고 addAttribute는 쿼리스트링으로 보내지기 때문
			return "redirect:/member/get";
		}
	}
}
