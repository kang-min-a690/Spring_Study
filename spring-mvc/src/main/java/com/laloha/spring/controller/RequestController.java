package com.laloha.spring.controller;

import java.io.File;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.laloha.spring.dto.Board;
import com.laloha.spring.dto.User;
@Controller				// Controller 로 지정하고 빈 등록
@RequestMapping("/request")
public class RequestController {
	
	private static final Logger logger = LoggerFactory.getLogger(RequestController.class);
	
	

	/**
	 * @RequestMapping : 요청 경로 매핑
	 *  - /request/board 로 요청
	 *  - /request/board.jsp 응답
	 * @return
	 */
//	@RequestMapping(value = "/request/board", method = RequestMethod.GET)		
//	@RequestMapping("/request/board")		
	@RequestMapping("/board")		
	public String request() {
		logger.info("[GET] - /request/board");
		return "request/board";
	}
	
	/**
	 * 경로 패턴 매핑
	 * @param no
	 * @return
	 */
	@RequestMapping(value = "/board/{no}", method = RequestMethod.GET)
	public String requestPath(@PathVariable("no") int no) {
		logger.info("[GET] - /request/board/{no}");
		logger.info("no : " + no);
		
		return "request/board";
	}
	
	/**
	 * 요청 메소드 매핑
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/board", method = RequestMethod.POST)
	// public String requestPost(@RequestParam("no") int data) {
	public String requestPost(@RequestParam int no) {
		logger.info("[POST] - /request/post");
		logger.info("no : " + no);
		return "SUCCESS - no : " + no;
	}
	
	/**
	 * 파라미터 매핑
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/board", method = RequestMethod.GET,
				    params = "id")
	public String requestParams(@RequestParam String id) {
		logger.info("[GET] - /request/board?id=board_123");
		logger.info("id : " + id);
		
		return "request/board";
	}
	

	/**
	 * 헤더 매핑
	 * @return
	 */
	// headers = "헤더명=값"
	@ResponseBody
	@RequestMapping(value = "/board", method = RequestMethod.POST
			       ,headers = "Content-Type=application/json" )
	public String requestHeader() {
		logger.info("[POST] - /request/board");
		logger.info("헤더 매핑...");
		return "SUCCESS";
	}
	
	
	/*
	 	@ResponseBody O : return "데이터"; 	---> 응답메시지( 본문: 데이터 )
	 	@ResponseBody X : return "화면이름";	---> 뷰 리졸버가 jsp 선택->렌더링->html 응답
	*/
	@RequestMapping(value = "/board", method = RequestMethod.PUT)
	public String requestPut() {
		logger.info("[POST] (PUT) - /request/board");
		return "redirect:/";
	}
	
	
	/**
	 * 컨텐츠 타입 매핑
	 * - Content-Type 헤더의 값으로 매핑
	 * - consumes = "컨텐츠타입값"
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/board", method = RequestMethod.POST
			       ,consumes = "application/xml")
	public String requestContentType() {
		logger.info("[POST] - /board/request");
		logger.info("컨텐츠 타입 매핑...");
		return "SUCCESS";
	}
	
	
	/**
	 * Accept 매핑
	 * - Accept 헤더의 값으로 매핑 (Accept 헤더는 응답 답을 컨텐츠 타입을 지정)
	 * - produces = "컨텐츠타입값"
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/board", method = RequestMethod.POST
			       ,produces = "application/json")
	public String requestAccept() {
		logger.info("[POST] - /board/request");
		logger.info("Accept 매핑...");
		return "SUCCESS";
	}

	
	
	/* ---------------------- [요청 경로 매핑 ] ------------------------- */
	/* --------------------------------------------------------------- */
	/* ---------------------- [요청 처리 ] ----------------------------- */
	/**
	 * 요청 헤더를 가져오기
	 * @param accept
	 * @param userAgent
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/header", method = RequestMethod.GET)
	public String header(@RequestHeader("Accept") String accept
			            ,@RequestHeader("User-Agent") String userAgent
			            ,HttpServletRequest request) {
		//@RequestHeader 를 통한 헤더정보 가져오기
		logger.info("[GET] - /request/header");
		logger.info("@RequestHeader 를 통한 헤더 정보 가져오기");
		logger.info("Accept - " + accept);
		logger.info("User-Agent - " + userAgent);
							
		// request 객체로 부터 헤더 가져오기
		String requestAccept = request.getHeader("Accept");
		String requestUserAgent = request.getHeader("User-Agent");
		logger.info("HttpServletRequest 을 통한 헤더 정보 가져오기");
		logger.info("Accept - " + requestAccept);
		logger.info("User-Agent - " + requestUserAgent); //요청을 보내는 사용자(클라이언트)의 정보를 담고있는 헤더
							
		return "SUCCESS";
							
	}
	
	
	//요청 본문 가져오기
	/**
	 * 요청 본문 가져오기
	 * @param board
	 * @return
	 * @ResponseBody : HTTP 요청 메시지의 본문 (body) 내용을 객체로 변환하는 어노테이션
	 * 					클라이언트에서 json 형식으로 보낸 데이터를 객체로 변환하기 위해 사용한다.
	 * 					* 생략 가능(주로 생략 가능)
	 * 
	 * 	415 에러 - 지원되지 않는 미디어 타입
	 *  (Unsupported Media Type)
	 *  : 클라이언트가 보낸 컨테츠 타입의 요청을 서버가 처리할수 없을 때 발생하는 에러
	 *  form으로 보내는[클라이언트] 를 (application/x-www-form-urlencoded)로 보냄
	 *					↓  
	 *				[  서버  ] ( application/json)
	 *	@ResponseBody를 쓰면 , 본문의 컨텐츠 타입을 application/json을 기본으로 지정
	 *
	 *	* 비동기 또는 thunder client 로 테스트 가능
	 *	Content-type : application/json
	 *	body {  "title" : "제목", "writer" : "작성자" , "content" : "내용"}
	 */
	@ResponseBody
	@RequestMapping(value = "/body", method = RequestMethod.POST)
	public String requestBody(@RequestBody Board board) {
		logger.info("[POST] - /request/body");
		logger.info(board.toString());
		
		return "SUCCESS";
	}
	
