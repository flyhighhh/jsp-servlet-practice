package com.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.member.model.vo.Member;

/**
 * Servlet Filter implementation class LoginCheckFilter
 */
@WebFilter(urlPatterns= {"/member/memberView.do"})
public class LoginCheckFilter implements Filter {

    /**
     * Default constructor. 
     */
    public LoginCheckFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// 1. login한 데이터를 가져와야함
		HttpSession session=((HttpServletRequest)request).getSession();
		Member loginMember=(Member)session.getAttribute("loginMember");
		String paramUserId=request.getParameter("userId");
		if(loginMember==null||
				!(loginMember.getUserId().equals(paramUserId)
						||loginMember.getUserId().equals("admin"))) {
			request.setAttribute("msg", "시스템메시지: 잘못된 경로로 접근하셨습니다.");
			request.setAttribute("loc", "/");
			request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);
		
		}else {
			chain.doFilter(request, response);	//다음으로 넘어가는 거 else문 안에 들어가야지 왔다갔다 오류 안남
			
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
