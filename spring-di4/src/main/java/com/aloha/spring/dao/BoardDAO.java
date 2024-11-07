package com.aloha.spring.dao;

import org.springframework.stereotype.Repository;

@Repository	//DAO 역할로 빈으로 등록
public class BoardDAO {

		//잘되는지 호출 확인하는거임
		public void test() {
			System.out.println("BoardDAO 입니다.");
		}
}
