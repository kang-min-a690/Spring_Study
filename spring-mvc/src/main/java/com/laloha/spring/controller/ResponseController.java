package com.laloha.spring.controller;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.laloha.spring.dto.Board;

/**
 * 컨트롤러 응답
 *
 */

@Controller
@RequestMapping("/response")
public class ResponseController {
	
	private static final Logger logger =  LoggerFactory.getLogger(RequestController.class);
	
	/**
	 * 요철 경로 : /response/index
	 * 응답 	   : /response/index.jsp
	 * @param model
	 */
	@RequestMapping("/index")
	public void response(Model model) {
		logger.info("void 타입 - : /response/index");
		logger.info("/response/index.jsp 뷰를 응답");
		model.addAttribute("message", "Hello");
		
	}
	/**
	 * 요청 경로 : /response/view
	 * 응답 	   : /response/index.jsp
	 * @return
	 */
	@RequestMapping("/View")
	public String responseView() {
		logger.info("Strine 타입 - : /response/index");
		logger.info("/response/index.jsp 뷰를 응답");
		logger.info("view 이름을 반환 값으로 지정");
		return "response/index";
		
	}
	
	/**
	 * ModelAndView
	 * 요청 경로 : /response/model/view
	 * 응답 	   : /response/index.jsp
	 * @return
	 */
	@RequestMapping("/model/view")
	public ModelAndView responseModelAndView(ModelAndView mv, Board board) {
		// ModelAndView
		// 뷰와 모델 데이터를 지정하여 함꼐 반환 처리 할 수 있는 스프링 프레임 워크
		logger.info("ModelAndView타입- /response/model/view");
		logger.info("/response/index.jsp 뷰를 응답");
		logger.info("모델과 뷰를 ModelAndView 객체에 지정하여 응답");
		
		//뷰이름 지정
		mv.setViewName("/response/index");		//view : /response/index.jsp
		
		//모델 등록
		board.setTitle("제목");
		board.setWriter("작성자");
		board.setContent("내용");
		mv.addObject("message", "ModelAndView 컨트롤러 응답...");
		mv.addObject("message","ModelAndView 컨트롤러 응답...");
		
//		model: board, response
		
		return mv;
	}
	
	/**
	 * 클래스
	 * 요청 경로 : /response/data/board
	 * 응답 : board (XML/JSON)
	 * @return
	 */
	@ResponseBody		//응답하는 데이터를 응답 메세지에 본문(body)에 지정하는 어노테이션
	@RequestMapping("/data/board")
	public Board reponseBoard() {
		Board board = new Board("제목","작성자","내용");
		return board;
		
	}
	
	/**
	 * 4xx : 클라이언트의 잘못
	 * 404 :  잘못된 경로를 요청(페이지 찾을 수 없음)
	 * 403 : 권한이 없음
	 * 406 : Accept 헤더 컨텐츠 유형과 서버가 응답해줄 수 있는 유형이 일치하지 않을떄
	 */
	
	
	
	/**
	 * 컬렉션
	 * 브라우저에서 Accept 헤더의 값이 우선순위에 맞는 데이터 형식으로 응답
	 * - text/html
	 * - application/xhtml+xml
	 * - application/xml
	 * - ...
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
//	 @RequestMapping("/data/board/list")
	@RequestMapping(value = "/data/board/list", produces = "application/json")
	// produces = "application/json" : 응답컨텐츠를 제이슨으로 강제로 응답하는 타입으로 지정
	public List<Board> responseBoardList() throws Exception {
		// - Accept:application/xml 으로 요청이 와도, json 으로 응답할 수 있다.
		List<Board> boardList = new ArrayList<Board>();
		boardList.add(new Board ("제목1","작성자1","내용1"));
		boardList.add(new Board ("제목2","작성자2","내용2"));
		boardList.add(new Board ("제목3","작성자3","내용3"));
		
		return boardList;
	}
	
	/**
	 * 컬렉션(Map)
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	// @RequestMapping(value = "/data/map")
	@RequestMapping(value = "/data/map", produces = "application/json")
	public Map<String, Board> responseMap() throws Exception {
		
		Map<String, Board> map = new HashMap<String, Board>();
		map.put("board1", new Board("제목1","작성자1","내용1"));
		map.put("board2", new Board("제목2","작성자2","내용2"));
		map.put("board3", new Board("제목3","작성자3","내용3"));
		
		return map;
	}
	
	
	/**
	 * ResponseEntity의 <void> 타입
	 * void : 본문을 사용하지 않는다~ 라는뜻
	 * @return
	 */
	@ResponseBody	//뷰페이지를 지정하는 작업이 아니라 데이터를 응답해서 사용
	@RequestMapping("/data/entity/void")
	public ResponseEntity<Void> responseEntityVoid() {
		// ResponseEntity
		// : 스프링 프레임워크에서 응답 헤더,본문,상태코드 등을 캡슐화하는 객체
		// ResponseEntity<Void>
		// : 헤더정보, 상태코드를 지정하여 사용할 수 있다.
		// HttpStatus 열거타입
		// : 상태코드를 가지고 있는 스프링프레임웤의 열거타입
		// - OK 						: 200
		// - NOT_FOUND 					: 404
		// - INTERNAL_SERVER_ERROR		: 500
		return new ResponseEntity<Void>(HttpStatus.OK);
//		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
//		return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		// 브라우저 개발자 도구에 network탭에서 확인 가능
	}
	
