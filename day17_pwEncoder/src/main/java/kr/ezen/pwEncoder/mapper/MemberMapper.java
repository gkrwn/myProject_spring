package kr.ezen.pwEncoder.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.ezen.pwEncoder.domain.MemberDTO;


// DAO 대신 mapper 인터페이스 사용하기
//@Mapper
public interface MemberMapper {
	List<MemberDTO> memberList();
	
	int memberInsert(MemberDTO dto);
	
	MemberDTO memberInfo(int no);
	
	int deleteMember(int no);
	
	int memberUpdate(MemberDTO dto);
	
	MemberDTO idCheck(String uid);

	MemberDTO memberLogin(MemberDTO dto);

	String findId(MemberDTO dto);

	int findPw(String uid, String uEmail, String tempPw);

	int updatePw(MemberDTO dto);
}
