<%@page import="kr.ezen.triple888.domain.MemberDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	MemberDTO dto 
	  = (MemberDTO)request.getAttribute("dto");
%>    
    
<%@ include file="../include/header.jsp" %>	
                                                 
<div class='container mt-5'>                                                  
<h2>회원 정보</h2> 
<form action='./memberUpdate.do' method='post'>          
 <input type='hidden' name='no' value='<%=dto.getNo()%>'>
	<table>                                                
		<thead>                                            
			<tr>                                           
				<th colspan='2'><%=dto.getName()%>님 회원 정보</th>              
			</tr>                                          
		</thead>                                           
		<tbody>                                            
			<tr>                                           
				<td>회원번호</td>                            
				<td><%=dto.getNo()%></td>     
			</tr>                                          
			<tr>                                           
				<td>아이디</td>                            
				<td><%=dto.getId()%></td>     
			</tr>                                          
			<tr>                                           
				<td>이름</td>                              
				<td><%=dto.getName()%></td>   
			</tr>                                          
			<tr>                                           
				<td>나이</td>                              
				<td><input type='text' name='age' value='<%=dto.getAge()%>'></td>    
			</tr>                                          
			<tr>                                           
				<td>이메일</td>                            
				<td><input type='text' name='email' value='<%=dto.getEmail()%>'></td>  
			</tr>                                          
			<tr>                                           
				<td>전화번호</td>                          
				<td><input type='text' name='tel' value='<%=dto.getTel()%>'></td>    
			</tr>                                          
			<tr>				                           
				<td colspan='2'>                           
					<input type='submit' value='수정하기' class='btn btn-primary'> 
					<input type='reset' value='취소' class='btn btn-warning'>      
					<a href='./memberList.do' class='btn btn-info'>리스트</a>      
				</td>                                      
			</tr>                                          
		</tbody>                                           
	</table>                                               
</form>                                                 
</div>                                                        
<%@ include file="../include/footer.jsp" %>