	/**
	 * ResponseEntity 의 <String> 타입으로 반환 할때
	 * - 응답 메세지를 String 타입으로 지정하여 응답
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/data/entity/string")
	public ResponseEntity<String> responseEntityString() {
		
		// new ResponseEntity<반환타입>(응답메시지, 상태코드);
		//개발자 도구가 아닌 스트링 타입으로 반환
		return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
	}
	
	/**
	 * ResponseEntity 에 <Board> 타입으로 반환
	 * : 응답 메세지를 객체<Board> 타입으로 반환
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/data/entity/board")
	public ResponseEntity<Board> responseEntityBoard() {
		// 객체 타입 으로 반환
		Board board = new Board("제목","작성자","내용");
		return new ResponseEntity<Board>(board, HttpStatus.OK);
	}
	
	/**
	 * ResponseEntity 에 <List<Board>> 컬렉션으로 지정
	 * XML 형식으로 가져옴
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/data/entity/board/list")
	public ResponseEntity<List<Board>> responseEntityBoardList() throws Exception {
		
		List<Board> boardList = new ArrayList<Board>();
		boardList.add(new Board ("제목1","작성자1","내용1"));
		boardList.add(new Board ("제목2","작성자2","내용2"));
		boardList.add(new Board ("제목3","작성자3","내용3"));
		
		// return new ResponseEntity<>(boardList, HttpStatus.OK);		// <> 생략가능
		return new ResponseEntity<List<Board>>(boardList, HttpStatus.OK);
	}
	
	
	
	/**
	 * ResponseEntity 에 <Map<String, Board>> 지정
	 * XML 형식으로 가져옴
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/data/entity/map")
	public ResponseEntity<Map<String, Board>> responseEntityBoardMap() throws Exception {
		
		List<Board> boardList = new ArrayList<Board>();
		boardList.add(new Board ("제목1","작성자1","내용1"));
		boardList.add(new Board ("제목2","작성자2","내용2"));
		boardList.add(new Board ("제목3","작성자3","내용3"));
		
		Map<String, Board> map = new HashMap<String, Board>();
		
		int i = 1;
		for (Board board : boardList) {
			map.put("board" + i++, board);
		}
		
		// json 형식으로 컨텐츠 타입 지정
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
//		new ResponseEntity<void>(응답상태); 보이드 인경우에만
//		new ResponseEntity<>(메시지,응답상태);
//		new ResponseEntity<>(메시지,헤더,응답상태);
		return new ResponseEntity<>(map, headers, HttpStatus.OK); 
	}
	
	
	/**
	 * 파일 다운로드
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/data/file")
	public ResponseEntity<byte[]> responseFile(HttpServletRequest request) throws Exception {
		
		String path = request.getServletPath()+("/WEB-INF/upload/test.png");
		logger.info("path : " + path);
		
		// 파일 경로
		// String filePath = "E:\\TJE\\UPLOAD\\test.jpg";
		// 경로에 맞는 폴더, 파일 삽입
		String filePath = request.getServletPath()+("/WEB-INF/upload/test.png");
		String fileName = "test.png";
		// 헤더정보
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType( MediaType.IMAGE_PNG );						// 이미지로 응답
		// headers.setContentType( MediaType.APPLICATION_OCTET_STREAM);		// 일반 프로그램 응답
		
		// 헤더!!!
		// headers.add("헤더명", "값")
		// Content-Disposition
		// - inline			: 웹페이지에서 출력(기본값)
		// - attachment		: 첨부파일 (다운로드)
		// 없으면 웹에서 출력
		headers.add("Content-Disposition", "attachment; filename=" + fileName);	// 다운로드 여부, 파일명 지정
		
		
		byte[] fileData = null;
		try {
			FileInputStream fis = new FileInputStream(filePath);
			// pom.xml에 commons-io 라이브러리 추가
			// toByteArray() : 파일을 바이트코드로 변환
			fileData = IOUtils.toByteArray(fis);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		// new ResponseEntity<반환타입>(응답메시지, 헤더,상태코드);
		return new ResponseEntity<byte[]>(fileData, headers, HttpStatus.OK);
	}
	
	
	
	
}
