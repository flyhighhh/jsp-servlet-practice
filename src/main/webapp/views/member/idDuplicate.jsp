<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.member.model.vo.Member" %>
<%
	Member m=(Member)request.getAttribute("member");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디 중복 확인</title>
</head>
<body>
	<div id="checkId-container">
	<%if(m==null){ %>
			[<span><%=request.getParameter("userId") %></span>]는 사용가능합니다.	
			<br><br>
			<button type="button" id="btn">닫기</button>
	<%}else{ %>
			[<span id="duplicated"><%=m.getUserId() %></span>]는 사용중입니다.
			<br><br>
			<!-- 아이디 재입력창 구성 -->
			<form action="<%=request.getContextPath() %>/member/idDuplicate.do" method="post">
				<input type="text" name="userId" id="userId">
				<input type="submit" value="중복검사">
			</form>
	<%} %>
	</div>
	<script>
		const el=document.querySelector("#btn").addEventListener("click",e=>{
			const userId='<%=request.getParameter("userId")%>';
			opener.enrollMemberFrm.userId.value=userId;
			opener.enrollMemberFrm.password.focus();
			close();
		});
	</script>
</body>
</html>