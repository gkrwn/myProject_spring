<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
      
<%@ include file="../include/header.jsp" %>
<div class="container w-50 mt-5 p-5 shadow">
   <form action="memberInsert.do" method="post" onSubmit="return submitChk()">
      <h4>회원가입</h4>            
     <!--  <div class="row">
         <div class="col-md-8 pe-0">
            <input class="form-control mb-2" type="text" id="id" name="id" placeholder="아이디">
         </div>
        <div class="col-md-4">
           <a class="btn btn-outline-info w-100" onclick="idCheck()">중복체크</a>
        </div>
      </div> -->
      
      <input class="form-control mb-2" type="text" id="id" name="id" 
      	placeholder="아이디" onkeyup="idCheck()">
      <p id="chkMsg" class="mb-2"></p>
      <input type="hidden" id="isIdCheck" value="no"/>
      
      <input class="form-control mb-2" type="text" name="pw" placeholder="비밀번호">
      <input class="form-control mb-2" type="text" name="name" placeholder="이름">
      <input class="form-control mb-2" type="text" name="age" placeholder="나이">
      
      <!-- 이메일 인증 -->
      <div class="row">
         <div class="col-md-8 pe-0">
            <input class="form-control mb-2" type="text" 
               id="email" name="email" placeholder="이메일">
         </div>
         <div class="col-md-4">
            <input id="code-send" class="btn btn-outline-secondary w-100" type="button"
               onclick="emailCheck()" value="인증번호 발송">
         </div>
      </div>
      <!-- 이메일 인증 확인 -->
      <div class="row" id="confirmEmail">
      	<div class="col-md-8 pe-0">
        	<input class="input-code" type="text" id="confirmUUID" placeholder="인증코드 입력"><input class="time" value="" readonly>
        </div>                  
        <div class="col-md-4">
        	<span id="confirm-btn" class="disabled btn btn-outline-secondary w-100" onclick="emailConfirm()">인증코드확인</span>
      	</div>
         
      </div>
      
      <p class="code-msg"></p>
      <input class="form-control mb-2" type="text" name="tel" placeholder="전화번호">
      
      <div class="text-center mt-3">
         <input type="submit" class="btn btn-primary" value="가입">
         <input type="reset" class="btn btn-info" value="취소">
      </div>
   </form>
</div>

<!-- The Modal -->
<!-- <div class="modal fade" id="chkModal">
  <div class="modal-dialog">
    <div class="modal-content">

      Modal Header
      <div class="modal-header">
        <h4 class="modal-title">중복체크 확인</h4>
        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
      </div>

      Modal body
      <div class="modal-body" id="chkMsg"></div>

      Modal footer
      <div class="modal-footer">
        <button type="button" class="btn btn-danger" data-bs-dismiss="modal">닫기</button>
      </div>

    </div>
  </div>
</div> -->

