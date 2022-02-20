package com.notice.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notice {
	private int noticeNo;
	private String noticeTitle;
	private String noticeWriter;	//원래는 Member noticeWriter로 만들어야됨
	private String noticeContent;
	private Date noticeDate;
	private String filePath;
	//private	String status;
	
}
