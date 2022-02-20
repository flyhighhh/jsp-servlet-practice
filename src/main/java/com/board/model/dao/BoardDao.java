package com.board.model.dao;

import static com.common.JDBCTemplate.close;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.board.model.vo.Board;
import com.board.model.vo.BoardComment;

public class BoardDao {
	
	private Properties prop=new Properties();
	
	public BoardDao() {
		try {
			String path=BoardDao.class.getResource("/sql/board/boardsql.properties").getPath();
			prop.load(new FileReader(path));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public List<Board> viewBoardList(Connection conn, int cPage, int numPerPage){
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<Board> boardList=new ArrayList();
		String sql=prop.getProperty("viewBoardList");
		
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, (cPage-1)*numPerPage+1);
			pstmt.setInt(2, cPage*numPerPage);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				Board b=Board.builder()
						.boardNo(rs.getInt("BOARD_NO"))
						.boardTitle(rs.getString("BOARD_TITLE"))
						.boardWriter(rs.getString("BOARD_WRITER"))
						.boardContent(rs.getString("BOARD_CONTENT"))
						.originalFileName(rs.getString("BOARD_ORIGINAL_FILENAME"))
						.renamedFileName(rs.getString("board_renamed_filename"))
						.boardDate(rs.getDate("board_date"))
						.readCount(rs.getInt("BOARD_READCOUNT"))
						.build();
				boardList.add(b);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return boardList;
	}
	
	public int boardListCount(Connection conn) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		int result=0;
		String sql=prop.getProperty("boardListCount");
		try {
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()) result=rs.getInt(1);
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}return result;
		
	}
	
	public Board viewBoardContent(Connection conn, int boardNo) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		Board b=null;
		String sql=prop.getProperty("selectBoard");
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				b=Board.builder()
						.boardNo(rs.getInt("BOARD_NO"))
						.boardTitle(rs.getString("BOARD_TITLE"))
						.boardWriter(rs.getString("BOARD_WRITER"))
						.boardContent(rs.getString("BOARD_CONTENT"))
						.originalFileName(rs.getString("BOARD_ORIGINAL_FILENAME"))
						.renamedFileName(rs.getString("BOARD_RENAMED_FILENAME"))
						.boardDate(rs.getDate("BOARD_DATE"))
						.readCount(rs.getInt("BOARD_READCOUNT"))
						.build();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return b;
	}
	
	public int updateBoardReadCount(Connection conn, int boardNo) {
		PreparedStatement pstmt=null;
		int result=0;
		String sql=prop.getProperty("updateBoardReadCount");
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			result=pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}
	
	public int insertBoard(Connection conn, Board b) {
		PreparedStatement pstmt=null;
		int result=0;
		String sql=prop.getProperty("insertBoard");
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,b.getBoardTitle());
			pstmt.setString(2,b.getBoardWriter());
			pstmt.setString(3,b.getBoardContent());
			pstmt.setString(4,b.getOriginalFileName());
			pstmt.setString(5,b.getRenamedFileName());
			result=pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}
	
	public int insertBoardComment(Connection conn, BoardComment bc) {
		PreparedStatement pstmt=null;
		int result=0;
		String sql=prop.getProperty("insertBoardComment");
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, bc.getBoardCommentLevel());
			pstmt.setString(2, bc.getBoardCommentWriter());
			pstmt.setString(3, bc.getBoardCommentContent());
			pstmt.setInt(4, bc.getBoardRef());
			//데이터가 없으면 (null)이라고 나와야하는데 이렇게 넣으면 오류남
			//pstmt.setInt(5, bc.getBoardCommentRef()==0?null:bc.getBoardCommentRef());
			//string으로 집어넣으면 됨
			pstmt.setString(5, bc.getBoardCommentRef()==0?null:String.valueOf(bc.getBoardCommentRef()));
			
			result=pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}
	
	public List<BoardComment> boardCommentList(Connection conn, int boardNo){
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<BoardComment> bcList=new ArrayList();
		String sql=prop.getProperty("boardCommentList");
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			rs=pstmt.executeQuery();
			
			while(rs.next()){
				BoardComment bc=BoardComment.builder()
						.boardCommentNo(rs.getInt("BOARD_COMMENT_NO"))
						.boardCommentLevel(rs.getInt("BOARD_COMMENT_LEVEL"))
						.boardCommentWriter(rs.getString("BOARD_COMMENT_WRITER"))
						.boardCommentContent(rs.getString("BOARD_COMMENT_CONTENT"))
						.boardRef(rs.getInt("BOARD_REF"))
						.boardCommentRef(rs.getInt("BOARD_COMMENT_REF"))
						.boardCommentDate(rs.getDate("BOARD_COMMENT_DATE"))
						.build();
				bcList.add(bc);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return bcList;
	}
	
	
}
