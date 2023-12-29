package kr.ezen.pwEncoder.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.ezen.pwEncoder.domain.ReplyDTO;

public interface ReplyMapper {
	int insert(ReplyDTO rDto);
	
	int delete(int rno);

	ReplyDTO select(int rno);

	int update(ReplyDTO rDto);

//	List<ReplyDTO> getListByBid(int bid);
	List<ReplyDTO> getListByBid(@Param("bid") int bid,
			@Param("startIndex") int si, 
			@Param("cntPerPage") int cp);
	
	int replyCnt(int bid);
}