	/**
	 * 체크 박스 데이터 가져오기
	 * @param hobbies
	 * @return
	 *  체크박스 다중 데이터는 배열로 전달 받을 수 있다.
	 *  같은 이름의 요청 파라미터(name) 들은 배열 또는 리스트로 전달 받을수 있다.
	 */
	@ResponseBody
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String requestCheck(@RequestParam("hobby") String[] hobbies) {
		logger.info("[POST] - /request/check");
		
		for (String hobby : hobbies) {
			logger.info("hobby : " + hobby);
		}
		
		return "SUCCESS";
	}
	
	//date 데이터 가져오기
	/**
	 * date 형식, 여러 요청 정보 가져오기
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	//생일 입력방식 3개
//	public String requestUser(String birth) { 
//	public String requestUser(@DateTimeFormat(pattern="yyyy-MM-dd") Date birth) {
	public String requestUser(User user) {
		
		// 체크박스 다중 데이터는 배열로 전달 받을 수 있다.
		logger.info("[POST] - /request/user");
		
//		 logger.info("birth : " + birth);	// 2024/11/07로 콘솔 표시
		logger.info("user - " + user);
		
		
		return "SUCCESS";
	}
	
	
	/**
	 * //Map 컬렉션 요청 파라미터 가져오기
	 * 요청 경로 : /request/map?name=김조은&age=20
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/map")
	public String requestMap(@RequestParam Map<String, String> map) {
			String name = map.get("name");
			String age = map.get("age");
			
			logger.info("name : " + name);
			logger.info("age : " + age);
			return "SUCCESS";
	}
	
	
	//업로드 경로
	@Autowired
//	@Qualifier("uploadPath")
	@Resource(name="uploadPath") // name속성 필수
	private String uploadPath;
	
	
	//파일 업로드
	@ResponseBody
	@RequestMapping(value = "/file", method = RequestMethod.POST)
	public String fileUpload(MultipartFile file) throws Exception {
		logger.info("/repuest/file");
		logger.info("uploadPath :" + uploadPath);
		
		if( file == null ) return "FAIL";
		
		logger.info("originalFileName : " + file.getOriginalFilename());
		logger.info("Size : " + file.getSize());
		logger.info("contentType : " + file.getContentType());
		
		//파일 데이터
		byte[] fileData = file.getBytes();
		
		//파일 업로드
		String filePath = uploadPath;
		String fileName = file.getOriginalFilename();
		File uploadFile = new File(filePath, fileName);
		
		FileCopyUtils.copy(fileData, uploadFile);	//파일 업로드
		// FileCopyUtils.copy(파일 데이터, 파일 객체)
		// : 내부적으로 InputStream, OutputStream 을 이용하여 입력받은 파일을 출력한다.
		
		return "SUCCESS - uploadPath : " + uploadPath;
		
	}
	
	// 다중 파일 업로드
		@ResponseBody
		@RequestMapping(value = "/file/multi", method = RequestMethod.POST)
		public String fileUpload(@RequestParam("file") MultipartFile[] fileList) throws Exception {
			logger.info("/requet/file/multi");
			logger.info("uploadPath : " + uploadPath);
			
			if( fileList == null ) return "FAIL";
			
			if( fileList.length > 0 ) {
				for (MultipartFile file : fileList) {
					logger.info("originalFileName : " + file.getOriginalFilename());
					logger.info("size : " + file.getSize());
					logger.info("contentType : " + file.getContentType());
					
					// 파일 데이터
					byte[] fileData = file.getBytes();
					
					// 파일 업로드
					String filePath = uploadPath;
					String fileName = file.getOriginalFilename();
					File uploadFile = new File(filePath, fileName);
					
					FileCopyUtils.copy(fileData, uploadFile); // 파일 업로드(저장)
					// FileCopyUtils.copy(파일 데이터, 파일 객체)
					// : 내부적으로 InputStream, OutputStream 을 이용하여 입력받은 파일을 출력한다.
				}
			}
			return "SUCCESS - uploadPath : " + uploadPath;
		}
		
	
	//데이터 등록 + 파일 업로드(다중)
	@ResponseBody
	@RequestMapping(value = "/file/board", method = RequestMethod.POST)
	public String fileUpload(Board board) throws Exception {
		logger.info("/requet/file/board");
		logger.info("uploadPath : " + uploadPath);
		logger.info("board : " + board);
		
		// MultipartFile[] fileList = board.getFileList(); 배열일때
		List<MultipartFile>fileList = board.getFileList();
		if( fileList == null ) return "FAIL";
		
//		if( fileList.length > 0 ) 배열일때
		if( !fileList.isEmpty() ) {
			for (MultipartFile file : fileList) {
				logger.info("originalFileName : " + file.getOriginalFilename());
				logger.info("size : " + file.getSize());
				logger.info("contentType : " + file.getContentType());
				
				// 파일 데이터
				byte[] fileData = file.getBytes();
				
				// 파일 업로드
				String filePath = uploadPath;
				String fileName = file.getOriginalFilename();
				File uploadFile = new File(filePath, fileName);
				
				FileCopyUtils.copy(fileData, uploadFile); // 파일 업로드(저장)
				// FileCopyUtils.copy(파일 데이터, 파일 객체)
				// : 내부적으로 InputStream, OutputStream 을 이용하여 입력받은 파일을 출력한다.
			}
		}
		return "SUCCESS";
	}
		
		
	
	//ajax 비동기 파일 업로드
	/**
	 * 비동기 파일 업로드 화면
	 * @return
	 */
	@RequestMapping("/ajax")
	public String ajax() {
		return "request/ajax";
	}
	
