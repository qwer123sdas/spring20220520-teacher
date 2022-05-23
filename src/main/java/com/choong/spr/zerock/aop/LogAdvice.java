package com.choong.spr.zerock.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

@Aspect // 해당 클래스가  Aspect를 구현함을 나타냄
@Log4j 
@Component // 스프링에서 빈으로 인식하기위해 사용
public class LogAdvice {
	@Before( "execution(* org.zerock.service.SampleService*.*(..))") // Point Cut으로 ( )안은 AspectJ의 표현식임. excution은 접근제한자와 특수정 클래스의 메서드를 지정할 수 있
	public void logBefore() {                                       // 맨앞의 *은 접근제한자를 의미, 맨두의 *은 클래스의 이름과 메서드의 이름을 의미함.
		log.info("======================");
	}
}
