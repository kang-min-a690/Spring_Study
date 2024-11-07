package com.aloha.spring.Service;

import org.springframework.stereotype.Service;

@Service 		// 서비스 빈으로 등록 , 
				// ("빈이름") 등록 안하면 클래스 이름(AServiceImpl)으로 등록됨
public class AServiceImpl implements MyService {

	@Override
	public void test() {
		System.out.println("AServiceImpl입니다.");
	}
	
	

}
