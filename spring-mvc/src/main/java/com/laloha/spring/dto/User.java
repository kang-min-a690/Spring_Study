package com.laloha.spring.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class User {
	
	private String id;
	private String name;
	private String[] hobby;
	
	@DateTimeFormat(pattern="yyy-MM-dd") //하이픈 아니라 다른거 쓸거면 데이터 타입 바꿔야함
	private Date birth;
	

}
