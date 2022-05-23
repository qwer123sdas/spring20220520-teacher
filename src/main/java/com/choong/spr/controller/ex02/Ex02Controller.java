package com.choong.spr.controller.ex02;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.choong.spr.domain.ex02.Book;

@Controller
@RequestMapping("ex02")
public class Ex02Controller {
	@RequestMapping("sub01")
	public String method01() {
		return "hello"; // view의 jsp인 XML이 아니라, data자체인 String인 JSON으로 보내고자한다.
	}
	
	@RequestMapping("sub02")
	@ResponseBody // 리턴 값을 데이터 자체로 판단하여 보낸다.
	public String method02() {
		return "hello";
	}
	
	
	@RequestMapping("sub03")
	@ResponseBody
	public String method03() {
		return "{ \"title\": \"java\", \"writer\": \"son\"}";
		
	}
	
	@RequestMapping("sub04")
	@ResponseBody
	public Book method04() {
		Book b = new Book();
		b.setTitle("spring");
		b.setWriter("son");
		return b;
	}
	
	@RequestMapping("sub05")
	public String method05() {
		return "/ex03/sub01";
	}
	
}
