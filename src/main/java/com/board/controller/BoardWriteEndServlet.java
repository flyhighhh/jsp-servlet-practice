package com.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.board.model.service.BoardService;
import com.board.model.vo.Board;
import com.common.MyRenamed;
import com.oreilly.servlet.MultipartRequest;

/**
 * Servlet implementation class BoardWriteEndServlet
 */
@WebServlet("/board/boardWriteEnd.do")
public class BoardWriteEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardWriteEndServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(!ServletFileUpload.isMultipartContent(request)) {
			request.setAttribute("msg", "잘못된 요청입니다 관리자에게 문의하세요");
			request.setAttribute("loc", "/board/boardWrite.do");
			request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);
			return; //안하면 exception이 뜸
		}
		
		//파일 업로드 처리
		String path=getServletContext().getRealPath("/upload/board/");
		MultipartRequest mr=new MultipartRequest(request,path,(1024*1024*10),"UTF-8",new MyRenamed());
		
		//파일명에 대한 리네임을 커스터마이징하기
		
		Board b=Board.builder()
				.boardTitle(mr.getParameter("boardTitle"))
				.boardWriter(mr.getParameter("boardWriter"))
				.boardContent(mr.getParameter("boardContent"))
				.originalFileName(mr.getOriginalFileName("upfile"))	//어떤파일을 올렸는지 가지고 있는거
				.renamedFileName(mr.getFilesystemName("upfile"))
				.build();

		int result=new BoardService().insertBoard(b);
		String msg="";
		String loc="";
		if(result>0) {
			msg="게시물 등록 성공";
			loc="/board/boardList.do";
		}else {
			msg="게시물 등록 실패";
			loc="/board/boardWrite.do";
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
