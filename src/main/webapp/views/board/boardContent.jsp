<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/views/common/header.jsp" %>
<%@ page import="com.board.model.vo.*, java.util.List" %>
<%
	Board b=(Board)request.getAttribute("board");
	List<BoardComment> bcList=(List)request.getAttribute("comments");
%>

<style>
    section#board-container{width:600px; margin:0 auto; text-align:center;}
    section#board-container h2{margin:10px 0;}
    table#tbl-board{width:500px; margin:0 auto; border:1px solid black; border-collapse:collapse; clear:both; }
    table#tbl-board th {width: 125px; border:1px solid; padding: 5px 0; text-align:center;} 
    table#tbl-board td {border:1px solid; padding: 5px 0 5px 10px; text-align:left;}
    
     div#comment-container button#btn-insert{width:60px;height:50px; color:white;
    background-color:#3300FF;position:relative;top:-20px;}
        /*댓글테이블*/
    table#tbl-comment{width:580px; margin:0 auto; border-collapse:collapse; clear:both; } 
    table#tbl-comment tr td{border-bottom:1px solid; border-top:1px solid; padding:5px; text-align:left; line-height:120%;}
    table#tbl-comment tr td:first-of-type{padding: 5px 5px 5px 50px;}
    table#tbl-comment tr td:last-of-type {text-align:right; width: 100px;}
    table#tbl-comment button.btn-reply{display:none;}
    table#tbl-comment button.btn-delete{display:none;}
    table#tbl-comment tr:hover {background:lightgray;}
    table#tbl-comment tr:hover button.btn-reply{display:inline;}
    table#tbl-comment tr:hover button.btn-delete{display:inline;}
    table#tbl-comment tr.level2 {color:gray; font-size: 14px;}
    table#tbl-comment sub.comment-writer {color:navy; font-size:14px}
    table#tbl-comment sub.comment-date {color:tomato; font-size:10px}
    table#tbl-comment tr.level2 td:first-of-type{padding-left:100px;}
    table#tbl-comment tr.level2 sub.comment-writer {color:#8e8eff; font-size:14px}
    table#tbl-comment tr.level2 sub.comment-date {color:#ff9c8a; font-size:10px}
    /*답글관련*/
    table#tbl-comment textarea{margin: 4px 0 0 0;}
    table#tbl-comment button.btn-insert2{width:60px; height:23px; color:white; background:#3300ff; position:relative; top:-5px; left:10px;}
</style>
   
	<section id="board-container">
		<h2>게시판</h2>
		<table id="tbl-board">
			<tr>
				<th>글번호</th>
				<td><%=b.getBoardNo() %></td>
			</tr>
			<tr>
				<th>제 목</th>
				<td><%=b.getBoardTitle() %></td>
			</tr>
			<tr>
				<th>작성자</th>
				<td><%=b.getBoardWriter() %></td>
			</tr>
			<tr>
				<th>조회수</th>
				<td><%=b.getReadCount() %></td>
			</tr>
			<tr>
				<th>첨부파일</th>
				<td>
				 <%if(b.getOriginalFileName()!=null){%>
				 	<!-- fileDownload라는 함수 실행하겠다-->
				 	<a href="javascript:fn_fileDownload('<%=b.getOriginalFileName()%>',
				 		'<%=b.getRenamedFileName()%>')">
				 		<img src="<%=request.getContextPath()%>/images/file.png" width="20" height="20">
				 	</a>
				 <%} %>
				</td>
			</tr>
			<tr>
				<th>내 용</th>
				<td><%=b.getBoardContent() %></td>
			</tr>
			<%--글작성자/관리자인경우 수정삭제 가능 --%>
			<%if(loginMember!=null && loginMember.getUserId().equals("admin")) {%>
			<tr>
				<th colspan="2">
					<input type="button" value="수정하기" 
                	onclick="">
                			
                <input type="button" value="삭제하기" 
                	onclick="">
				</th>
			</tr>
			<%} %>
			
		</table>
		<!-- 댓글입력창 -->
		<div id="comment-container">
			<div class="comment-editor">
				<form action="<%=request.getContextPath()%>/board/insertBoardComment.do" method="post">
					<textarea name="content" rows="3" cols="50"></textarea>
					<!-- 개발자만 필요한 정보니까 hidden으로 -->
					<input type="hidden" name="level" value="1">
					<input type="hidden" name="writer" value="<%=loginMember!=null?loginMember.getUserId():""%>">
					<input type="hidden" name="boardRef" value="<%=b.getBoardNo()%>">
					<input type="hidden" name="boardCommentRef" value="0">	
					
					<button type="submit" id="btn-insert">등록</button>
				</form>
			</div>
		</div>
		<table id="tbl-comment">
			<%for(BoardComment bc : bcList) {
				if(bc.getBoardCommentLevel()==1) {%>
				
				<tr class="level1">
					<td>
						<sub class="comment-writer"><%=bc.getBoardCommentWriter() %></sub>
						<sub class="comment-date"><%=bc.getBoardCommentDate() %></sub>
						<br>
						<%=bc.getBoardCommentContent() %>
					</td>
					<td>
					<%if(loginMember!=null){ %>
						<button class="btn-reply" value="<%=bc.getBoardCommentNo()%>">답글</button>
						<%if(loginMember.getUserId().equals("admin")
							||loginMember.getUserId().equals(bc.getBoardCommentWriter()))%>
						<button class="btn-delete">삭제</button>
						
					<%} %>
					</td>
				</tr>
			<%}else{ %>
				<tr class="level2">
					<td>
						<sub><%=bc.getBoardCommentWriter() %></sub>
						<sub><%=bc.getBoardCommentDate() %></sub>
						<%=bc.getBoardCommentContent() %>
					</td>
					<td></td>
				</tr>
			<%} 
			}%>
		</table>
   
    </section>
    
    <script>
    	const fn_fileDownload=(oriName,reName)=>{
    		const url="<%=request.getContextPath() %>/board/filedownload.do";
    		const encode=encodeURIComponent(oriName);	//인코딩해서 보내기
    		location.assign(url+"?oriName="+encode+"&reName="+reName);
    	}
    	
    	$(()=>{
    		$(".comment-editor textarea[name=content]").focus(e=>{
    			if(<%=loginMember==null%>){
    				alert("로그인 후 이용이 가능합니다.");
    				$("#userId").focus();	
    			}
    		})
    		
    		//$(".btn-reply").one("click", e=>{
    		$(".btn-reply").click(e=>{
    			const test=$("#tbl-comment").find("form");
    			console.log(test);
    			//$(test).parent().css({"display":"none"}
    			$(test).parent().remove();
    			//버튼을 누른 tr밑에 입력창이 나오게 작성
    			const tr=$("<tr>");
    			//폼을 복사해서 쓰기
    			const form=$(".comment-editor>form").clone();
    			console.log(form);
    			//가져온폼 내용 바꿔주기
    			form.find("textarea").attr({"rows":"1"});
    			form.find("input[name=level]").val("2");
    			form.find("input[name=boardCommentRef]").val($(e.target).val());
    			form.find("button").removeAttr("id").addClass("btn-insert2");
    			
    			const td=$("<td>").attr({"colspan":"2"})
    			td.append(form);
    			tr.append(td);
    			
    			//$(e.target).parent().parent()
    			$(e.target).parents("tr").after(tr);
    			//parents는 내 위에 부모들 다
    			
    			//실행하고 이벤트 제거
    			$(e.target).off("click");
    			
    		});
    		
    		
    		
    	});
    	
    	
    </script>
    
    
 <%@include file="/views/common/footer.jsp" %>