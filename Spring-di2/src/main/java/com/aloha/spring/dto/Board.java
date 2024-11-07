package com.aloha.spring.dto;

public class Board {
	
	private String title;
	private String writer;
	private String comtent;
	
	
	
	// useing 없이
	public Board() {
	}


	// using 전부 체크
	public Board(String title, String writer, String comtent) {
		super();
		this.title = title;
		this.writer = writer;
		this.comtent = comtent;
	}


	// get set
	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getWriter() {
		return writer;
	}



	public void setWriter(String writer) {
		this.writer = writer;
	}



	public String getComtent() {
		return comtent;
	}



	public void setComtent(String comtent) {
		this.comtent = comtent;
	}


	//tostring
	@Override
	public String toString() {
		return "Board []";
	}
	
	
	




	
}
