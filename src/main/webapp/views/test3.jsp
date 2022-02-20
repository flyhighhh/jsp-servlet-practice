<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Document</title>
<script src="http://code.jquery.com/jquery-3.6.0.min.js"></script>

<style>
div#login-container {
	display: flex;
	justify-content: right;
}
div#menu-container>ul{
	display:flex;
	justify:space-around;
	line-height: 40px;
}
div#menu-container>ul>li {
	display: inline-block;
	margin-right: 20px;
	height: 40px;
	width: 20rem;
	
	text-align: center;
	

}

div#menu-container>ul>li>a {
	font-size: 20px;
	font-weight: bolder;
	text-decoration: none;
	color: cornflowerblue;
	text-align: center;
}

div#menu-container>ul>li:hover {
	background: purple;
}
</style>



</head>
<body>
	<header>
		<div id="login-container">
			<form method="get"
				action="<%=request.getContextPath() %>/member/login.do">
				<input type="text" name="userId"><br> <input
					type="password" name="pw"><br> <input type="submit"
					value="로그인"><br>
			</form>
		</div>
		<div id="menu-container">
			<ul>
				<li><a href="<%=request.getContextPath()%>">home</a></li>
				<li><a href="<%=request.getContextPath()%>/board/boardList.do">게시판</a></li>
				<li><a
					href="<%=request.getContextPath()%>/gallary/gallaryList.do">갤러리</a></li>
				<li><button id="search-btn">조회</button></li>
			</ul>
		</div>

		<div id="data"></div>


	</header>
	<script>
		$("#search-btn").click(e=>{
			$.ajax({
			url: "/warehouse.do",
			dataType: "json",
			success: data=>{
				const table=$("<table>");
				const header="<tr><th>제목</th><th>내용</th><th>작성자</th>";
				table.append(header);
				for(let i=0; i<data.length; i++){
					let tr=$("<tr>");
					let title=$("<td>").html(data[i]["title"]);
					let content=$("<td>").html(data[i]["content"]);
					let writer=$("<td>").html(data[i]["writer"]);
					tr.append(title).append(content).append(writer);
					table.append(tr);
				}
				$("#data").html(table);
			}
			});
		});
	
	</script>
</body>
</html>