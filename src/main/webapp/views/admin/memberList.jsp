<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.member.model.vo.Member" %>
<%@ include file="/views/common/header.jsp" %>
<%
	List<Member> mList=(List)request.getAttribute("mList");	
	String searchType=request.getParameter("searchType");
	String keyword=request.getParameter("searchKeyword");
%>


<style type="text/css">
    section#memberList-container {text-align:center;}
    section#memberList-container table#tbl-member {width:100%; border:1px solid gray; border-collapse:collapse;}
    section#memberList-container table#tbl-member th, table#tbl-member td {border:1px solid gray; padding:10px; }
    section#memberList-container table#tbl-member th{background-color:#D7EBBA; }
    
    /* 검색창에 대한 스타일 */
    div#search-container {margin:0 0 10px 0; padding:3px; 
    background-color: #FEFFBE;}
    div#search-userId{display:inline-block;}
    div#search-userName{display:none;}
    div#search-gender{display:none;}
    div#numPerpage-container{float:right;}
    form#numperPageFrm{display:inline;}
    #pageBar>a,#pageBar>span {padding-right : 20px;}
</style>
    
    <section id="memberList-container">
        <h2>회원관리</h2>
        <div id="search-container">
        	검색타입 : 
        	<select id="searchType">
        		<option value="userId" <%=searchType!=null && searchType.equals("userId")?"selected":"" %>>아이디</option>
        		<option value="userName" <%=searchType!=null && searchType.equals("userName")?"selected":"" %>>회원이름</option>
        		<option value="gender" <%=searchType!=null && searchType.equals("gender")?"selected":"" %>>성별</option>
        	</select>
        	<div id="search-userId">
        		<form action="<%=request.getContextPath()%>/admin/searchMember">
        			<input type="hidden" name="searchType" value="userId" >
        			<input type="text" name="searchKeyword" size="25" 
        			placeholder="검색할 아이디를 입력하세요" 
        			value="<%=searchType!=null && searchType.equals("userId")?keyword:"" %>"> 
        					<!-- 검색창에 검색한게 남아있게 value -->
        			<button type="submit">검색</button>
        		</form>
        	</div>
        	<div id="search-userName">
        		<form action="<%=request.getContextPath()%>/admin/searchMember">
        			<input type="hidden" name="searchType" value="userName">
        			<input type="text" name="searchKeyword" size="25" 
        			placeholder="검색할 이름을 입력하세요"
        			value="<%=searchType!=null && searchType.equals("userName")?keyword:"" %>">
        			<button type="submit">검색</button>
        		</form>
        	</div>
        	<div id="search-gender">
        		<form action="<%=request.getContextPath()%>/admin/searchMember">
        			<input type="hidden" name="searchType" value="gender">
        			<label><input type="radio" name="searchKeyword" value="M" 
        					<%=searchType!=null && searchType.equals("gender") && keyword.equals("M")?"checked":"" %>>남</label>
        			<label><input type="radio" name="searchKeyword" value="F" 
        					<%=searchType!=null && searchType.equals("gender") && keyword.equals("F")?"checked":"" %>>여</label>
        			<button type="submit">검색</button>
        		</form>
        	</div>
        </div>
        <div id="numPerpage-container">
        	페이지당 회원수 : 
        	<form id="numPerFrm" action="">
        		<select name="numPerpage" id="numPerpage">
        			<option value="10">10</option>
        			<option value="5" >5</option>
        			<option value="3" >3</option>
        		</select>
        	</form>
        </div>
        
        
        <table id="tbl-member">
            <thead>
                <tr>
                 <th>아이디</th>
		    <th>이름</th>
		    <th>성별</th>
		    <th>나이</th>
		    <th>이메일</th>
		    <th>전화번호</th>
		    <th>주소</th>
		    <th>취미</th>
		    <th>가입날짜</th>
                </tr>
            </thead>
            <tbody>
            <%if(mList.isEmpty()) {%>
            	<tr>
            		<td colspan="9">조회된 데이터가 없습니다.</td>
            	</tr>
            <%}else{ %>
	       	    <%for(Member m: mList) {%>
	       	    	<!-- 기본for문하려면 mList.get(i).getUserId() -->
	       	    	<tr>
		       	    	<td><%=m.getUserId() %></td>
		       	    	<td><%=m.getUserName() %></td>
		       	    	<td><%=m.getGender() %></td>
		       	    	<td><%=m.getAge() %></td>
		       	    	<td><%=m.getEmail()%></td>
		       	    	<td><%=m.getPhone() %></td>
		       	    	<td><%=m.getAddress() %></td>
		       	    	<td><%=m.getHobby() %></td>
		       	    	<td><%=m.getEnrollDate() %></td>
	       	    	</tr>
	       	    <%}
	       	   } %>
            </tbody>
        </table>
        <div id="pageBar">
        	<%=request.getAttribute("pageBar") %>
        </div>
    </section>
    
    <script>
    	//타입을 고르면 원래있던 박스는 사라지고 옆에 뜸
    	$(()=>{
    		$("#searchType").change(e=>{
    			const value=$(e.target).val();
    			//console.log(value);
    			//console.log($("#seach-container>div[id^=seach]"));
    			
    			//$("#search-container>div[id^=search]").css("display","none");
    			
    			//아니면 하나하나 가져와도 됨
    			const userId=$("#search-userId");
    			const userName=$("#search-userName");
    			const gender=$("#search-gender");
    			userId.css("display","none");
    			userName.css("display","none");
    			gender.css("display","none");
    			
    			
    			$("div#search-"+value).css("display","inline-block");
    			
    		})
    	})
    	
    	//onload됐을 때
    	$(()=>{
    		$("#searchType").change(); //페이지 로드 후 바로 select태그에 Change이벤트 발생시킴
    	})
    
    </script>
    
    
    
    
    
    
    
    
    
    
    
    
    
    
 <%@include file="/views/common/footer.jsp" %>
