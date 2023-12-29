package kr.ezen.pwEncoder.domain;

public class PageDTO {
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
	private int cntPerPage = 10;
	
	// 각페이지별 시작값 - offset(0, 10, 20, 30,....)
	private int startIndex; 
	
	// 멤버변수 값들 셋팅
	public void setValue(int totalCnt, int cntPerPage) {
		// 검색된 게시글 수를 저장
		// 검색처리를 위해 전체 게시글 수 재설정
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
	
	////////////// Search ////////////////
	private String searchType;
	private String keyword;
		
	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	///////////////////////////////////////////
	
	
	// getter, setter
	public int getTotalCnt() {
		return totalCnt;
	}

	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}
	
	public int getViewPage() {
		return viewPage;
	}

	public void setViewPage(int viewPage) {
		this.viewPage = viewPage;
	}

	public int getBlockSize() {
		return blockSize;
	}

	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}

	public int getBlockNum() {
		return blockNum;
	}

	public void setBlockNum(int blockNum) {
		this.blockNum = blockNum;
	}

	public int getBlockStart() {
		return blockStart;
	}

	public void setBlockStart(int blockStart) {
		this.blockStart = blockStart;
	}

	public int getBlockEnd() {
		return blockEnd;
	}

	public void setBlockEnd(int blockEnd) {
		this.blockEnd = blockEnd;
	}

	public int getPrevPage() {
		return prevPage;
	}

	public void setPrevPage(int prevPage) {
		this.prevPage = prevPage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getCntPerPage() {
		return cntPerPage;
	}

	public void setCntPerPage(int cntPerPage) {
		this.cntPerPage = cntPerPage;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	
	
	
	
}
