<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@include file="/views/common/header.jsp"%>


<section id="content">
	<h2 align="center" style="margin-top:200px;">안녕하세요, MVC입니다.</h2>
	
	
	<!-- ajax를 이용해서 데이터 가져오기 -->
	이름조회 : <input type="text" id="seachKey"><button id="memberBtn">회원정보 가져오기</button>
	<div id="memberData"></div>
	
	<script>
		$("#memberBtn").click(e=>{
			$.ajax({
			url: "/06_HelloMVC/memberAjax.do",
			data: {"keyword": $("#searchKey").val()},
			success: data=>{
				const table=$("<table>");
				const header="<tr><th>아이디</th><th>이름</th><th>나이</th><th>성별</th><th>이메일</th></tr>";
				table.append(header);
				for(let i=0; i<data.length; i++){
					let tr=$("<tr>");
					let id=$("<td>").html(data[i]["userId"]);
					let name=$("<td>").html(data[i]["userName"]);
					let age=$("<td>").html(data[i]["age"]);
					let gender=$("<td>").html(data[i]["gender"]);
					let email=$("<td>").html(data[i]["email"]);
					tr.append(id).append(name).append(age).append(gender).append(email);
					table.append(tr);
				}
				$("#memberData").html(table);
				$("#searchKey").val("");
			}
		});
			});
	
	</script>
	
</section>

<%@include file="/views/common/footer.jsp"%>