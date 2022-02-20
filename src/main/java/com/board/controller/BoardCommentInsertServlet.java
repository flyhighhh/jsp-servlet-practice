package com.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.board.model.service.BoardService;
import com.board.model.vo.BoardComment;

/**
 * Servlet implementation class BoardCommentInsertServlet
 */
@WebServlet("/board/insertBoardComment.do")
public class BoardCommentInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardCommentInsertServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		BoardComment bc=BoardComment.builder()
				.boardCommentContent(request.getParameter("content"))
				.boardCommentWriter(request.getParameter("writer"))
				.boardRef(Integer.parseInt(request.getParameter("boardRef")))
				.boardCommentRef(Integer.parseInt(request.getParameter("boardCommentRef")))
				.boardCommentLevel(Integer.parseInt(request.getParameter("level")))
				.build();
		
		int result=new BoardService().insertBoardComment(bc);
		String msg="";
		String loc="/board/boardRead.do?boardNo="+bc.getBoardRef();
		if(result>0) {
			msg="댓글 등록 성공";
		}else {
			msg="댓글 등록 실패";
		}
		request.setAttribute("msg", msg);
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
