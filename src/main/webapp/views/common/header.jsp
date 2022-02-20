<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.member.model.vo.Member,com.common.listener.LoginCheckListener"%>
<%
	//Member loginMember=(Member)request.getAttribute("loginMember");
	Member loginMember=(Member)session.getAttribute("loginMember");
	
	String saveId=null;
	Cookie[] cookies=request.getCookies();
	if(cookies!=null){
		for(Cookie c : cookies){
			if(c.getName().equals("saveId")) {
				saveId=c.getValue();
				break;
			}
		}
	}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>HelloMVC프로젝트</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath() %>/css/style.css">
<script src="<%=request.getContextPath() %>/js/jquery-3.6.0.min.js"></script>
</head>
<body>
	<div id="container"></div>
	<header>
		<h1>Hello MVC</h1>
		<div class="login-container">
			<%if(loginMember==null){ %>
			<form id="loginFrm" action="<%=request.getContextPath() %>/login.do"
				method="post">
				<table>
					<tr>
						<td><input type="text" id="userId" name="userId" placeholder="아이디"
							 value="<%=saveId!=null?saveId:""%>"/></td>
						<td></td>
					</tr>
					<tr>
						<td><input type="password" id="password" name="password"
							placeholder="비밀번호" /></td>
						<td><input type="submit" value="로그인" /></td>
					</tr>
					<tr>
						<td colspan="2">
							<input type="checkbox" name="saveId" id="saveId"
								<%=saveId!=null?"checked":""%>> 
							<label for="saveId">아이디저장</label>&nbsp&nbsp
							<input type="button" value="회원가입" onclick="location.assign('<%=request.getContextPath()%>/member/enrollMember.do');">
						</td>											<!-- location.replace해도 됨 -->
					</tr>
				</table>
			</form>
			<%}else{ %>
				<table id="logged-in">
					<tr>
						<td colspan="2">
							<%=loginMember.getUserName() %>님, 환영합니다.
						</td>
					</tr>
					<tr>
						<td colspan="2">
							접속한 회원 수: <%=LoginCheckListener.getCount() %>
						</td>
					</tr>
					<tr>
						<td>
							<input type="button" value="내정보보기" onclick="memberView();">
							<%-- onclick="location.assign('<%=request.getContextPath()%>/memberView.do')" --%>
						</td>
						<td>
							<input type="button" value="로그아웃" 
								onclick="location.replace('<%=request.getContextPath()%>/logout.do');">
						</td>
					</tr>
				</table>
			<%} %>
		</div>
		<nav>
			<ul class="main-nav">
				<li class="home"><a href="">HOME</a></li>
				<li id="notice"><a href="<%=request.getContextPath()%>/notice/noticeView.do">공지사항</a></li>
				<li id="board"><a href="<%=request.getContextPath()%>/board/boardList.do">게시판</a></li>
				<li id="gallery"><a href="">갤러리</a></li>
				<%if(loginMember!=null&&loginMember.getUserId().equals("admin")){ %>
				<li id="memberManage"><a href="<%=request.getContextPath()%>/admin/memberList.do">회원관리</a></li>
				<%} %>
			</ul>
		</nav>
	</header>
	
	
	<script>
	//스크립트 이용하면 이렇게 쓸 수 있음 근데 위에 쓰는게 더 좋음
	<%-- $(()=>{
		if(<%=loginMember!=null&&loginMember.getUserId().equals("admin")%>){
			$("#memberManage").show();
		}
	} --%>
	
	const memberView=()=>{
		//회원정보를 보여주는 Servlet를 호출
		//쿼리스트링방식으로 서버에 데이터전송하기 (get방식으로 전송)
		//servlet주소?key=vlaue&key=value.....
		location.assign("<%=request.getContextPath()%>/member/memberView.do?userId=<%=loginMember!=null?loginMember.getUserId():""%>")
	}
	</script>
	
	
	
	
	
	
	
	
	