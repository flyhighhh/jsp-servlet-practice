package com.notice.model.service;

import java.sql.Connection;
import java.util.List;
import com.notice.model.dao.NoticeDao;
import com.notice.model.vo.Notice;
import static com.common.JDBCTemplate.getConnection;
import static com.common.JDBCTemplate.close;
import static com.common.JDBCTemplate.commit;
import static com.common.JDBCTemplate.rollback;

public class NoticeService {
	
	private NoticeDao dao=new NoticeDao();

	public List<Notice> viewAllNotice(int cPage, int numPerPage){
		Connection conn=getConnection();
		List<Notice> noticeList=dao.viewAllNotice(conn,cPage,numPerPage);
		close(conn);
		return noticeList;
	}
	
	public int countAllNotice() {
		Connection conn=getConnection();
		int result=dao.countAllNotice(conn);
		close(conn);
		return result;
	}
	
	public Notice selectNotice(int noticeNo) {
		Connection conn=getConnection();
		Notice n=dao.selectNotice(conn, noticeNo);
		close(conn);
		return n;
	}
	
	public int insertNotice(Notice n) {
		Connection conn=getConnection();
		int result=dao.insertNotice(conn,n);
		if(result>0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}
	
	public int updateNotice(Notice n) {
		Connection conn=getConnection();
		int result=dao.updateNotice(conn,n);
		if(result>0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
		
	}
	public int deleteNotice(int noticeNo) {
		Connection conn=getConnection();
		int result=dao.deleteNotice(conn,noticeNo);
		if(result>0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
		
	}
	
	
	
}
