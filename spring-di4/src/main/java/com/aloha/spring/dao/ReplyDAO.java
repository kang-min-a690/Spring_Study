package com.aloha.spring.dao;

import org.springframework.stereotype.Repository;
	
@Repository		//DAO 역할로 빈으로 등록
public class ReplyDAO extends BoardDAO{ //상속 extends BoardDAO
	
	public void test() {
		System.out.println("ReplyDAO입니다.");
	}

}
