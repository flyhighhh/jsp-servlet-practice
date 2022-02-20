package com.admin.model.service;

import static com.common.JDBCTemplate.close;
import static com.common.JDBCTemplate.getConnection;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.admin.model.dao.AdminDao;
import com.member.model.vo.Member;

public class AdminService {
	
	private AdminDao dao=new AdminDao();
	
//	public List<Member> viewAllMember(){
//		Connection conn=getConnection();
//		List<Member> mList=dao.viewAllMember(conn);
//		close(conn);
//		return mList;
//		
//	}
	
	public List<Member> viewAllMember(int cPage, int numPerPage){
		Connection conn=getConnection();
		List<Member> mList=dao.viewAllMember(conn, cPage, numPerPage);
		close(conn);
		return mList;
		
	}
	
	public int selectMemberAllCount() {
		Connection conn=getConnection();
		int result=dao.selectMemberAllCount(conn);
		close(conn);
		return result;
		
	}

	
	public List<Member> searchMember(int cPage, int numPerpage, String searchType, String keyword){
		Connection conn=getConnection();
		List<Member> mList=dao.searchMember(conn, cPage, numPerpage, searchType, keyword);
		close(conn);
		return mList;
		
	}
	
	//Map으로
	public List<Member> searchMember(Map<String,Object> param){
		Connection conn=getConnection();
		List<Member> mList=dao.searchMember(conn, param);
		close(conn);
		return mList;
	}
	
	
	public int searchMemberCount(String searchType, String keyword) {
		Connection conn=getConnection();
		int result=dao.searchMemberCount(conn,searchType,keyword);
		close(conn);
		return result;
		
	}
	
	
}
