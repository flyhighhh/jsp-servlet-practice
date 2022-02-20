package com.board.model.service;

import static com.common.JDBCTemplate.close;
import static com.common.JDBCTemplate.commit;
import static com.common.JDBCTemplate.getConnection;
import static com.common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.List;

import com.board.model.dao.BoardDao;
import com.board.model.vo.Board;
import com.board.model.vo.BoardComment;

public class BoardService {
	
	private BoardDao dao=new BoardDao();
	
	public List<Board> viewBoardList(int cPage, int numPerPage){
		Connection conn=getConnection();
		List<Board> boardList=dao.viewBoardList(conn,cPage,numPerPage);
		close(conn);
		return boardList;
		
	}
	public int boardListCount() {
		Connection conn=getConnection();
		int result=dao.boardListCount(conn);
		close(conn);
		return result;
	}
	
	public Board viewBoardContent(int boardNo, boolean isRead) {
		Connection conn=getConnection();
			
		//***조회수 증가 로직을 추가
		Board b=dao.viewBoardContent(conn, boardNo);
		if(b!=null && !isRead) {
			int result=dao.updateBoardReadCount(conn,boardNo);
			
			if(result>0) {
				commit(conn);
				b=dao.viewBoardContent(conn,boardNo);
			}
			else rollback(conn);
		}
		close(conn);
		return b;
	}
	
	public int insertBoard(Board b) {
		Connection conn=getConnection();
		int result=dao.insertBoard(conn,b);
		if(result>0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}
	
	public int insertBoardComment(BoardComment bc) {
		Connection conn=getConnection();
		int result=dao.insertBoardComment(conn, bc);
		if(result>0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}
	
	public List<BoardComment> boardCommentList(int boardNo){
		Connection conn=getConnection();
		List<BoardComment> bcList=dao.boardCommentList(conn,boardNo);
		close(conn);
		return bcList;
	}
	
	
}
