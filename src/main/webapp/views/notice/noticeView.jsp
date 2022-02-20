<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.notice.model.vo.Notice"%>
<%
	List<Notice> noticeList=(List)request.getAttribute("noticeList");
%>

<%@ include file="/views/common/header.jsp" %>
 
 <style>
    section#notice-container{width:600px; margin:0 auto; text-align:center;}
    section#notice-container h2{margin:15px 0 0 0;}
    table#tbl-notice{width:100%; margin:0 auto; border:1px solid black; border-collapse:collapse;}
    table#tbl-notice th, table#tbl-notice td {border:1px solid; padding: 5px 0; text-align:center;}
    #pageBar>a,#pageBar>span {padding-right : 20px;} 
    .write-button{margin-bottom:10px; float:right;}
    body{background-color: #F2F3D9;}
</style>
<body>
<section id="notice-container">
        <h2>공지사항</h2>
        <%if(loginMember!=null && loginMember.getUserId().equals("admin")) {%>
        	<input class="write-button" type="button" value="공지사항 작성" 
        		onclick="location.assign('<%=request.getContextPath() %>/notice/noticeWrite.do')">
        <%} %>
        <table id="tbl-notice">
            <tr>
            
                <th>번호</th>
                <th>제목</th>
                <th>작성자</th>
                <th>첨부파일</th>
                <th>작성일</th>
            </tr>
            <%if(noticeList.isEmpty()) {%>
           	<tr>
            	<td colspan="5">등록된 공지사항이 없습니다.</td>
            </tr>
            <%}else{ %>
            	<%for(Notice n: noticeList) {%>
            	<tr>
            		<td><%=n.getNoticeNo() %></td>
            		<td>
            			<a href="<%=request.getContextPath() %>/notice/contentView.do?noticeNo=<%=n.getNoticeNo()%>"><%=n.getNoticeTitle() %></a>
            		</td>
            		<td><%=n.getNoticeWriter() %></td>
            		<td>
            		<%if(n.getFilePath()!=null) {%>
            			<img src="<%=request.getContextPath() %>/images/file.png" width="20" height="20"> 
            		<%}else {%>
            			첨부파일 없음
            		<%} %>
            		</td>
            		<td><%=n.getNoticeDate() %></td>
            	</tr>
            	<%} %>
            <%} %>
<!-- 	내용출력할것
	첨부파일 있으면 이미지, 없으면 공란으로 표시
	이미지파일은 web/images/file.png에 저장
	제목을 클릭하면 상세화면으로 이동 -->
        </table>
        <div id="pageBar">
        	<%=request.getAttribute("pageBar") %>
        </div>
</section>
</body>



<%@ include file="/views/common/footer.jsp" %>