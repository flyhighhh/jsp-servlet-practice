package com.notice.controller;

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
 * Servlet implementation class NoticeWriteEndServlet
 */
@WebServlet("/notice/noticeWriteEnd.do")
public class NoticeWriteEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeWriteEndServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//공지사항 저장하기
		//파일 업로드 처리하기
		
		//파일 업로드가 된 요청인지 아닌지 판단
		if(!ServletFileUpload.isMultipartContent(request)) {
			//파일 업로드 요청을 해야하는데 그 요청이 아닐 때 예외처리
			request.setAttribute("msg", "공지사항 작성오류 [form:enctype] 관리자에게 문의하세요! :(");
			request.setAttribute("loc", "/notice/noticeList.do");
			request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);
			return;
		}
		//multipart로 보낸 요청에 대해 처리하기
		//cos.jar를 이용해서 처리해보자 -> 클라이언트가 전송한 파일을 저장하기
		//1. 파일을 저장할 위치 정보를 가져온다 -> 절대경로를 가져와야함.
			//서블릿안에서는 getServletContext() 로 절대경로 가져올 수 있음
		String path=request.getServletContext().getRealPath("/upload/notice/");
		System.out.println(path);
		
		//2. 저장할파일의 크기를 설정 -> 요청정보를 받을 때 크기
		//byte -> KB -> MB -> GB -> TB
		int maxSize=1024*1024*10; //10MB, cos는 2기가까지 지원
		
		//3. 문자열에 대한 인코딩 설정
		String encode="UTF-8";
		
		//업로드된 파일명에 대한 파일명 재정의 (rename)
		//개발자가 커스터마이징해서 사용, cos에서 기본제공하는 클래스를 이용할 수 있음
		//DeaultFileRenamePolicy
		
		//파일 업로드 및 사용자가 보낸 데이터 처리하기
		//cos.jar에서 제공하는 MultipartRequest클래스를 이용함
		//MultipartRequest클래스의 매개변수 있는 생성자를 이용해서 생성하면 파일이 자동 업로드 됨
		//매개변수 1 : 요청정보(HttpServletRequest)
		//매개변수 2 : 저장경로
		//매개변수 3 : 파일의 최대크기
		//매개변수 4 : 문자열 인코딩 설정
		//매개변수 5 : 파일 Rename 설정 클래스
		//MultipartRequest를 생성 후 업로드 파일을 제외한 클라이언트가 보낸 데이터는 
		//MultipartRequest클래스를 이용해서 가져와야한다.
		//getParameter()메소드를 MultiparRequest에서 실행
		
		//한번 생성해보자구
		//업로드처리하자
		MultipartRequest mr=new MultipartRequest(request,path,maxSize,encode,new DefaultFileRenamePolicy());
		//파일 업로드 끝
		
		//DB에 저장할 데이터 가져오기
		//mr.getParameter();
		
		Notice n=Notice.builder()
				.noticeTitle(mr.getParameter("noticeTitle"))
				.noticeWriter(mr.getParameter("writer"))
				.noticeContent(mr.getParameter("content"))
				.filePath(mr.getFilesystemName("upFile"))
				.build();
		
		//저장된 파일이름을 불러오려면 getFilesystemName("file타입의 name값");
		System.out.println(n);
		
		int result=new NoticeService().insertNotice(n);
		String msg="";
		String loc="";
		if(result>0) {
			msg="공지사항 등록 성공";
			loc="/notice/noticeView.do";
		}else {
			msg="공지사항 등록 실패";
			loc="/notice/noticeWrite.do";
		}
		request.setAttribute("msg",msg);
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
