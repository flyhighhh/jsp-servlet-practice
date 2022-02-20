package com.board.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BoardFileDownloadServlet
 */
@WebServlet("/board/filedownload.do")
public class BoardFileDownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardFileDownloadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String oriName=request.getParameter("oriName");
		System.out.println(oriName);
		String reName=request.getParameter("reName");
		System.out.println(reName);
		String path=getServletContext().getRealPath("/upload/board/");
		BufferedInputStream bis=new BufferedInputStream(new FileInputStream(path+reName));
		
		//한글인코딩처리
		String header=request.getHeader("user-agent");
		boolean isMs=header.contains("Trident")||header.contains("MSIE");
		String sendReName="";
		if(isMs) {
			sendReName=URLEncoder.encode(oriName,"UTF-8");
			sendReName=sendReName.replaceAll("\\+", "%20");
		}else {
			sendReName=new String(oriName.getBytes("UTF-8"),"ISO-8859-1");
		}
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename="+sendReName);
		
		BufferedOutputStream bos=new BufferedOutputStream(response.getOutputStream());
		
		int read=-1;
		while((read=bis.read())!=-1) {
			bos.write(read);
		}
		bis.close();
		bos.close();
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
