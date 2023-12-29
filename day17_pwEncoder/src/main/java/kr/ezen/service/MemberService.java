package kr.ezen.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import kr.ezen.pwEncoder.domain.BoardDTO;
import kr.ezen.pwEncoder.domain.MemberDTO;
import kr.ezen.pwEncoder.domain.PageDTO;

public interface MemberService {
	List<MemberDTO> memberList();
	
	int memberRegister(MemberDTO dto);
	
	MemberDTO memberInfo(int no);
	
	int memberRemove(int no);
	
	int memberModify(MemberDTO dto);
	
	MemberDTO idCheck(String uid);

	boolean memberLogin(MemberDTO dto, HttpServletRequest req);

	String findId(MemberDTO dto);

	int findPw(String uid, String uEmail);

	int modifyPw(MemberDTO dto);
}
