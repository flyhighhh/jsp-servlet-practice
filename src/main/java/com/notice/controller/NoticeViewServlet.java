package com.notice.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.notice.model.service.NoticeService;
import com.notice.model.vo.Notice;

/**
 * Servlet implementation class NoticeViewServlet
 */
@WebServlet("/notice/noticeView.do")
public class NoticeViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeViewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//DB에서 데이터 가져오기 + 페이징 처리 -> 출력해주는 기능
		int cPage;
		int numPerPage=10;
		try {
			cPage=Integer.parseInt(request.getParameter("cPage"));
		}catch(NumberFormatException e) {
			cPage=1;
		}
		
		List<Notice> noticeList=new NoticeService().viewAllNotice(cPage,numPerPage);
		
		int totalData=new NoticeService().countAllNotice();
		
				
		//page바 만들기
		int totalPage=(int)Math.ceil((double)totalData/numPerPage);
		int pageBarSize=5;
		
		int pageNo=((cPage-1)/pageBarSize)*pageBarSize+1;
		int pageEnd=pageNo+pageBarSize-1;
		
		String pageBar="";
		
		if(pageNo==1) {
			pageBar="<span>[이전]</span>";
		}else {
			pageBar="<a href='"+request.getRequestURL()+"?cPage="+(cPage-1)+"'>[이전]</a>";
		}
		while(!(pageNo>pageEnd||pageNo>totalPage)) {
			if(cPage==pageNo) {
				pageBar+="<span>"+pageNo+"</span>"; //클릭안되게
			}else {
				pageBar="<a href='"+request.getRequestURL()+"?cPage="+(cPage-1)+"'>"+pageNo+"</a>";
			}
			pageNo++;
		}
		if(pageNo>totalPage) {
			pageBar+="<span>[다음]</span>";
		}else {
			pageBar="<a href='"+request.getRequestURL()+"?cPage="+(cPage-1)+"'>"+pageNo+"</a>";
		}
		
		//생성된 페이지버튼을 프론트로 전달
		request.setAttribute("pageBar", pageBar);
		//데이터를 공유할 수 있는 객체에 저장
		request.setAttribute("noticeList", noticeList);
		
		//게시판 화면으로
		request.getRequestDispatcher("/views/notice/noticeView.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
