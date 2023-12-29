<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>      
<%@ include file="../include/header.jsp" %>	

<div class="container w-50 mt-5 p-5 shadow">
  <h4>글 수정</h4>
  <form action="<c:url value="modify.do"/>" method="post">
  	  <input type="hidden" name="viewPage" value="${pDto.viewPage}"/>
  	  <input type="hidden" name="searchType" value="${pDto.searchType}"/>
  	  <input type="hidden" name="keyword" value="${pDto.keyword}"/>
  	  <input type="hidden" name="cntPerPage" value="${pDto.cntPerPage}"/>
  	  
	  <div class="form-group">
	    <label for="bid">번호</label>        
	  	<input class="form-control" type="text" id="bid" 
	  		name="bid" value="${dto.bid}" readonly>
	  </div> 
	  <div class="form-group">
	     	<label for="subject">제목</label>     
	  	<input class="form-control" type="text" id="subject" 
	  		name="subject" value="${dto.subject}">      
	     </div>
	     <div class="form-group">
	     	<label for="contents">내용</label>
	     	<textarea class="form-control mt-2" 
	     		name="contents" id="contents"><c:out value="${dto.contents}"/></textarea>
	     </div>
	     <div class="form-group">
	     	<label for="writer">글쓴이</label>
	     	<input class="form-control mt-2" type="text" 
	     		id="writer" name="writer" value="${dto.writer}" disabled>
	     </div>
	     
	     <div class="text-center mt-3">
	     	<!-- button 태그의 type이 생략되면 form태그안에서는 submit이 된다. -->
	        <button id="btn-modify" class="btn btn-primary me-2">수정하기</button>   
	        <button type="button" id="btn-remove" class="btn btn-danger me-2">삭제하기</button>   
	        <button type="button" id="btn-list" class="btn btn-primary">리스트</button>   
	     </div>
     </form>
</div>
<script>
	// 아래의 람다식과 같다
/* 	$("#btn-list").click(function(){
		location.href="<c:url value='/board/list.do'/>";
	}); */
	
	// 람다식, =>(화살표 연산자), =>의 왼쪽 파라미터, 오른쪽 리턴값or명령코드
	$("#btn-list").click(()=>{
		location.href="<c:url value='/board/list.do?viewPage=${pDto.viewPage}&searchType=${pDto.searchType}&keyword=${pDto.keyword}&cntPerPage=${pDto.cntPerPage}'/>";
	});
	
	$("#btn-remove").click(()=>{
		location.href="<c:url value='/board/remove.do?bid=${dto.bid}&viewPage=${pDto.viewPage}&searchType=${pDto.searchType}&keyword=${pDto.keyword}&cntPerPage=${pDto.cntPerPage}'/>";
	});
</script>
</body>
</html>