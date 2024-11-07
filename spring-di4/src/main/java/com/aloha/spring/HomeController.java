package com.aloha.spring;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.aloha.spring.Service.BoardService;
import com.aloha.spring.Service.MyService;
import com.aloha.spring.dao.ReplyDAO;


/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	//의존성 자동 주입
	//Service A,B,C의 서비스 등록한 빈 이름을 @Qualifier뒤에 입력ㄴ
	
	@Autowired
	@Qualifier("AServiceImpl")
	private MyService myServiceA;
	
	@Autowired
	@Qualifier("BServiceImpl")
	private MyService myServiceB;
	
	@Autowired
	@Qualifier("C")
	private MyService myServiceC;
	
	// --------------------------------------
	// 의존성 자동 주입
	@Autowired(required = false)  //등록된 빈이 없을때 , null로 가져온다
	private BoardService boardService; // 보드 서비스가 null인 상태, 보드서비스 임플에서 Service어노테이션 입력하면 들어감
	
	@Autowired
	private ReplyDAO replyDAO;
	
	
	
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		// 출력
		myServiceA.test();
		myServiceB.test();
		myServiceC.test();
		
		//-------------------------------------
		if( boardService != null ) {	// 빈이 null이 아닐 경우 출력
			boardService.test();
			boardService.setDAO(replyDAO);
			boardService.test();	//replySAO로 바뀐다음에 호출이되는지 확인
		} else {	//그게아니면~~				
			System.err.println("빈이 등록되지 않았습니다.");
		}
		
		return "home";
	}
	
}
