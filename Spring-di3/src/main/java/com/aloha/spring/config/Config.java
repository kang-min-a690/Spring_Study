package com.aloha.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aloha.spring.dto.Board;

@Configuration 	// 스프링 빈을 등록 하는 설정 클래스로 지정
public class Config {
	// 설정 클래스를 통해서 빈을 등록하는 방법
	// 1. 백체를 반환하는 메소드 정의
	// 2. 메소드에 @Bean 어노테이션 정의
	
	
	@Bean		// IoC 컨테이너에 빈으로 등록
	public Board board() {
		return new Board("제목", "작성자", "내용");
	}

}
