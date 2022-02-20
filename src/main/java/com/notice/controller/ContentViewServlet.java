package com.notice.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.notice.model.service.NoticeService;
import com.notice.model.vo.Notice;

/**
 * Servlet implementation class ContentViewServlet
 */
@WebServlet("/notice/contentView.do")
public class ContentViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContentViewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//클릭한 제목에 일치하는 내용의 데이터를 찾아 출력
		int noticeNo=Integer.parseInt(request.getParameter("noticeNo"));	//parseInt해줘야지
		
		Notice n= new NoticeService().selectNotice(noticeNo);
		
		request.setAttribute("notice", n);
		request.getRequestDispatcher("/views/notice/noticeContentView.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
