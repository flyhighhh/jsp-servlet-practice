package com.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.board.model.service.BoardService;
import com.board.model.vo.Board;
import com.board.model.vo.BoardComment;

/**
 * Servlet implementation class BoardReadServlet
 */
@WebServlet("/board/boardRead.do")
public class BoardReadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardReadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//클라이언트가 요청한 게시물을 DB에서 가져와 출력해주는 기능
		int boardNo=Integer.parseInt(request.getParameter("boardNo"));
		
		//클라이언트가 이미 읽은 글은 조회수가 증가되지 않게 설정하기
		//Cookie로 읽은 데이터 저장하기
		Cookie[] cookies=request.getCookies();
		String boardRead="";	//읽은 보드번호 변수(이전 게시글 번호들을 저장하는 변수)
		boolean isRead=false; //안읽은걸로 기본값 줌, 읽었으면 true
		
		//게시물 읽은 것에 대해 cookie에 boardRead키 값으로 읽은 게시물 번호를 저장!
		
		if(cookies!=null) {	//조회한 게시물이 있다는말
			for(Cookie c : cookies) {
				String name=c.getName();
				String value=c.getValue();
				if(name.equals("boardRead")) {
					boardRead=value;	//value값을 쿠키에다가 추가
					if(value.contains("|"+boardNo+"|")) { //숫자 문자로 만들어주기 ""+"" |로 구분시켜주기
						isRead=true;
						break;	//읽었으니까 더이상 돌필요 없음
					}
				}
				
			}
		}
		if(!isRead) {
			//이 게시글을 안 읽었다면
			Cookie c=new Cookie("boardRead", boardRead+"|"+boardNo+"|");
			c.setMaxAge(24*60*60); //1일동안 유지
			response.addCookie(c);
		}
		
		//화면을 본 내용에 대해 count 수를 증가시키는 로직 추가
		//DB의 board테이블의 해당 row의 boardReadCount컬럼을 1증가
		Board b=new BoardService().viewBoardContent(boardNo, isRead);
		
		
		//**게시글에 포함된 댓글 가져오기
		List<BoardComment> bcList=new BoardService().boardCommentList(boardNo);
		request.setAttribute("comments", bcList);
		
		
		//이렇게 보내면 같이 쓸때 누가 삭제했는 데이터가 사라지면 500에러 뜨니까 분기처리
		String view="";
		if(b==null) {
			view="/views/common/msg.jsp";
			request.setAttribute("mgs", "이 게시글이 존재하지 않습니다.");
			request.setAttribute("loc", "board/boardList.do");
		}else{
			view="/views/board/boardContent.jsp";
			request.setAttribute("board", b);	 
		}
		request.getRequestDispatcher(view).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
