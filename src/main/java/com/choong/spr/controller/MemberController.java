package com.choong.spr.controller;

import java.lang.ProcessBuilder.Redirect;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
	public String getMember(String id, Model model, Principal principal, HttpServletRequest request) {
		if(hasAuthOrAdmin(id, principal, request)) {
			// 로그인 된 상태
			MemberDto member = service.getMemberById(id);
			model.addAttribute("member", member);
			return null; // view로 해석이됨!
		}
		// 로그인 안된 상태
		return "redirect:/board/list";
		
	}
	
	// 회원 정보 삭제
	@PostMapping("remove")
	public String removeMember(MemberDto dto, RedirectAttributes rttr, Principal principal, HttpServletRequest request) {
		if(hasAuthOrAdmin(dto.getId(), principal, request)) {
			boolean success = service.removeMember(dto);
			
			
			if(success) {
				rttr.addFlashAttribute("message", "회원 탈퇴 되었습니다.");
				return "redirect:/board/list";
			}else {
				rttr.addAttribute("id", dto.getId()); // addFlashAttribute는 model로 보내지고 addAttribute는 쿼리스트링으로 보내지기 때문
				return "redirect:/member/get";
			}
			
		}else {
			return "redirect:/board/list";
		}
		
	}
	
	// 회원정보 수정
	@PostMapping("modify")
	public String modifyMember(MemberDto dto, String oldPassword, RedirectAttributes rttr, Principal principal, HttpServletRequest request) {
		if(hasAuthOrAdmin(dto.getId(), principal, request)) {
			boolean success = service.modifyMember(dto, oldPassword);
			if(success) {
				rttr.addFlashAttribute("message", "회원정보 수정 완료");
			}else {
				rttr.addFlashAttribute("message", "회원정보 수정되지 않았습니다.");
			}
			//rttr.addFlashAttribute("member", dto); // 모델에 붙고
			rttr.addAttribute("id", dto.getId()); // 쿼리 스트링에 붙고
			return "redirect:/member/get";
			
		}else {
			return "redirect:/board/list";
		}
		
	}
	
	// 로그인 페이지로 가기
	@GetMapping("login")
	public void loginPage() {
		
	}
	@PostMapping("login")
	public void loginProcess() {
		System.out.println("프로세스 ");
	}
	// 로그인 아이디 + 요청받은 아이디  + 요청받은 권한이 모두 같은지 알아보는 메소드,
	private boolean hasAuthOrAdmin(String id, Principal principal, HttpServletRequest request) {
		return request.isUserInRole("ROLE_ADMIN") || (principal != null && principal.getName().equals(id));
	}
}
