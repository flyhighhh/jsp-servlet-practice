package com.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.common.AESCryptor;
import com.member.model.service.MemberService;
import com.member.model.vo.Member;

/**
 * Servlet implementation class MemberViewServlet
 */
@WebServlet("/member/memberView.do")
public class MemberViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberViewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 클라이언트가 보낸 아이디값을 기준으로 정보를 조회해서 화면에 보내주는 기능
		String userId=request.getParameter("userId");
		
		//2. 주소창에서 바로 접근하지 못하도록 설정해줘야함
		//잘못된 접근에 대한 처리
		HttpSession session=request.getSession();
		Member loginMember=(Member)session.getAttribute("loginMember");
		

//		if(loginMember==null||!(loginMember.getUserId().equals(userId)
//				||loginMember.getUserId().equals("admin"))) {	//잘못접근한거
//			//잘못된 접근에 대한 메시지를 출력, 메인화면으로 전환
//			request.setAttribute("msg", "시스템경고: 잘못된 경로로 접근하셨습니다!");
//			request.setAttribute("log", "/");
//			request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);
//		}else {
		//위에꺼대신 필터를 이용하는 방법
		
		
		
		
		System.out.println(userId);		
		
		Member m=new MemberService().checkIdDuplicate(userId);
		try {			
			m.setPhone(AESCryptor.decrypt(m.getPhone()));
			m.setEmail(AESCryptor.decrypt(m.getEmail()));
			m.setAddress(AESCryptor.decrypt(m.getAddress()));
		}catch(Exception e){
			e.printStackTrace();
		}
	
		request.setAttribute("member", m);
		
		
		request.getRequestDispatcher("/views/member/memberView.jsp")
		.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