	/**
	 * AJAX 비동기 파일 업로드
	 * @param board
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/ajax", method = RequestMethod.POST)
	public String fileUploadPost(Board board) throws Exception {
		logger.info("/requet/ajax");
		logger.info("uploadPath : " + uploadPath);
		logger.info("board : " + board);
		
		// MultipartFile[] fileList = board.getFileList(); 배열일때
		List<MultipartFile>fileList = board.getFileList();
		if( fileList == null ) return "FAIL";
		
//		if( fileList.length > 0 ) 배열일때
		if( !fileList.isEmpty() ) {
			for (MultipartFile file : fileList) {
				logger.info("originalFileName : " + file.getOriginalFilename());
				logger.info("size : " + file.getSize());
				logger.info("contentType : " + file.getContentType());
				
				// 파일 데이터
				byte[] fileData = file.getBytes();
				
				// 파일 업로드
				String filePath = uploadPath;
				String fileName = file.getOriginalFilename();
				File uploadFile = new File(filePath, fileName);
				
				FileCopyUtils.copy(fileData, uploadFile); // 파일 업로드(저장)
				// FileCopyUtils.copy(파일 데이터, 파일 객체)
				// : 내부적으로 InputStream, OutputStream 을 이용하여 입력받은 파일을 출력한다.
			}
		}
		return "SUCCESS";
	}
	
	
	
}