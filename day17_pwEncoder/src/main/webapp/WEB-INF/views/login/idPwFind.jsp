<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
      
<%@ include file="../include/header.jsp" %>
<div class="container w-50 p-5 mt-5">
  <!-- Nav tabs -->
  <ul class="nav nav-tabs" role="tablist">
    <li class="nav-item">
      <a class="nav-link ${find=='id' ? 'active':''}" data-bs-toggle="tab" href="#menu1">아이디 찾기</a>
    </li>
    <li class="nav-item">
      <a class="nav-link ${find=='pw' ? 'active':''}" data-bs-toggle="tab" href="#menu2">비밀번호 찾기</a>
    </li>
  </ul>

  <!-- Tab panes -->
  <div class="tab-content">
   <!-- 아이디 패널 -->
    <div id="menu1" class="container tab-pane fade ${find=='id' ? 'show active':''}"><br>
      <div id="findIdSuccess" style="display:none">
         <p>가입시 아이디는 <b><span id="findId"></span></b> 입니다.</p>
         <a href="<c:url value="login.do"/>" class="btn btn-primary mt-3 w-100" 
            type="button">로그인</a>
      </div>
      <div id="findIdBefore">
         <p>이름과 휴대폰번호를 입력하세요.</p>
         <input class="form-control mb-2" type="text" 
            id="name" name="name" placeholder="이름"/>
         <input class="form-control mb-2" type="text" 
            id="tel" name="tel" placeholder="전화번호"/>
         <input class="btn btn-primary mt-3 w-100" type="button" 
            value="아이디 찾기" onclick="idFind()"/>
      </div>
    </div>
    
   <!-- 비밀번호 패널 -->
    <div id="menu2" class="container tab-pane fade ${find=='pw' ? 'show active':''}"><br>      
      <div id="findPwSuccess" style="display:none">
         <p>
            <small>해당 이메일로 임시 비밀번호를 보냈습니다.
            로그인후 마이페이지에서 비밀번호를 변경하시기 바랍니다.
            </small>
         </p>
         <a href="<c:url value="login.do"/>" class="btn btn-primary mt-3 w-100" 
            type="button">로그인</a>
      </div>
      
      <div id="findPwBefore">
         <p>가입시 아이디와 이메일을 입력하시오.</p>
         <input class="form-control mb-2" type="text" 
            id="id" name="id" placeholder="아이디"/>
         <input class="form-control mb-2" type="text" 
            id="email" name="email" placeholder="이메일"/>
         <input class="btn btn-primary mt-3 w-100" type="button" 
            value="비밀번호 찾기" onclick="pwFind()"/>
      </div>
    </div>
  </div>   
  <script>
  function idFind(){
     let name = $("#name").val();
     let tel = $("#tel").val();
     
     $.ajax({
       url:"<c:url value='findId.do?name='/>"+name+"&tel="+tel,
       type:"post",
       // 파라미터 data는 서버에서 전송한 id
       success:function(data){  
          console.log(data); // 빈공백 "" 전송됨
          // 자바스크립트에서 빈공백("")과 0은 false와 같다
          // null과 ""은 다르다
          // if(data == null){ 이조건은 거짓
          if(data == false){ // data==0, data==""는 data == false 같다
             alert("이름 및 전화번호를 다시 확인하세요!!");
          }else{ // 아이디 찾기 성공
             console.log("success");
             $("#findIdBefore").css("display", "none");
             $("#findIdSuccess").css("display", "block");
              $("#findId").text(data);
          }
       },
       error:function(){alert("아이디찾기 요청 실패!!")}
     });
  }
     
  function pwFind(){
     let uid = $("#id").val();
     let uEmail = $("#email").val();
     
     $.ajax({
       url:"<c:url value='findPw.do?uid='/>"+uid+"&uEmail="+uEmail,
       type:"post",       
       success:function(data){  
          console.log(data); // 빈공백 "" 전송됨          
          if(data == 0){ 
             alert("아이디와 이메일을 다시 확인하세요!!");
          }else{ // 비번 찾기 성공
             console.log("success");
             $("#findPwBefore").css("display", "none");
             $("#findPwSuccess").css("display", "block");
          }
       },
       error:function(){alert("비밀번호찾기 요청 실패!!")}
     });
  }
  </script>    
</div>
<%@ include file="../include/footer.jsp" %>