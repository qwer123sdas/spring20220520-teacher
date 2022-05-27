package com.choong.spr.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.choong.spr.domain.ReplyDto;
import com.choong.spr.service.ReplyService;

@RestController
@RequestMapping("reply")
public class ReplyController {

	@Autowired
	private ReplyService service;

	@PostMapping(path = "insert", produces = "text/plain;charset=UTF-8")
	public ResponseEntity<String> insert(ReplyDto dto, Principal principal) { 
		
		if(principal != null ) {
			boolean success = service.insertReply(dto);
			
			if (success) {
				return ResponseEntity.ok("새댓글이 등록");
			} else{
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error");
			}
		}
		return ResponseEntity.status(401).body("삭제 못함");
	}

	@PutMapping(path = "modify", produces = "text/plain;charset=UTF-8")
	public ResponseEntity<String> modify(@RequestBody ReplyDto dto) {// put방식은 dto를 파라미터로 처리 못함
		boolean success = service.updateReply(dto);
		
		if (success) {
			return ResponseEntity.ok("댓글이 변경되었습니다.");
		}else {
			return ResponseEntity.status(500).body("댓글 변경 중 오류 발생");
		}

	}
	
	@DeleteMapping(path = "delete/{id}",  produces = "text/plain;charset=UTF-8")  // {id}는 pathVariable
	//@ResponseBody // ResponseEntity가 메소드 리턴값이면 생략 가능
	public ResponseEntity<String> delete(@PathVariable("id") int id) {
		boolean success = service.deleteReply(id);
		if(success) {
			return ResponseEntity.ok("댓글을 삭제 하였습니다.");
		}else {
			return ResponseEntity.status(500).body("");
		}
	}
	
	@GetMapping("list")
	public List<ReplyDto> list(int boardId){
		return service.getReplyByBoardId(boardId);
	}
}






