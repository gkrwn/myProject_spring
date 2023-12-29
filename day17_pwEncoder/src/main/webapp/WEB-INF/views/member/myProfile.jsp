<%@page import="kr.ezen.triple888.domain.MemberDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../include/header.jsp" %>	
                                                 
<div class='container mt-5 d-flex'>                                                  
	<aside class="border-end" style="width:200px">
		<h4>마이 프로필</h4>
		<ul>
			<li><a href="">비밀번호 변경</a></li>
			<li><a href="">기타메뉴</a></li>
		</ul>
	</aside>
	<div class="container w-50">
		<h4>비밀번호 변경</h4>
		<input type="hidden" id="memberId" value="${sessionScope.loginDTO.id}"/>
		<input type="text" value="${sessionScope.loginDTO.pw}"/>
		
		<p id="pwChkMsg"></p>
		<input class="form-control mb-2" type="text" id="pw" name="pw" placeholder="현재비밀번호">
		<input class="form-control mb-2" type="text" id="newPw" name="newPw" placeholder="새비밀번호">
		<input class="form-control mb-2" type="text" id="confirmPw" name="confirmPw" placeholder="새비밀번호 확인">
		
		<div class="text-center">
			<button id="pwChangeBtn" class="btn btn-sm btn-success">비밀번호 변경</button>
		</div>
	</div>                         
</div>
<script>
   let pw="";
   let currentPwChk="";
   
   // 현재비밀번호 체크
   function pwCheck(){
      pw = $("#pw").val();
      
      $.ajax({
         url:"<c:url value='pwCheck.do'/>",
         type:"post",
         data:{"pw":pw},
         async:false, // 동기화 처리로 설정, 기본값 true
         success:function(result){
            console.log("result : " + result);
            if(result == "ok"){
               alert("@@@@@@@ 현재 비밀번호 확인 완료!!");
               currentPwChk=true;
            }else{
               alert("현재 비밀번호 다시 확인 요망!!");
               currentPwChk=false;
            }
         },
         error:function(){alert("현재 비밀번호 체크 요청 실패!!")}
      });
   }
   
   let newPwChk="";
   // 새비밀번호 유효성 체크
   $("#newPw").on("keyup", function(){
      let npw = $("#newPw").val();
      console.log("npw : " + npw);
      
      if(npw == ""){
         $("#pwChkMsg").text("새 비밀번호를 입력하세요");         
         newPwChk=false;
      }else if(npw.length < 4){
         $("#pwChkMsg").text("4자리 이상 입력하세요");
         newPwChk=false;
      
      /*
      새비밀번호를 다시 수정했을 때 새비밀번호확인과 일치하지 않아도
      비밀번호 변경요청이 처리되는 문제 발생 
      }else{
         $("#pwChkMsg").text("");
         newPwChk=true;
      }    
      */
      
      /*
      위의 문제 해결하기 위해 새비밀번호확인에 값이 있는지 없는지 판단
      }else{
         if($("#confirmPw").val()){ 확인 값이 있으면
            // 확인값과 비교해서 일치하지 않으면 newPwChk= false 설정
            if($("#confirmPw").val() != npw) newPwChk=false;
            
         }else{확인 값이 없으면 newPwChk=true 
            $("#pwChkMsg").text("");
            newPwChk=true;
         }
      }
      이 경우 새비밀번호를 다시 정상적으로 수정했을 경우
      비밀번호 변경 요청처리 안되는 문제 발생
      */
      
      }else{
         if($("#confirmPw").val()){
            if($("#confirmPw").val() != npw) {
               newPwChk=false;
               // 함수 종료
               return;
            }
            // 위의 문제 해결하기 위해서 확인값과 같으면 newPwChk=true설정
            newPwChk=true;
            
         }else{ 
            $("#pwChkMsg").text("");
            newPwChk=true;
         }
      }
      
      
   });
   
   let newConfirmPwChk="";
   // 새비밀번호 확인 체크
   $("#confirmPw").on("keyup", function(){
      let cpw = $("#confirmPw").val();
      console.log("@@@@ newPw : " + $("#newPw").val());
      if(cpw ==""){
         $("#pwChkMsg").text("확인 비밀번호를 입력하세요");
         newConfirmPwChk=false;
      }else if($("#newPw").val() !=cpw){
         $("#pwChkMsg").text("비밀번호가 일치하지 않습니다");
         newConfirmPwChk=false;
/*       }else{
         $("#pwChkMsg").text("");
         newConfirmPwChk=true;
      } */
      // 새비밀번호를 수정한후 확인값을 다시 새비밀번호와 일치시킨경우에
      // newPwChk값이 false이기 때문 비밀번호 변경요청이 안되는 문제를 해결하기 위해
      // newPwChk=true 설정
      }else if($("#newPw").val() == cpw){         
         $("#pwChkMsg").text("");
         newPwChk=true;
         newConfirmPwChk=true;
      } 
   });
   
   
   // 비밀번호 변경 요청
   $("#pwChangeBtn").on("click", function(){
      pwCheck();
      console.log("newConfirmPwChk=" + newConfirmPwChk);
      console.log("newPwChk=" + newPwChk);
      
      if(!currentPwChk){
         alert("##########현재 비밀번호를 확인하세요!!");
      }else if(!newPwChk){
         alert("새비밀번호를 다시 확인하세요!!");
      }else if(!newConfirmPwChk){
         alert("새비밀번호가 일치하지 않습니다.. 다시 확인하세요!!");
      }else if(currentPwChk && newPwChk && newConfirmPwChk){
         console.log("새비밀번호로 변경처리!!");
         let id = $("#memberId").val();
         let pw = $("#newPw").val();
         // member는 자바스크립트 객체, json 만들기
         let member = {"id":id, "pw":pw};
         
         $.ajax({
            url:"<c:url value="pwChange.do"/>",
            type:"post",
            // 자바스크립트 객체를 전송할 수 없기 때문에
            // JSON형태의 문자열 변환해줌
            data:JSON.stringify(member),  
            contentType:"application/json; charset=utf8",
            success:function(result){
               if(result > 0){
                  alert("비밀번호 변경 완료!! 새로운 비밀번호로 로그인 하세요!!");
                  location.href="<c:url value="logout.do"/>";
               }
            },
            error:function(){alert("비밀번호 변경 요청 실패!!")}
         });
         
      }
   });

</script>
                                                     
<%@ include file="../include/footer.jsp" %>