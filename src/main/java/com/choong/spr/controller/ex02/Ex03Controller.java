package com.choong.spr.controller.ex02;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@RequestMapping("sub09")
	public String method09(@RequestParam String title, @RequestParam String writer) {
		System.out.println("받은 데이터 : ");
		System.out.println("title : " + title);
		System.out.println("write : " + writer);
		return null;
	}
	
	@PostMapping("sub10")
	public String method10(@RequestParam String name, @RequestParam String address) {
		System.out.println("받은 데이터 : ");
		System.out.println("name : " + name);
		System.out.println("address : " + address);
		return "good~!!!!!!!";
	}
	
	@PostMapping("sub11")
	public void method11(@ModelAttribute Book book) {
		System.out.println(book);
		System.out.println(book.getTitle());
	}
	
	@PostMapping("sub12")
	public String method12(){
		System.out.println("12번째 메소드 일함");
		return "hello";
	}
	
	@GetMapping("sub13")
	public Integer method13() {
		return (int)(Math.random() * 45 + 1);
	}
	
	@GetMapping("sub14")
	public Book method14() {
		Book b = new Book();
		b.setTitle("스프링");
		b.setWriter("홍길동");
		
		return b;
	}
	
	@GetMapping("sub15")
	public Map<String, String> method15(){
		Map<String, String> map = new HashMap<>();
		map.put("name", "손흥민");
		map.put("age", "30");
		map.put("address", " london");
		return map;
	}
}