<script>
	// 모든 함수에서 사용하도록 responseUUID를 전역변수로 설정
	let responseUUID = "";
	let isEmailCheck = false;
	
	function submitChk(){
		let isIdCheck = $("#isIdCheck").val();
		console.log("isIdCheck : " + isIdCheck);
		
		if(isIdCheck == "no"){
		 alert("아이디 중복 체크를 확인해주세요!!");
		 $("#id").select();
		 return false;
		}
		
		if(!isEmailCheck){
			alert("이메일 인증확인을 해주세요!!")
			$("#email").select();
			return false;
		}
		
		return true;
	}
	
	// time표시
	let time_display;
	// timer 변수선언
	let timer;
	
	// timer 동작 함수
	function timer_start(){
		let count = 20;
		
		time_display = $(".time");
		// time_display.val("00:20");
		
		// Timer 설정시 사용하는 함수 
		// setInterval(function, 1/1000) : 1초마다 function을 주기적으로 호출
		// clearInterval(timer) : timer 함수를 종료시킴
		
		timer= setInterval(()=>{
			let minutes = parseInt(count/60); // 십진수로 변환
			let seconds = parseInt(count % 60);
			
			minutes = minutes < 10 ? "0" + minutes: minutes;
			seconds = seconds < 10 ? "0" + seconds: seconds;
			
			time_display.val(minutes + ":" + seconds);
			
			//
			if(--count < 0){
				timer_stop();
				time_display.val("시간초과");
				$(".code-msg").css({"display":"block","color":"red"});
				$(".code-msg").text("인증코드 만료되었습니다!!");
				$("#confirm-btn").addClass("disabled");
				$("#code-send").removeClass("disabled");
				$("#code-send").val("코드 재발송");
				$(".input-code").attr("readonly", true); // 읽기전용
			}
		}, 1000)
	}
	
	// 타이머 종료
	function timer_stop(){
		clearInterval(timer)
	}

   function emailCheck(){
      let uEmail = $("#email").val(); //사용자가 입력한 email주소
      $("#code-send").addClass("disabled")
      $("#input-code").val("");
      $(".input-code").attr("readonly", false);
      $("#confirm-btn").removeClass("disabled");
      
      $.ajax({
         url:"<c:url value='memberEmailCheck.do'/>",
         type:"get",
         data: {"uEmail":uEmail},
         success: function(uuid){ // uuid는 서버에서 전달된 값
            if(uuid != "fail"){
               console.log("이메일 인증코드:"+uuid);
               responseUUID = uuid;
               /* $("#confirmEmail").html('<div class="col-md-8 pe-0">'
                     +'<input class="form-control mb-2" type="text"' 
                     +' id="confirmUUID" placeholder="인증코드 입력"></div>'                     
                     +'<div class="col-md-4">'
                     +'<span class="btn btn-outline-secondary w-100" onclick="emailConfirm()">인증코드확인</span></div>'); */
                $("#confirmEmail").css({"visibility":"visible","height":"auto"});
                $(".code-msg").css({"display":"block","color":"green"});
                $(".code-msg").text("인증코드가 발송되었습니다!!");
            	timer_start();
            }else{
               alert("이메일을 다시 확인하세요!!");
            }
         },
         error: function(){
               alert("이메일 인증 실패!!");
               $("#email").select();
               }   
      });
   }
   
   // 이메일 인증코드 확인여부 체크
   function emailConfirm(){
      let confirmUUID = $("#confirmUUID").val();
      console.log("confirmUUID : " + confirmUUID)
      
      if(confirmUUID == null || confirmUUID ==""){
         // alert("이메일 확인코드를 입력하세요");
         $(".code-msg").css({"display":"block","color":"red"});
         $(".code-msg").text("인증코드를 다시 확인하세요!!");
         $("#confirmUUID").select();
         return;
      // 서버에서 전송된 uuid와 입력한 confirmUUID가 같은 경우
      }else if(responseUUID == confirmUUID){ 
         //alert("이메일 인증 성공!!")
         $(".code-msg").css({"display":"block","color":"green"});
         $(".code-msg").text("이메일 인증 성공!!");
         timer_stop();
         time_display.val("");
         // javaScript의 readonly속성 제어하기
         // document.querySelector(".input-code").readOnly=true;
         $(".input-code").attr("readonly", true);
         $("#confirm-btn").addClass("disabled");
         $("#code-send").removeClass("disabled");
		 $("#code-send").val("코드 재발송");
         isEmailCheck = true;
      }else{ // 인증코드 잘못 입력한 경우
         //alert("인증코드를 다시 확인하세요!!");
    	 $(".code-msg").css({"display":"block","color":"red"});
         $(".code-msg").text("인증코드를 다시 확인하세요!!");
         $("#confirmUUID").select();
         return;
      }
   }

   function idCheck(){
      let uid = $("#id").val(); // 사용자 입력 아이디 가져오기      
      
      if(uid.length<4){
    	  $("#chkMsg").text("아이디 길이는 4자리 이상이어야 합니다!!");
          $("#chkMsg").css({"color":"red", "font-size":"13px"})
          /* $("#id").select(); */
          return;
      }
      
      $.ajax({
         url:"<c:url value='memberIdCheck.do'/>",
         type: "get",
         data: {"uid":uid}, // 서버에 전송할 데이터
         success: function(responseData){
            console.log(responseData);
            if(responseData == "yes"){
               //alert("사용가능한 아이디 입니다!!");
               $("#chkMsg").text("사용가능한 아이디 입니다!!");
               $("#chkMsg").css({"color":"blue", "font-size":"13px"});
               $("#isIdCheck").val("yes");
            }else{
               //alert("이미 존재하는 아이디 입니다!!");
               $("#chkMsg").text("사용할 수 없는 아이디 입니다!!");
               $("#chkMsg").css({"color":"red", "font-size":"13px"});
            }
            /* $("#chkModal").modal("show"); */
         },
         error: function(){
            alert("서버 에러 입니다!!");
         }
      });
   }
</script>
<%@ include file="../include/footer.jsp" %>