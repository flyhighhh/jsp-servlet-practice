<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/views/common/header.jsp" %>
<%@page import="com.notice.model.vo.Notice" %>
<%
	Notice n=(Notice)request.getAttribute("notice");
%>

<style>
    section#notice-container{width:600px; margin:0 auto; text-align:center;}
    section#notice-container h2{margin:10px 0;}
    table#tbl-notice{width:500px; margin:0 auto; border:1px solid black; border-collapse:collapse; clear:both; }
    table#tbl-notice th {width: 125px; border:1px solid; padding: 5px 0; text-align:center;} 
    table#tbl-notice td {border:1px solid; padding: 5px 0 5px 10px; text-align:left;}
</style>

<section>
  <div id="notice-container">
  	<!-- 파일 업로드가 있는 폼에 대해서는 enctype을 아래처럼 써줘야함 (cos라이브러리 추가)-->
    <form action="<%=request.getContextPath() %>/notice/noticeUpdateEnd.do" method="post" enctype="multipart/form-data">
        <table id="tbl-notice">
        <tr>
            <th>제 목</th>
            <td><!-- 작성이랑 똑같은데 value값으로 넣어주기 -->
            	<input type="text" name="noticeTitle" value="<%=n.getNoticeTitle()%>">
            </td>
        </tr>
        <tr>
            <th>작성자</th>
            <td>
            	<input type="text" name="writer" required readonly value="<%=n.getNoticeWriter()%>">
            </td>
        </tr>
        <tr>
            <th>첨부파일</th>
            <td><!-- 파일을 입력받아야함 cos라이브러리 추가-->
            	<input type="file" name="upFile" ><span><%=n.getFilePath()%></span>
            </td>
            <input type="hidden" name="orifile" value="<%=n.getFilePath()%>">	<!-- 이전에 있던 파일 지워야하니까 벨류대신 이걸로 만듬 -->
            
        </tr>
        <tr>
            <th>내 용</th>
            <td>
            	<textarea name="content" rows="8" cols="40" style="resize:none"><%=n.getNoticeContent() %></textarea>
            </td>
        </tr>
        <tr>
            <th colspan="2">
                <input type="submit" value="수정완료" onclick="">
            </th>
        </tr>
    </table>
    	<input type="hidden" name="noticeNo" value="<%=n.getNoticeNo()%>">
    </form>
    </div>
</section>


<%@include file="/views/common/footer.jsp" %>