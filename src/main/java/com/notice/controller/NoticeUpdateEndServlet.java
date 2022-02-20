package com.notice.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.notice.model.service.NoticeService;
import com.notice.model.vo.Notice;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

/**
 * Servlet implementation class NoticeUpdateEndServlet
 */
@WebServlet("/notice/noticeUpdateEnd.do")
public class NoticeUpdateEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeUpdateEndServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(!ServletFileUpload.isMultipartContent(request)) {
			request.setAttribute("msg","시스템 에러 [form:enctype] 관리자에게 문의하지 마세요");
			request.setAttribute("loc", "/notice/noticeView.do");
			request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);
			return;
		}
		
		String path=getServletContext().getRealPath("/upload/notice/");
		int maxSize=1024*1024*10;
		MultipartRequest mr=new MultipartRequest(request,path,maxSize,"UTF-8",new DefaultFileRenamePolicy());
		
		Notice n=Notice.builder()
				.noticeNo(Integer.parseInt(mr.getParameter("noticeNo")))
				.noticeTitle(mr.getParameter("noticeTitle"))
				.noticeWriter(mr.getParameter("writer"))
				.noticeContent(mr.getParameter("content"))
				.build();
		//파일이 전송됐는지 확인하고 전송이 됐으면 이전 파일을 삭제하고
		//전송되지 않았으 이전파일을 넣어야함
		File f=mr.getFile("upFile");
		if(f!=null&&f.length()>0) {	//data가 넘어왔으면
			//이전파일 삭제
			File deleteFile=new File(path+mr.getParameter("oriFile"));	//jsp에서 oriFile넘김
			deleteFile.delete(); //이전파일 지움
			n.setFilePath(mr.getFilesystemName("upFile"));
		}else {
			//업로드 파일이 없으면
			n.setFilePath(mr.getParameter("oriFile"));
		}
		
		//이제서야 업데이트 구문을 쓸 수 있음 파일업로드가 쉽지 않네
		int result=new NoticeService().updateNotice(n);
		
		String msg="";
		String loc="";
		if(result>0) {
			msg="공지사항 수정 완료";
			loc="/notice/contentView.do?noticeNo="+n.getNoticeNo();
		}else {
			msg="공지사항 수정 실패";
			loc="/notice/noticeUpdate.do?noticeNo="+n.getNoticeNo();
		}
		request.setAttribute("msg", msg);
		request.setAttribute("loc", loc);
		request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
