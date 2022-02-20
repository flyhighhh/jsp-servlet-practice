<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String msg=(String)request.getAttribute("msg");
	String loc=(String)request.getAttribute("loc");
	String script=(String)request.getAttribute("script");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<script>
		alert('<%=msg%>');	//alert(로그인실패,다시시도하세요); 문구를 출력하려면 ''로 묶어줘야함
		location.replace("<%=request.getContextPath()%><%=loc%>");
		<%=script!=null?script:""%>	//close();랑 똑같음
	</script>
</body>
</html>