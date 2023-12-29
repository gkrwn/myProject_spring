package kr.ezen.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.ezen.pwEncoder.domain.ReplyDTO;
import kr.ezen.pwEncoder.domain.ReplyPageDTO;
import kr.ezen.pwEncoder.mapper.BoardMapper;
import kr.ezen.pwEncoder.mapper.ReplyMapper;

@Service
public class ReplyServiceImpl implements ReplyService{
	@Autowired
	private ReplyMapper mapper;
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Transactional
	@Override
	public int register(ReplyDTO rDto) {
		boardMapper.updateReplyCnt(rDto.getBid(), 1);
		int n = mapper.insert(rDto);
		return n;
	}
	
	@Transactional
	@Override
	public int remove(int rno) {
		ReplyDTO rDto = mapper.select(rno);
		boardMapper.updateReplyCnt(rDto.getBid(), -1);
		return mapper.delete(rno);
	}

	@Override
	public ReplyDTO read(int rno) {
		
		return mapper.select(rno);
	}

	@Override
	public int modify(ReplyDTO rDto) {
		
		return mapper.update(rDto);
	}

//	@Override
//	public List<ReplyDTO> getList(int bid) {
//		
//		return mapper.getListByBid(bid);
//	}
	
	@Override
//	public List<ReplyDTO> getList(int bid, int vp) {
	public ReplyPageDTO getList(int bid, int vp) {
		
		// bid에 해당하는 전체 댓글 수 가져오기
		int replyCnt = mapper.replyCnt(bid);
		
		ReplyPageDTO rPageDTO = new ReplyPageDTO();
		// viewPage가 바뀔때 마다 새롭게 셋팅
		rPageDTO.setViewPage(vp);
		
		rPageDTO.setValue(replyCnt);
		
		List<ReplyDTO> list = 
				mapper.getListByBid(bid, 
						rPageDTO.getStartIndex(),
						rPageDTO.getCntPerPage());
		
		rPageDTO.setList(list);
		
		return rPageDTO; 
	}

	@Override
	public int replyCnt(int bid) {
		
		return mapper.replyCnt(bid);
	}
	
}
