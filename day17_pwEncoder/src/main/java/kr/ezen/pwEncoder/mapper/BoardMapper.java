package kr.ezen.pwEncoder.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.ezen.pwEncoder.domain.BoardDTO;
import kr.ezen.pwEncoder.domain.PageDTO;

public interface BoardMapper {
	// 게시글 등록
	void insert(BoardDTO dto);
	
	// 게시판 리스트
//	List<BoardDTO> getList();
	List<BoardDTO> getList(PageDTO pDto);
	
	// 글상세보기
	BoardDTO view(int bid);

	void update(BoardDTO dto);

	void delete(int bid);
	
	// 조회수 추가
	void hitAdd(int bid);
	
	// 검색어 없을 경우 전체 게시글 수
//	int totalCnt();
	// 검색어 있을 경우 전체 게시글 수
	int totalCnt(PageDTO pDto);
	
	// 댓글 추가/삭제 시 replyCnt 수정
	void updateReplyCnt(@Param("bid") int bid, @Param("n") int n);
	
}
