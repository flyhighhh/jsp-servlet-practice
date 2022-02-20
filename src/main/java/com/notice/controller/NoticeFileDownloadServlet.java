package com.notice.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class NoticeFileDownloadServlet
 */
@WebServlet("/notice/fileDownload.do")
public class NoticeFileDownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeFileDownloadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//파일 다운로드 로직
		//1. 클라이언트가 보낸 파일정보를 받기(파일명)
		//2. 파일명이랑 일치하는 파일을 서버의 저장경로에서 가져오기
		//3. 클라이언트에게 파일명에 대해 인코딩처리하기 -> 한글 파일명은 꺠지지 않게 하려고 함
		//4. 클라이언트에게 파일을 전송함
			//1) 응답 MIME타입 설정, HttpResponseHeader설정
			//2) 클라이언트 스트림에 파일을 출력 -> 전송! 파일스트림 전송!
		
		//다운로드 해볼까?
		//클라이언트가 보낸 파일 가져오기
		String fileName=request.getParameter("fileName");	//noticeWrite.jsp <a>태그에서 보낸 값 가져오기
		
		//저장경로 가져오기
		String path=request.getServletContext().getRealPath("/upload/notice/");
		String filePath=path+fileName;
		
		//설정된 파일경로+파일명에 스트림연결하기 (불러오기)
		FileInputStream fis=new FileInputStream(filePath);
		//성능 향상을 ㅜ위해 보조스트림 이용하기
		BufferedInputStream bis=new BufferedInputStream(fis);
		
		//파일명 인코딩처리하기
		String fileRename="";
		//HttpRequest에서 구성한 Header에 대한 정보를 가져올 떄는 GETHEADER()메소드를 이용, 크롬 Network탭에서 볼 수 있
		String header=request.getHeader("user-agent");
		//익스플로러와 다른 브라우저 인코딩 방식이 달라 따로 처리해줘야함
		boolean isMSIE=header.contains("MSIE")||header.contains("Trident");
		if(isMSIE) {
			fileRename=URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+", "%20");
		}else {
			fileRename=new String(fileName.getBytes("utf-8"),"ISO-8859-1");
		}
		
		//클라이언트에게 보낼 스트림생성
		ServletOutputStream sos=response.getOutputStream();
		BufferedOutputStream bos=new BufferedOutputStream(sos);
		
		//응답메시지 설정
		response.setContentType("application/octet-stream");	//어플리케이션은 특정한 데서 사용, 정해지지 않은 파일임(octet)
		//다운로드시 파일명 설정, 다운로드 관련 설정
		response.setHeader("Content-disposition", "attachment;filename="+fileRename);
		
		//파일전송하기
		int read=-1;
		while((read=bis.read())!=-1) {
			bos.write(read);
		}
		
		bis.close(); 	//buffer 닫아주기
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
