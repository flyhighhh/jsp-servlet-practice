<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	Member m=(Member)request.getAttribute("member");
	/* String[] checks=new String[5];
		for(String h : m.getHobby().split(",")){
		switch(h){
		case "운동" : checks[0]="checked"; break;
		case "등산" : checks[1]="checked"; break;
		case "독서" : checks[2]="checked"; break;
		case "게임" : checks[3]="checked"; break;
		case "여행" : checks[4]="checked"; break;
		}
	} */
%>
<%@ include file="/views/common/header.jsp" %>

<section id=enroll-container>
		<h2>회원 정보 수정</h2>
		<form id="memberFrm" method="post" >
			<table>
				<tr>
					<th>아이디</th>
					<td>
						<input type="text" name="userId" id="userId_" 
							value="<%=m.getUserId()%>" readonly>
					</td>
				</tr>
				<!-- <tr>
					<th>패스워드</th>
					<td>
						<input type="password" name="password" id="password_">
					</td>
				</tr>
				<tr>
					<th>패스워드확인</th>
					<td>	
						<input type="password" id="password_2"><br>
					</td>
				</tr>  --> 
				<tr>
					<th>이름</th>
					<td>	
					<input type="text"  name="userName" id="userName" required 
						value="<%=m.getUserName()%>"><br>
					</td>
				</tr>
				<tr>
					<th>나이</th>
					<td>	
					<input type="number" name="age" id="age" 
						value="<%=m.getAge()%>"><br>
					</td>
				</tr> 
				<tr>
					<th>이메일</th>
					<td>	
						<input type="email" placeholder="abc@xyz.com" name="email" id="email" 
							value="<%=m.getEmail()%>"><br>
					</td>
				</tr>
				<tr>
					<th>휴대폰</th>
					<td>	
						<input type="tel" placeholder="(-없이)01012345678" name="phone" id="phone" maxlength="11" 
							value="<%=m.getPhone() %>"><br>
					</td>
				</tr>
				<tr>
					<th>주소</th>
					<td>	
						<input type="text" placeholder="" name="address" id="address" 
							value="<%=m.getAddress()%>"><br>
					</td>
				</tr>
				<tr>
					<th>성별 </th>
					<td>
						<%if(m.getGender().equals("M")){ %>
							<input type="radio" name="gender" id="gender0" value="M" checked >
							<label for="gender0">남</label>
							<input type="radio" name="gender" id="gender1" value="F">
							<label for="gender1">여</label>
						<%}else{ %>
							<input type="radio" name="gender" id="gender0" value="M" >
							<label for="gender0">남</label>
							<input type="radio" name="gender" id="gender1" value="F" checked>
							<label for="gender1">여</label>
						<%} %>
					</td>
				</tr>
				<tr>
					<th>취미 </th>
					<td>
						<%-- <input type="checkbox" name="hobby" id="hobby0" value="운동" <%=checks[0] %>><label for="hobby0">운동</label>
						<input type="checkbox" name="hobby" id="hobby1" value="등산" <%=checks[1] %>><label for="hobby1">등산</label>
						<input type="checkbox" name="hobby" id="hobby2" value="독서" <%=checks[2] %>><label for="hobby2">독서</label><br />
						<input type="checkbox" name="hobby" id="hobby3" value="게임" <%=checks[3] %>><label for="hobby3">게임</label>
						<input type="checkbox" name="hobby" id="hobby4" value="여행" <%=checks[4] %>><label for="hobby4">여행</label><br /> --%>
						
						<input type="checkbox" name="hobby" id="hobby0" value="운동" <%=m.getHobby().contains("운동")?"checked":"" %>><label for="hobby0">운동</label>
						<input type="checkbox" name="hobby" id="hobby1" value="등산" <%=m.getHobby().contains("등산")?"checked":"" %>><label for="hobby1">등산</label>
						<input type="checkbox" name="hobby" id="hobby2" value="독서" <%=m.getHobby().contains("독서")?"checked":"" %>><label for="hobby2">독서</label><br />
						<input type="checkbox" name="hobby" id="hobby3" value="게임" <%=m.getHobby().contains("게임")?"checked":"" %>><label for="hobby3">게임</label>
						<input type="checkbox" name="hobby" id="hobby4" value="여행" <%=m.getHobby().contains("여행")?"checked":"" %>><label for="hobby4">여행</label><br />

					</td>
				</tr>
			</table>
			<input type="button" value="정보수정" onclick="memberUpdate();"/>
			<input type="button" value="비밀번호 변경" onclick="changePassword();">
			<input type="button" value="탈퇴" 
				onclick="location.assign('<%=request.getContextPath()%>/member/deleteAccount.do?userId=<%=loginMember.getUserId()%>')">
	</form>
</section>
<script>

	const changePassword=()=>{
		const url="<%=request.getContextPath()%>/member/changePassword.do?userId=<%=m.getUserId()%>";
		const style="width=400, height=210, top=200, left=500";
		open(url,"_blank",style);
	}


	const memberUpdate=()=>{
		//form전송하기
		const url="<%=request.getContextPath()%>/member/memberUpdate.do";
		$("#memberFrm").attr("action",url);
		//비밀번호를 반드시 입력해야지만 요청할 수 있음
		//비밀번호 미입력시 비밀번호 입력하세요 포커스해주기
		const password=$("#password_").val().trim();
		if(password.length!=0){
			$("#memberFrm").submit();
		}else{
			alert("패스워드는 반드시 입력하셔야합니다.")
			$("#password_").focus();
		};
		//패스워드 두개 일치하는지 해보기
	}

</script>






<%@ include file="/views/common/footer.jsp"%>