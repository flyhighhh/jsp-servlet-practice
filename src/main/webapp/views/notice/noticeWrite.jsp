<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/views/common/header.jsp" %>
<%@page import="com.member.model.vo.Member" %>
<%
	Member m=(Member)request.getAttribute("member");
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
    <form action="<%=request.getContextPath() %>/notice/noticeWriteEnd.do" method="post" enctype="multipart/form-data">
        <table id="tbl-notice">
        <tr>
            <th>제 목</th>
            <td><!-- 반드시 작성되야함. -->
            	<input type="text" name="noticeTitle">
            </td>
        </tr>
        <tr>
            <th>작성자</th>
            <td><!-- 반드시 있어야하고 로그인한 사용자 아이디가 들어가야함. 작성못하게! -->
            	<input type="text" name="writer" required readonly value="<%=loginMember.getUserId()%>">
            </td>
        </tr>
        <tr>
            <th>첨부파일</th>
            <td><!-- 파일을 입력받아야함 cos라이브러리 추가-->
            	<input type="file" name="upFile">
            </td>
        </tr>
        <tr>
            <th>내 용</th>
            <td>
            	<textarea name="content" rows="8" cols="40" style="resize:none"></textarea>
            </td>
        </tr>
        <tr>
            <th colspan="2">
                <input type="submit" value="등록하기" onclick="">
            </th>
        </tr>
    </table>
    </form>
    </div>
</section>


<%@include file="/views/common/footer.jsp" %>