package kr.ezen.pwEncoder;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired
	private BCryptPasswordEncoder pwEncoder;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
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
		
		return "home";
	}
	
	@RequestMapping("chiperTest")
	public void chiperTest() {
		String plainPw = "test1234";
		
		String encPw1 = pwEncoder.encode(plainPw);
		String encPw2 = pwEncoder.encode(plainPw);
		System.out.println("====================");
		System.out.println("encPw1 = " + encPw1);
		System.out.println("encPw2 = " + encPw2);
		
		String pw1 = "test1234";
		String pw2 = "abcd";
		
		// 평문암호는 같아도 DB에 저장되는 암호코드는 서로 다르다.
		// A의 비번:test1234, B의 비번:test1234 서로 같지만 DB에 저장되는 암호코드는
		// 같을 수 없다.
		System.out.println(pwEncoder.matches(pw1, encPw1));
		System.out.println(pwEncoder.matches(pw1, encPw2));
		System.out.println("====================");
		System.out.println(pwEncoder.matches(pw2, encPw1));
		
	}
	
}
