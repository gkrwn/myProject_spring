package kr.ezen.pwEncoder.domain;

import java.util.List;

import lombok.Data;

@Data
public class ReplyPageDTO {
	private int viewPage = 1; // 현재 페이지
	private int blockSize = 5;
	private int blockNum; // 블럭의 위치
	private int blockStart; // 블럭의 시작값
	private int blockEnd; //블럭의 마지막값
	private int prevPage; // 이전페이지
	private int nextPage; // 다음페이지
	private int totalPage; // 전체 페이지수
	
	// 검색처리를 위해 totalCnt 추가
	private int totalCnt;
	
	// 페이지별 게시글 수
	private int cntPerPage = 5;
	
	// 각페이지별 시작값 - offset(0, 10, 20, 30,....)
	private int startIndex; 
	
	
	//************** 댓글 리스트 ***************
	private List<ReplyDTO> list;
	
	
	// 멤버변수 값들 셋팅
	public void setValue(int totalCnt) {

		this.totalCnt = totalCnt;
		
		// 전체 페이지수
		this.totalPage = (int)Math.ceil((double)totalCnt/cntPerPage);
		// 0, 10,20,30,.....
		this.startIndex = (viewPage-1)*cntPerPage;
		
		// 현재 페이지 블럭의 위치, 0부터 시작
		this.blockNum = (viewPage-1)/blockSize;
		
		// 블럭의 시작값, 1, 6, 11, 16,...
		this.blockStart = (blockSize*blockNum)+1;
		
		// 블럭의 마지막값
		this.blockEnd = blockStart + (blockSize - 1);
		if(blockEnd > totalPage) blockEnd = totalPage;
		
		// 이전페이지
		this.prevPage = blockStart - 1;
		
		// 다음페이지
		this.nextPage = blockEnd + 1;
	}
	

}
