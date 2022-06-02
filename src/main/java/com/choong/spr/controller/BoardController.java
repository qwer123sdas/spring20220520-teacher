package com.choong.spr.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.choong.spr.domain.BoardDto;
import com.choong.spr.domain.ReplyDto;
import com.choong.spr.service.BoardService;
import com.choong.spr.service.ReplyService;

@Controller
@RequestMapping("board")
public class BoardController {
	
	@Autowired
	private BoardService service;
	
	@Autowired
	private ReplyService replyService;

	@RequestMapping("list")
	public void list(@RequestParam(name="keyword", defaultValue = "")String keyword, 
			@RequestParam(name="type", defaultValue = "") String type,
			Model model) {
		List<BoardDto> list = service.listBoard(type, keyword);
		model.addAttribute("boardList", list);
	}
	// 검색기능
	/*	@RequestMapping(path = "list", params = "keyword")
		public void search(String keyword, Model model) {
			List<BoardDto> list = service.searchBoard(keyword);
			model.addAttribute("boardList", list);
		}*/
	
	@GetMapping("insert")
	public void insert() {
		
	}
	
	@PostMapping("insert")
	public String insert(BoardDto board,
						MultipartFile[] files, 
						Principal principal, 
						RedirectAttributes rttr) { // Principal : ???에 넘겨준 username, password, role에 대한 정보가 담겨져 있다
		//System.out.println(principal);
		//System.out.println(principal.getName()); // username
		board.setMemberId(principal.getName());
		
		// 한개 파일 올리기
		/*
		if(file.getSize() > 0) {
			board.setFileName(file.getOriginalFilename());
		}*/
		if(files != null) {
			List<String> fileList = new ArrayList<>();
			for(MultipartFile file : files) {
				fileList.add(file.getOriginalFilename());
			}
			board.setFileName(fileList);
		}
		
		boolean success = service.insertBoard(board, files);
		
		
		if (success) {
			rttr.addFlashAttribute("message", "새 글이 등록되었습니다.");
		} else {
			rttr.addFlashAttribute("message", "새 글이 등록되지 않았습니다.");
		}
		
		return "redirect:/board/list";
	}
	
	@GetMapping("get")
	public void get(int id, Model model) {
		BoardDto dto = service.getBoardById(id);
		List<ReplyDto> replyList = replyService.getReplyByBoardId(id);
		model.addAttribute("board", dto);
		
		// ajax처리하기 위해 밑의 코드는 삭제함
		//model.addAttribute("replyList", replyList);
		
	}
	
	@PostMapping("modify")
	public String modify(BoardDto dto, Principal principal, RedirectAttributes rttr) {
		BoardDto oldBoard = service.getBoardById(dto.getId());
		
		if(oldBoard.getMemberId().equals(principal.getName())) {
			
			boolean success = service.updateBoard(dto);
			
			if (success) {
				rttr.addFlashAttribute("message", "글이 수정되었습니다.");
			} else {
				rttr.addFlashAttribute("message", "글이 수정되지 않았습니다.");
			}
		}else {
			rttr.addFlashAttribute("message", "수정권한이 없습니다.");
		}
		
		rttr.addAttribute("id", dto.getId());
		
		return "redirect:/board/get";
	}
	
	@PostMapping("remove")
	public String remove(BoardDto dto, Principal principal, RedirectAttributes rttr) {
		// 게시물 정보 얻고
		BoardDto oldBoard = service.getBoardById(dto.getId());
		// 게시물 작성자(memberId)와 principal의 name과 비교해서 같을 때만 진행
		if(oldBoard.getMemberId().equals(principal.getName())){
			boolean success = service.deleteBoard(dto.getId());
			
			if (success) {
				rttr.addFlashAttribute("message", "글이 삭제 되었습니다.");
				
			} else {
				rttr.addFlashAttribute("message", "글이 삭제 되지않았습니다.");
			}
			
		}else {
			rttr.addFlashAttribute("message", "권한이 없습니다.");
			return "redirect:/board/get";
		}
		// 아니면 바로 리턴 시키기
		return "redirect:/board/list";
	}
}










