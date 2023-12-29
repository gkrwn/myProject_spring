package kr.ezen.service;

import java.util.List;

import kr.ezen.pwEncoder.domain.BoardDTO;
import kr.ezen.pwEncoder.domain.PageDTO;

public interface BoardService {
	// 게시글 등록
	void register(BoardDTO dto);
	
	// 게시판 리스트
//	List<BoardDTO> getList();
	List<BoardDTO> getList(PageDTO pDto);
	
	// 글상세보기
//	BoardDTO view(int bid);
	BoardDTO view(int bid, String mode);

	void modify(BoardDTO dto);

	void remove(int bid);
	
	// 조회수 추가
//	void hitAdd(int bid);
}
