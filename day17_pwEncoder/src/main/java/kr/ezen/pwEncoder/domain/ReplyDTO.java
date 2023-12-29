package kr.ezen.pwEncoder.domain;

import java.util.Date;

import lombok.Data;

@Data
public class ReplyDTO {
	private int rno;
	private int bid;
	
	private String r_contents;
	private String replyer;
	private Date r_date;
	private Date update_date;
	
}
