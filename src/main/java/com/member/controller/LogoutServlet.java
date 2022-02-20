package com.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.model.vo.Member;

/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet("/logout.do")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//logout로직
		//session에 있는 데이터 삭제! -> session삭제
		//invalidate()
		HttpSession session=request.getSession(false);  //session id false: 있으면 가져오고 없으면 null
		if(session!=null) {
			session.invalidate(); //logout	
		}
		request.setAttribute("msg", "로그아웃 되었습니다.");
	    request.setAttribute("loc","/");
	    request.getRequestDispatcher("views/common/msg.jsp").forward(request, response);
	    //response.sendRedirect(request.getContextPath()); //main화면으로 이동
		
	    
	    
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
