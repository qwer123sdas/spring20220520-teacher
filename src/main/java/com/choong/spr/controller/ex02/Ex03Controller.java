package com.choong.spr.controller.ex02;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.choong.spr.domain.ex02.Book;

/*@Controller
@ResponseBody*/
@RequestMapping("ex03")
@RestController
public class Ex03Controller {
	
	@RequestMapping("sub01")
	public String method01() {
		return "string data";
	}
	
	@RequestMapping("sub02")
	public Book method02() {
		Book b = new Book();
		b.setTitle("soccer");
		b.setWriter("jimin");
		return b;
	}
	
	@RequestMapping("sub03")
	public String method03() {
		System.out.println("ex03/sub03 일함");
		return "hello ajax";
	}
	
	@RequestMapping("sub04")
	public String method04() {
		System.out.println("ex03/sub04가 일함");
		return "hello ajax";
	}
	
	@RequestMapping("sub05")
	public String method05() {
		System.out.println("ex03/sub05가 일함");
		return "hello ajax";
	}
	
	@PostMapping("sub06")
	public String method06() {
		System.out.println("ex03/sub06@@@@@@@");
		return "hello";
	}
	
	//@RequestMapping(method = RequestMethod.DELETE, value="sub07")
	@DeleteMapping("sub07")
	public String method07() {
		System.out.println("ex03/sub07!!!! delete");
		return "hello";
	}
	
	//@RequestMapping(method = RequestMethod.PUT, value = "sub08")
	
	@PutMapping("sub08")
	public String method08() {
		System.out.println("ex03/sub08@#@#@#@# put");
		return "hello";
	}
}
