package com.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.common.AESCryptor;
import com.member.model.service.MemberService;
import com.member.model.vo.Member;

/**
 * Servlet implementation class MemberEnrollEndServlet
 */
@WebServlet(name="memberEnrollEnd", urlPatterns= {"/member/enrollMemberEnd.do"})
public class MemberEnrollEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberEnrollEndServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//클라이언트가 보낸 회원가입 정보를 받아와서 DB에 저장
		//저장 성공, 실패에 따라 페이지를 응답해줌
		String userId=request.getParameter("userId");
		String password=request.getParameter("password");
		String userName=request.getParameter("userName");
		String gender=request.getParameter("gender");
		int age=Integer.parseInt(request.getParameter("age"));
		String phone=request.getParameter("phone");
		String email=request.getParameter("email");
		String address=request.getParameter("address");
		//암호화처리하기
		try {
			phone=AESCryptor.encrypt(phone);
			email=AESCryptor.encrypt(email);
			address=AESCryptor.encrypt(address);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		String hobby=String.join(",",request.getParameterValues("hobby"));
		
		Member m=Member.builder()
				.userId(userId)
				.password(password)
				.userName(userName)
				.gender(gender)
				.age(age)
				.phone(phone)
				.email(email)
				.address(address)
				.hobby(hobby)
				.build();
		//DB에 접속해서 Member m저장하기!
		int result=new MemberService().insertMember(m);
		
		//응답화면 설정하기
		//알림 띄워주기
		String msg="";
		String loc="";
		if(result>0) {
			msg="회원가입 성공!";
			loc="/";
		}else {
			msg="회원가입 실패, 다시 시도하세요";
			loc="/member/enrllMember.do";
		}
		//request에 데이터 세팅하기
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
