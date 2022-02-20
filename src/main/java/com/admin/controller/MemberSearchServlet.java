package com.admin.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.admin.model.service.AdminService;
import com.member.model.vo.Member;

/**
 * Servlet implementation class MemberSearchServlet
 */
@WebServlet("/admin/searchMember")
public class MemberSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberSearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//클라이언트가 보낸 검색 타입(userId, userName, gender), 검색어를 받아와서
		//타입(컬럼)에 맞는 검색어가 있는지 확인하고 그 결과를 반환해주는 기능
		String searchType=request.getParameter("searchType");
		String keyword=request.getParameter("searchKeyword");
		
		//검색에 대한 페이지처리를 하자
		int cPage;
		try {
			cPage=Integer.parseInt(request.getParameter("cPage"));
		}catch(NumberFormatException e) {	//페이지에 0이 들어올 수 있으니까
			cPage=1;
		}
		int numPerpage=5; //5개의 자료씩 볼 수 있게
		
		//Map으로는 이렇게 할 수 있음
		Map<String,Object> param=Map.of("cPage",cPage, "numPerpage",numPerpage, 
												"searchType",searchType, "keyword",keyword);
		//List<Member> result= new AdminService().searchMember(param);
		
		List<Member> result= new AdminService().searchMember(cPage,numPerpage,searchType,keyword);
		
		int totalData=new AdminService().searchMemberCount(searchType,keyword);
		int totalPage=(int)Math.ceil((double)totalData/numPerpage);
		int pageBarSize=5;
		int pageNo=((cPage-1)/pageBarSize)*pageBarSize+1;
		int pageEnd=pageNo+pageBarSize-1;
		
		String pageBar="";
		if(pageNo==1) {
			pageBar+="<span>[이전]<span>";
		}else {
			pageBar+="<a href='"+request.getContextPath()
					+"/admin/searchMember?cPage=?"+(pageNo-1)
					+"&searchType="+searchType
					+"&searchKeyword"+keyword+"'>[이전]</a>";
		}
		while(!(pageNo>pageEnd||pageNo>totalPage)) {
			if(cPage==pageNo) {
				//페이지번호가 같으면 안눌리게
				pageBar+="<span>"+pageNo+"</span>";
			}else {
				pageBar+="<a href='"+request.getContextPath()
				+"/admin/searchMember?cPage=?"+(pageNo-1)
				+"&searchType="+searchType
				+"&searchKeyword"+keyword+"'>"
				+pageNo+"</a>";
			}
			pageNo++;
		}
		
		if(pageNo>totalPage) {
			pageBar+="<span>[다음]</span>";
		}else {
			pageBar+="<a href='"+request.getContextPath()
			+"/admin/searchMember?cPage=?"+(pageNo-1)
			+"&searchType="+searchType
			+"&searchKeyword"+keyword+"'>[다음]</a>";
		}
		request.setAttribute("pageBar", pageBar);
		
		
		//저장하기
		request.setAttribute("mList", result);
		
		//출력할화면 선택
		//
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
