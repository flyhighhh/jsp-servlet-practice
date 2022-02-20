package com.member.model.service;

import java.sql.Connection;
import java.util.List;

import com.member.model.dao.MemberDao;
import com.member.model.vo.Member;
import static com.common.JDBCTemplate.getConnection;
import static com.common.JDBCTemplate.close;
import static com.common.JDBCTemplate.commit;
import static com.common.JDBCTemplate.rollback;
public class MemberService {
	
	private MemberDao dao=new MemberDao();
	
	public Member login(String userId, String password) {
		Connection conn=getConnection();
		Member m=dao.login(conn,userId,password);
		close(conn);
		return m;
	}
	
	public int insertMember(Member m) {
		Connection conn=getConnection();
		int result=dao.insertMember(conn,m);
		//DML구문은 트렌젝션처리를 해야함.
		if(result>0) commit(conn);
		else rollback(conn);
		close(conn);
		
		return result;
				
	}
	
	public Member checkIdDuplicate(String userId) {
		Connection conn=getConnection();
		Member m=dao.checkIdDuplicate(conn,userId);
		close(conn);
		return m;
	}
	
	
	public int updateMember(Member m) {
		Connection conn=getConnection();
		int result=dao.updateMember(conn,m);
		if(result>0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		
		return result;
	}
	
	
	public int changePassword(String userId, String password) {
		Connection conn=getConnection();
		int result=dao.changePassword(conn,userId,password);
		if(result>0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		return result;
	}
	
	public int deleteAccount(String userId) {
		Connection conn=getConnection();
		int result=dao.deleteAccount(conn,userId);
		if(result>0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

}
