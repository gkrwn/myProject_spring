package kr.ezen.controller;

import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.ezen.pwEncoder.domain.MemberDTO;
import kr.ezen.pwEncoder.mapper.MemberMapper;
import kr.ezen.service.MemberService;


@Controller
@RequestMapping("/member")
public class MemberController {
	
	@Autowired // DI(Dependency Injection: 의존성 주입)
//	private MemberDAO dao;
	// DAO 대신 Mapper 사용
	private MemberService service;
	
	@Autowired
	JavaMailSender mailSender;
	
	@RequestMapping("/memberList.do")
	public String memberList(Model model) {
		List<MemberDTO> memberList = service.memberList();
		model.addAttribute("list", memberList);
		
		return "member/memberList";
	}
	
	@RequestMapping("/memberRegister.do")
	public String memberRegister() {
		return "member/register";
	}
	
	@RequestMapping("/memberInsert.do")
	public String memberInsert(MemberDTO dto) {
	int cnt = service.memberRegister(dto);
		
		return "redirect:memberList.do";
	}
	
	@RequestMapping("/memberInfo.do")
	public String memberInfo(int no, Model model) {
		MemberDTO dto = service.memberInfo(no);
		model.addAttribute("dto", dto);
		
		return "member/memberInfo";
	}
	
	@RequestMapping("/memberUpdate.do")
	public String memberUpdate(MemberDTO dto) {
		service.memberModify(dto);
		
		return "redirect:memberList.do";
	}
	
	@RequestMapping("/memberDelete.do")
	public String memberDelete(int no) {
		service.memberRemove(no);
		
		return "redirect:memberList.do";
	}
	
	// Message Converter API : jackson
	// ==> JSON 형식 데이터를 자바객체로, 반대로 자바객체 --> JSON
	
	// 비동기 전송데이터는 HTTP MSG의 body에 담아서 전송한다.
	// @ResponseBody : 서버 --> 클라이언트
	// @RequestBody  : 클라이언트 --> 서버
	
	@RequestMapping("/memberAjaxList.do")	
	public @ResponseBody List<MemberDTO> memberAjaxList() {
		List<MemberDTO> memberList = service.memberList();		
		
		return memberList;
	}
	
	@RequestMapping("/memberIdCheck.do")
	@ResponseBody
	public String memberIdCheck(@RequestParam("uid") String uid) {	
		System.out.println("uid : " + uid);
		MemberDTO dto = service.idCheck(uid);
		System.out.println("dto : " + dto);
		// 아이디 중복 또는 빈값이 넘어왔을때
		if(dto !=null || "".equals(uid.trim())) {
			return "no";
		}
		
		// 아이디 중복이 아닌 경우
		return "yes";
	}
	
	@RequestMapping("/memberEmailCheck.do")
	@ResponseBody
	public String emailCheck(@RequestParam("uEmail") String uEmail) {
		System.out.println("uEmail = " + uEmail);
		
		// 인증코드 생성, java.util 안에 UUID를 이용
//		String uuid = UUID.randomUUID().toString();
//		System.out.println(uuid);
		
		String uuid = UUID.randomUUID().toString().substring(0,6);
//		if(uuid != null) return uuid;
		
		// Mime Message 객체 생성 : 데이터(Mime타입) 전송 (예: image/jpg)
		MimeMessage mail = mailSender.createMimeMessage();
		
		// 메일 내용
		String mailContents = "<h3>이메일 주소 확인</h3><br/>"
				+"<span>사용자가 본인임을 확인하려고 합니다. 다음 확인 코드를 입력하세요</span>"
				+"<h2>"+uuid+"</h2>";
		
		try {
			// 메일 제목 셋팅
			mail.setSubject("hj아카데미 [이메일 인증]", "utf-8");
			// 메일 내용 셋팅
			mail.setText(mailContents, "utf-8", "html");
			
			// 수신자 셋팅, 인터넷 주소체계로 변환
			mail.addRecipient(RecipientType.TO, new InternetAddress(uEmail));
			mailSender.send(mail);
			
			return uuid;
			
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return "fail";
	}
	
	// 로그인
	@GetMapping("login.do")
	public String loginForm(String moveUrl, Model model) {
		model.addAttribute("moveUrl", moveUrl);
		return "login/loginForm";
	}
	
	@PostMapping("login.do")
	public String memberLogin(String moveUrl, MemberDTO dto, HttpServletRequest req) {
		boolean result = service.memberLogin(dto, req);
		
		if(!result) { // 로그인 실패
			return "redirect:login.do";
		}
//		return "redirect:/member/memberList.do";
		if(!moveUrl.equals("")) {
			return "redirect:"+moveUrl;
		}
		
		return "redirect:/";
	}
	
	@GetMapping("logout.do")
	public String memberLogout(HttpSession session) {
		session.invalidate(); // 세션 초기화
		return "redirect:/";
	}
	
	// 아이디 비번찾기 폼페이지 이동
	@GetMapping("idPwFind.do")
	public String idPwFind(String find, Model model) {
		model.addAttribute("find", find);
		
		return "login/idPwFind";
	}
	
	// 아이디 찾기
	@PostMapping("findId.do")
	@ResponseBody
	public String findId(MemberDTO dto) {
		String findId = service.findId(dto);
		System.out.println("findId : "+findId);
		
		return findId;
	}
	// 비번 찾기
	@PostMapping("findPw.do")
	@ResponseBody
	public int findPw(String uid, String uEmail) {
		int n = service.findPw(uid, uEmail);
		
		return n;
	}
	
	// 마이 프로필
	@GetMapping("myProfile.do")
	public String myProfile() {
		return "member/myProfile";
	}
	
	//
	@PostMapping("pwCheck.do")
	@ResponseBody
	public String pwCheck(String pw, HttpSession session) {
		String chkResult = "";
		// DB에서 확인할 필요없이 session에 바인딩된 값으로 확인
		// session에서 가져올때는 타입이 Object
		MemberDTO dto = (MemberDTO)session.getAttribute("loginDTO");
		
		String dbPw = dto.getPw();
		
		if(dbPw.equals(pw)) {
			chkResult = "ok";
		}else {
			chkResult = "no";
		}
		return chkResult;
	}
	
	@PostMapping("pwChange.do")
	@ResponseBody
//	@ResponseBody는 클라이언트에 전송할 데이터를 Http Message Body에 실어보냄
//	네트워크 전송을 할 수 있도록 JSON으로 변환(Jackson API)
//	@RequestBody는 Http Message의 Body에 있는 
//	데이터를 읽어올때 JSON 데이터를 자바객체로 변환(Jackson API)까지 한다.
	public int pwChange(@RequestBody MemberDTO dto) {
		int n = service.modifyPw(dto);
		
		return n;
	}
	
	
	
}
