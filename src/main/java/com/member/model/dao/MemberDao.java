package com.member.model.dao;
import static com.common.JDBCTemplate.close;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.member.model.vo.Member;


public class MemberDao {
	
	private Properties prop=new Properties();
	
	public MemberDao() {
		String path=MemberDao.class.getResource("/sql/member/membersql.properties").getPath();
		try {
			prop.load(new FileReader(path));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	//로그인을 했을때 로그인한 사람의 정보 반환
	//userId가 primary
	public Member login(Connection conn, String userId, String password){
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		Member m=null;
		String sql=prop.getProperty("selectMember");
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setString(2, password);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				//롬복이 만들어놓은 builder 패턴 이용하기
				m=Member.builder()
						.userId(rs.getString("userid"))
						.password(rs.getString("password"))
						.userName(rs.getString("userName"))
						.gender(rs.getString("gender"))
						.age(rs.getInt("age"))
						.email(rs.getString("email"))
						.phone(rs.getString("phone"))
						.address(rs.getString("address"))
						.hobby(rs.getString("hobby"))
						.enrollDate(rs.getDate("enrollDate"))
						.build();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return m;
	}
	
	public int insertMember(Connection conn, Member m) {
		PreparedStatement pstmt=null;
		int result=0;
		String sql=prop.getProperty("insertMember");
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, m.getUserId());
			pstmt.setString(2, m.getPassword());
			pstmt.setString(3, m.getUserName());
			pstmt.setString(4, m.getGender());
			pstmt.setInt(5,m.getAge());
			pstmt.setString(6, m.getEmail());
			pstmt.setString(7, m.getPhone());
			pstmt.setString(8, m.getAddress());
			pstmt.setString(9,m.getHobby());
			
			result=pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}
	
	public Member checkIdDuplicate(Connection conn, String userId) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		Member m=null;
		String sql=prop.getProperty("selectId");
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,userId);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				m=Member.builder()
						.userId(rs.getString("userId"))
						.userId(rs.getString("userId"))
						.userName(rs.getString("userName"))
						.gender(rs.getString("gender"))
						.age(rs.getInt("age"))
						.email(rs.getString("email"))
						.phone(rs.getString("phone"))
						.address(rs.getString("address"))
						.hobby(rs.getString("hobby"))
						.build();
				//패스워드는 뺌
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return m;
	}
	
	public int updateMember(Connection conn, Member m) {
		PreparedStatement pstmt=null;
		int result=0;
		String sql=prop.getProperty("updateMember");
		try {
			pstmt=conn.prepareStatement(sql);
			//pstmt.setString(1, m.getPassword());
			pstmt.setString(1, m.getUserName());
			pstmt.setString(2, m.getGender());
			pstmt.setInt(3, m.getAge());
			pstmt.setString(4, m.getEmail());
			pstmt.setString(5, m.getPhone());
			pstmt.setString(6, m.getAddress());
			pstmt.setString(7, m.getHobby());
			pstmt.setString(8, m.getUserId());
			
			result=pstmt.executeUpdate();
					
		}catch(SQLException e) {
			e.printStackTrace();;
		}finally {
			close(pstmt);
		}
		return result;
	}
	
	public int changePassword(Connection conn, String userId, String password) {
		PreparedStatement pstmt=null;
		int result=0;
		String sql=prop.getProperty("changePassword");
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, password);
			pstmt.setString(2, userId);
			result=pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}
	
	public int deleteAccount(Connection conn, String userId) {
		PreparedStatement pstmt=null;
		int result=0;
		String sql=prop.getProperty("deleteAccount");
		System.out.println(sql);
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			result=pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}
	

}
