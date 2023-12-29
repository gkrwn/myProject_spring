package kr.ezen.pwEncoder.domain;

import java.sql.Date;

import lombok.Data;

@Data
public class BoardDTO {
	private int bid;
	private String subject;
	private String contents;
	private int hit;
	private String writer;
	private Date reg_date;
	
	private int replyCnt;
	
}
