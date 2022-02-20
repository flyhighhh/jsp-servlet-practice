package com.notice.model.dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.member.model.vo.Member;
import com.notice.model.vo.Notice;
import static com.common.JDBCTemplate.close;

public class NoticeDao {
	
	private Properties prop=new Properties();
	
	public NoticeDao() {
		try {
			String path=NoticeDao.class.getResource("/sql/notice/noticesql.properties").getPath();
			prop.load(new FileReader(path));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<Notice> viewAllNotice(Connection conn, int cPage, int numPerPage){
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<Notice> noticeList=new ArrayList();
		String sql=prop.getProperty("viewAllNotice");
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, (cPage-1)*numPerPage+1);
			pstmt.setInt(2, cPage*numPerPage);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				Notice n= Notice.builder()
						.noticeNo(rs.getInt("NOTICE_NO"))
						.noticeTitle(rs.getString("NOTICE_TITLE"))
						.noticeWriter(rs.getString("NOTICE_WRITER"))
						.noticeContent(rs.getString("NOTICE_CONTENT"))
						.noticeDate(rs.getDate("NOTICE_DATE"))
						.filePath(rs.getString("FILEPATH"))
						.build();
				noticeList.add(n);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return noticeList;
	}
	
	public int countAllNotice(Connection conn) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		int result=0;
		String sql=prop.getProperty("countAllNotice");
		try {
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				result=rs.getInt(1);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}
	
	public Notice selectNotice(Connection conn, int noticeNo) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		Notice n=null;
		String sql=prop.getProperty("selectNotice");
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, noticeNo);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				n=Notice.builder()
						.noticeNo(rs.getInt("NOTICE_NO"))
						.noticeTitle(rs.getString("NOTICE_TITLE"))
						.noticeWriter(rs.getString("NOTICE_WRITER"))
						.noticeContent(rs.getString("NOTICE_CONTENT"))
						.filePath(rs.getString("FILEPATH"))
						.build();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return n;
	}
	
	public int insertNotice(Connection conn, Notice n) {
		PreparedStatement pstmt=null;
		int result=0;
		String sql=prop.getProperty("insertNotice");
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,n.getNoticeTitle());
			pstmt.setString(2,n.getNoticeWriter());
			pstmt.setString(3,n.getNoticeContent());
			pstmt.setString(4,n.getFilePath());
			result=pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}
	
	public int updateNotice(Connection conn, Notice n) {
		PreparedStatement pstmt=null;
		int result=0;
		String sql=prop.getProperty("updateNotice");
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,n.getNoticeTitle());
			pstmt.setString(2,n.getNoticeWriter());
			pstmt.setString(3,n.getNoticeContent());
			pstmt.setString(4,n.getFilePath());
			pstmt.setInt(5, n.getNoticeNo());
			result=pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
		
	}
	
	public int deleteNotice(Connection conn, int noticeNo) {
		PreparedStatement pstmt=null;
		int result=0;
		String sql=prop.getProperty("deleteNotice");
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, noticeNo);
			result=pstmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}
	

}
