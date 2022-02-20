package com.board.model.vo;

import java.sql.Date;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Board {
	private int boardNo;
	private String boardTitle;
	private String boardWriter;	//원래는 Member writer
	private String boardContent;
	private String originalFileName;
	private String renamedFileName;
	private Date boardDate;
	private int readCount;	//default 0
}
