package kr.ezen.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ezen.pwEncoder.domain.BoardDTO;
import kr.ezen.pwEncoder.domain.PageDTO;
import kr.ezen.pwEncoder.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {
	@Autowired
	private BoardMapper mapper;
	
	@Override
	public void register(BoardDTO dto) {
		mapper.insert(dto);
	}

//	@Override
//	public List<BoardDTO> getList() {
////		List<BoardDTO> list = mapper.getList();
////		return list;	
//		
//		// 위의 두라인과 동일
//		return mapper.getList();
//	}
	
	// 페이징 처리
	@Override
	public List<BoardDTO> getList(PageDTO pDto) {	
		
		// 검색어 없을 경우 전체 게시글 수
//		int totalCnt = mapper.totalCnt();
		
		// 검색어 있을 경우 전체 게시글 수
		int totalCnt = mapper.totalCnt(pDto);
		
		// startIndex가 정해진다.
		pDto.setValue(totalCnt, pDto.getCntPerPage());
		
		
		return mapper.getList(pDto);
	}

//	@Override
//	public BoardDTO view(int bid) {
//		mapper.hitAdd(bid);
//		return mapper.view(bid);
//	}

	// 수정하기, 글상세보기 구분해서 조회수를 1번만 증가시키기
	@Override
	public BoardDTO view(int bid, String mode) {
		// 글상세보기일 경우만 hitAdd호출
		if(mode.equals("v")) {
			mapper.hitAdd(bid);
		}
		return mapper.view(bid);
	}

	@Override
	public void modify(BoardDTO dto) {
		mapper.update(dto);
	}

	@Override
	public void remove(int bid) {
		mapper.delete(bid);		
	}

//	@Override
//	public void hitAdd(int bid) {
//		mapper.hitAdd(bid);
//	}

}
