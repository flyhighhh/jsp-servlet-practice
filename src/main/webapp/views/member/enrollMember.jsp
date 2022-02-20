<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/views/common/header.jsp" %>
<section id=enroll-container>
        <h2>회원 가입 정보 입력</h2>
        <form name="enrollMemberFrm" action="<%=request.getContextPath() %>/member/enrollMemberEnd.do" method="post" onsubmit="return memberEnrollValidate();" >
        <table>
			<tr>
				<th>아이디</th>
				<td>
					<input type="text" placeholder="4글자이상" name="userId" id="userId_" >
					<input type="button" value="중복검사" id="idDuplicateBtn">
				</td>
			</tr>
			<tr>
				<th>패스워드</th>
				<td>
					<input type="password" name="password" id="password_" ><br>
				</td>
			</tr>
			<tr>
				<th>패스워드확인</th>
				<td>	
					<input type="password" id="password_2" ><br>
					<span id="pwresult"></span>
				</td>
			</tr>  
			<tr>
				<th>이름</th>
				<td>	
				<input type="text"  name="userName" id="userName" ><br>
				</td>
			</tr>
			<tr>
				<th>나이</th>
				<td>	
				<input type="number" name="age" id="age"><br>
				</td>
			</tr> 
			<tr>
				<th>이메일</th>
				<td>	
					<input type="email" placeholder="abc@xyz.com" name="email" id="email"><br>
				</td>
			</tr>
			<tr>
				<th>휴대폰</th>
				<td>	
					<input type="tel" placeholder="(-없이)01012345678" name="phone" id="phone" maxlength="11" required><br>
				</td>
			</tr>
			<tr>
				<th>주소</th>
				<td>	
					<input type="text" placeholder="" name="address" id="address"><br>
				</td>
			</tr>
			<tr>
				<th>성별 </th>
				<td>
					<input type="radio" name="gender" id="gender0" value="M" >
					<label for="gender0">남</label>
					<input type="radio" name="gender" id="gender1" value="F">
					<label for="gender1">여</label>
				</td>
			</tr>
			<tr>
				<th>취미 </th>
				<td>
					<input type="checkbox" name="hobby" id="hobby0" value="운동"><label for="hobby0">운동</label>
					<input type="checkbox" name="hobby" id="hobby1" value="등산"><label for="hobby1">등산</label>
					<input type="checkbox" name="hobby" id="hobby2" value="독서"><label for="hobby2">독서</label><br />
					<input type="checkbox" name="hobby" id="hobby3" value="게임"><label for="hobby3">게임</label>
					<input type="checkbox" name="hobby" id="hobby4" value="여행"><label for="hobby4">여행</label><br />
				</td>
			</tr>
		</table>
		<input type="submit" value="가입" >
		<input type="reset" value="취소" >
<!-- 		onclick="return memberEnrollValidate();" -->
        </form>
        
        <form name="idDuplicateFrm">
        	<input type="hidden" name="userId">
        </form>
        
    </section>
    <script>
    	$(()=>{
    		$("#password_2").keyup(e=>{
    			if($(e.target).val().trim().length>3){
    				if($(e.target).val()==$("#password_").val()){
    					$("#pwresult").text("비밀번호가 일치합니다.").css({"color":"green"});
    				}else{
    					$("#pwresult").text("비밀번호가 불일치합니다.").css({"color":"red"});
    				}
    			}
    		});
    	});
    
    
    	const memberEnrollValidate=()=>{
    		//1. userId입력값이 4자리이상인지 확인
    		//2. password가 4글자 이상인지 확인
    		const userId=$("#userId_").val().trim();//공백을 빼고 처리
    		if(userId.length<4){
    			alert("아이디는 4글자 이상입력하세요!");
    			$("#userId_").focus();
    			return false;
    		}
    		const password=$("#password_").val().trim();
    		if(password.length<4){
    			alert("패스워드는 4글자 이상 입력하세요!");
    			$("#password_").focus();
    			return false;
    		}
    		
    	}
    
    	//아이디 중복확인하기
    	$(()=>{
    		$("#idDuplicateBtn").click(e=>{
    			const userId=$("#userId_").val().trim();
    			if(userId.length>=4){
    				const url="<%=request.getContextPath()%>/member/idDuplicate.do";
    				const title="idDuplcate";
    				const style="width=300,height=200";
    				open("",title,style);
    				//hidden form을 설정
    				//form태그는 name값으로 직접접근이 가능하다.
	   				console.log(idDuplicateFrm);//form태그
	   				console.log(idDuplicateFrm.userId);//form내부 input
	   				//form태그 자식 input태그의 value값 설정
	   				idDuplicateFrm.userId.value=userId;
	   				idDuplicateFrm.action=url;
	   				idDuplicateFrm.method="post";
	   				//생성한 윈도우에서 form을 실행하려면 form target속성을 이용
	   				idDuplicateFrm.target=title;
	   				
	   				idDuplicateFrm.submit();//form전송
	   				
	   				
    				
    			}else{
    				alert("아이디는 4글자 이상 작성해야합니다.");
    				$("#userId_").focus();
    			}
    		});
    	});
    	
    	
    	
    </script>
    

    
    
<%@ include file="/views/common/footer.jsp"%>