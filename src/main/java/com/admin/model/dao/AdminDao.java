package com.admin.model.dao;

import static com.common.JDBCTemplate.close;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.member.model.vo.Member;

public class AdminDao {
	
	private Properties prop=new Properties();
	
	public AdminDao() {
		try {
			String path=AdminDao.class.getResource("/sql/admin/adminsql.properties").getPath();			
			prop.load(new FileReader(path));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
//	public List<Member> viewAllMember(Connection conn){
//		PreparedStatement pstmt=null;
//		ResultSet rs=null;
//		List<Member> mList=new ArrayList();
//		String sql=prop.getProperty("viewAllMember");
//		try {
//			pstmt=conn.prepareStatement(sql);
//			rs=pstmt.executeQuery();
//			while(rs.next()) {
//				Member m = Member.builder()
//						.userId(rs.getString("userid"))
//						//.password(rs.getString("password"))
//						.userName(rs.getString("userName"))
//						.gender(rs.getString("gender"))
//						.age(rs.getInt("age"))
//						.email(rs.getString("email"))
//						.phone(rs.getString("phone"))
//						.address(rs.getString("address"))
//						.hobby(rs.getString("hobby"))
//						.enrollDate(rs.getDate("enrollDate"))
//						.build();
//				mList.add(m);
//			}
//		}catch(SQLException e) {
//			e.printStackTrace();
//		}finally {
//			close(rs);
//			close(pstmt);
//		}
//		return mList;
//		
//	}
	
	
	public List<Member> viewAllMember(Connection conn, int cPage, int numPerPage){
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<Member> mList=new ArrayList();
		String sql=prop.getProperty("viewAllMember");
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, (cPage-1)*numPerPage+1);
			pstmt.setInt(2, cPage*numPerPage);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				Member m = Member.builder()
						.userId(rs.getString("userid"))
						//.password(rs.getString("password"))
						.userName(rs.getString("userName"))
						.gender(rs.getString("gender"))
						.age(rs.getInt("age"))
						.email(rs.getString("email"))
						.phone(rs.getString("phone"))
						.address(rs.getString("address"))
						.hobby(rs.getString("hobby"))
						.enrollDate(rs.getDate("enrollDate"))
						.build();
				mList.add(m);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return mList;
		
	}
	
	
	public int selectMemberAllCount(Connection conn) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		int result=0;
		String sql=prop.getProperty("selectMemberAllCount");
		try {
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				result=rs.getInt(1);	
									//????????? ???????????? ?????????????????? ?????????
									//?????? ??????????????? ????????? (?????? ???????????? ????????? ??? ??????)
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}
	
	public List<Member> searchMember(Connection conn, int cPage, int numPerpage, String searchType, String keyword){
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<Member> mList=new ArrayList();
		//????????? ????????? select?????? ??????
		//userId : select * from member where userId like '%keyword%'
		//userName : select * from member where userName like '%keyword%'
		//gender : select * from member where gender like '%keyword%'
		
		//????????????, ???????????? ???? ????????? ?????? ??????
		//select * from where ? like ? ----> ?????????
		//pstmt.setString(1, searchType)
		//pstmt.setString(2, keyword)
		String sql=prop.getProperty("searchMember");
		sql=sql.replace("#COL", searchType);	//sql??????????????????
		
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, "%"+keyword+"%");
			pstmt.setInt(2, (cPage-1)*numPerpage+1);
			pstmt.setInt(3, cPage*numPerpage);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				Member m = Member.builder()
						.userId(rs.getString("userid"))
						//.password(rs.getString("password"))
						.userName(rs.getString("userName"))
						.gender(rs.getString("gender"))
						.age(rs.getInt("age"))
						.email(rs.getString("email"))
						.phone(rs.getString("phone"))
						.address(rs.getString("address"))
						.hobby(rs.getString("hobby"))
						.enrollDate(rs.getDate("enrollDate"))
						.build();
				mList.add(m);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return mList;
	}
	
	//parameter MAP?????? ????????????
	public List<Member> searchMember(Connection conn, Map<String,Object> param){
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<Member> mList=new ArrayList();
		String sql=prop.getProperty("searchMember");
		sql=sql.replace("#COL", (String)param.get("searchType"));	//object?????? ??????????????? ??????????????????
		
		int cPage=(Integer)param.get("cPage");
		int numPerpage=(Integer)param.get("numPerpage");
		
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, "%"+param.get("keyword")+"%");
			//pstmt.setInt(2, ((Integer)param.get("cPage")-1)*((Integer)param.get("numPerpage"))+1);
			pstmt.setInt(2,(cPage-1)*numPerpage+1);
			//pstmt.setInt(3, (Integer)param.get("cPage")*(Integer)param.get("numPerpage"));
			pstmt.setInt(3, cPage*numPerpage);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				Member m = Member.builder()
						.userId(rs.getString("userid"))
						//.password(rs.getString("password"))
						.userName(rs.getString("userName"))
						.gender(rs.getString("gender"))
						.age(rs.getInt("age"))
						.email(rs.getString("email"))
						.phone(rs.getString("phone"))
						.address(rs.getString("address"))
						.hobby(rs.getString("hobby"))
						.enrollDate(rs.getDate("enrollDate"))
						.build();
				mList.add(m);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return mList;
	}
	
	public int searchMemberCount(Connection conn, String searchType, String keyword) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		int result=0;
		String sql=prop.getProperty("searchMemberCount");
		sql=sql.replace("#COL", searchType);
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,"%"+keyword+"%");
			rs=pstmt.executeQuery();
			if(rs.next()) result=rs.getInt("count");
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(conn);
		}
		return result;
		
	}
	
	
	
	
	

}
