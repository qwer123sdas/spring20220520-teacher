package com.choong.spr.zerock.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

@Aspect // 해당 클래스가  Aspect를 구현함을 나타냄
@Component // 스프링에서 빈으로 인식하기위해 사용
public class LogAdvice {
	
	@Pointcut("execution(* com.choong.spr.service..*.*(..))")
	public void allPointCut() {}
	
	@Before("allPointCut()") // Point Cut으로 ( )안은 AspectJ의 표현식임. excution은 접근제한자와 특수정 클래스의 메서드를 지정할 수 있
	public void printLog() {                                       // 맨앞의 *은 접근제한자를 의미, 맨두의 *은 클래스의 이름과 메서드의 이름을 의미함.
		System.out.println("공통로그-LogAdvice, 비지니스 로직 수행전 동작");
	}
	@After("allPointCut()")
	public void printLogging(JoinPoint join) {
		System.out.println("공통로그-LogAdvice, 비지니스 로직 수행 후 동작");
	}
}
