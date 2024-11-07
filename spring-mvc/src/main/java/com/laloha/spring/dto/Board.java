package com.laloha.spring.dto;

import java.sql.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class Board {
	
	private int no;
	private String title;
	private String writer;
	private String content;
	private Date createdAT;
	private Date updatedAT;
	
	//파일 정보
//	MultipartFile[] fileList;
	List<MultipartFile> fileList; // board.jsp의 name과 같아야 함
	
	public Board() {

	}


	public Board(String title, String writer, String content) {
		super();
		this.title = title;
		this.writer = writer;
		this.content = content;
	}
	
	
}
