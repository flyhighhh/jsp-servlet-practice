package com.admin.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.admin.model.service.AdminService;
import com.common.AESCryptor;
import com.member.model.vo.Member;

/**
 * Servlet implementation class MemberListServlet
 */
@WebServlet("/admin/memberList.do")
public class MemberListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. DB에 접속해서 member테이블에 있는 모든 값을 가져온다
		//List<Member> mList=new AdminService().viewAllMember();
		
		//데이터에 대한 페이지 처리하기
		//String param=request.getParameter("cPage");
		//int cPage=1;
//		if(param!=null) {
//			cPage=Integer.parseInt(request.getParameter("cPage")); //현재페이지
//		}
		//try~catch문으로도 할 수 있음
		int cPage;
		try {
			cPage=Integer.parseInt(request.getParameter("cPage"));
		}catch(NumberFormatException e) {
			cPage=1;
		}
		
		int numPerPage=10;//페이지당 출력 데이터수
		
		List<Member> mList=new AdminService().viewAllMember(cPage,numPerPage);
		System.out.println(mList);
		
		//pageBar작성하기
		int totalData=new AdminService().selectMemberAllCount();
		//전체페이지수 가져옴
		int totalPage=(int)Math.ceil((double)totalData/numPerPage); //소수점이 나오면 날라가니까 올림처리
		//페이지에 출력할 페이지 수의 갯수 정하기
		int pageBarSize=5;
		
		//페이지 숫자의 시작 값을 설정
		int pageNo=((cPage-1)/pageBarSize)*pageBarSize+1;
		//페이지 숫자의 끝 값을 설정
		int pageEnd=pageNo+pageBarSize-1;
		
		String pageBar="";
		
		//이전버튼 만들기
		if(pageNo==1) {
			pageBar="<span>[이전]</span>";
		}else {
			pageBar="<a href='"+request.getContextPath()+"/admin/memberList.do?cPage="+(pageNo-1)+"'>[이전]</a>";
		}
		
		//while(pageNo<=pageEnd&&pageNo<=totalPage){
		while(!(pageNo>pageEnd||pageNo>totalPage)) {
			if(cPage==pageNo) {
				pageBar+="<span>"+pageNo+"</span>"; //클릭안되게
			}else {
				pageBar+="<a href='"+request.getContextPath()+"/admin/memberList.do?cPage="+pageNo+"'>"+pageNo+"</a>";
			}
			pageNo++;
		}
		if(pageNo>totalPage) {
			pageBar+="<span>[다음]</span>";
		}else {
			pageBar+="<a href='"+request.getContextPath()+"/admin/memberList.do?cPage="+pageNo+"'>[다음]</a>";
		}
		System.out.println(pageBar);
		
		//생성된 페이지버튼을 프론트로 전달
		request.setAttribute("pageBar", pageBar);
		
		
		for(Member m : mList) {
			try {
				m.setEmail(AESCryptor.decrypt(m.getEmail()));
				m.setPhone(AESCryptor.decrypt(m.getPhone()));
				m.setAddress(AESCryptor.decrypt(m.getAddress()));
			}catch(Exception e) {
				//e.printStackTrace();
			}
		}
		
		
		//2. 가져온 데이터를 화면단(JSp)에 전달
		// 데이터를 공유할 수 있는 객체에 저장 -> session, servletContext, request.
		request.setAttribute("mList", mList);
		
		
		//3. 화면단을 선택해서 전환
		request.getRequestDispatcher("/views/admin/memberList.jsp").forward(request, response);
		
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
