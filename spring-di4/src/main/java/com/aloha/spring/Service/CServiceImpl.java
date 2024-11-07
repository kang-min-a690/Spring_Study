package com.aloha.spring.Service;

import org.springframework.stereotype.Service;

//빈등록 어노테이션 ("빈이름")
//* 빈이름 지정하지 않으면 클래스 이름을 기본으로 지정
//* "빈 이름"을 지정 하면 , @Qualifier("지정한 빈 이름") 으로 주입 할 수 있다.
@Service("C") 		// 서비스 빈으로 등록 , 
public class CServiceImpl implements MyService {

	@Override
	public void test() {
		System.out.println("CServiceImpl입니다.");
	}
	
	
}
