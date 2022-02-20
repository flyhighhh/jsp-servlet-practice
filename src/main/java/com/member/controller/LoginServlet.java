package com.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.model.service.MemberService;
import com.member.model.vo.Member;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(name="login",urlPatterns="/login.do")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public LoginServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
				throws ServletException, IOException {
		//요청에 대한 서비스를 제공!
		//로그인서비스 실행
		//요청자(client)로부터 아이디와 패스워드를 전달받아 
		//연결되어있는 DB의 Member테이블에 전달받은 아이디와 패스워드가 일치하는 회원이 있는지 확인하고
		//있으면 인증(로그인) 처리 , 없으면 인증실패 처리
		//처리결과를 사용자에게 알려줘(view)
		
		
		//1. 요청자가 보낸 데이터 받아오기(getParameter("이름")
		String userId=request.getParameter("userId");
		String password=request.getParameter("password");
		//2. DB에서 데이터 확인하기
		Member m=new MemberService().login(userId,password);
		
		//아이디 저장로직 구현하기
		//데이터 가져오기
		String saveId=request.getParameter("saveId");
		
		//checkbox에 value값을 넣지 않고 전송되면
		//check했을 때 on값이 반환
		//check안했을 때 null값 반환
		if(saveId!=null) {
			Cookie c = new Cookie("saveId",userId);
			c.setMaxAge(24*60*60*7); //7일동안 보관
			response.addCookie(c);
		}else {
			Cookie c = new Cookie("saveId",userId);
			c.setMaxAge(0);//쿠키삭제
			response.addCookie(c);
		}
		System.out.println(saveId);
		
		
		//3. 인증로직 처리하기
		//m이 null(인증실패)이거나 값이 있음(인증처리)
		if(m!=null) {
			//request.setAttribute("loginMember", m);
			//위에처럼 넣으면 안됨...
			//로그인한 사용자에 대한 정보를 session객체를 생성해서 저장한다.
			HttpSession session=request.getSession();
			session.setAttribute("loginMember",m);
			response.sendRedirect(request.getContextPath());
//			request.setAttribute("msg", "로그인성공");
//			request.setAttribute("loc", "/");
//			request.getRequestDispatcher("/views/common/msg.js")
//			.forward(request,response);
		}else {
			//로그인실패시 로직처리
			//로그인실패 메시지를 출력하고 메인화면으로 이동하게 로직 구성
			request.setAttribute("msg", "로그인 실패, 다시 시도하세요");
			request.setAttribute("loc","/");
			request.getRequestDispatcher("/views/common/msg.jsp").forward(request,response);
			
		}
		//4.사용자한테 보여줄 화면 선택!
		//주소창에 보이지 않게 sendredirect로???
		//request.getRequestDispatcher("/").forward(request,response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
