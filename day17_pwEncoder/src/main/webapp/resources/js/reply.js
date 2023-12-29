let replyFunc = (()=>{
   // 댓글 등록
   function register(reply, callback){
      $.ajax({
         url:"/pwEncoder/replies/new",
         type:"post",
         data:JSON.stringify(reply), // 문자열(텍스트) 변환 ==> 직렬화작업
         contentType: "application/json; charset=utf8",
         success:(result)=>{               
            callback(result);
         },
         error:()=>{alert("요청실패!!!")}
      });
   }
   
   // 댓글 삭제
   function remove(rno, callback){
      $.ajax({
         url:"/pwEncoder/replies/"+rno,
         type:"delete",
         success:(result)=>{               
            callback(result);
         },
         error:()=>{alert("요청실패!!!")}
      });
   }
   
   // 댓글 조회
   function read(rno, callback){
      // alert(rno);
      $.ajax({
         url:"/pwEncoder/replies/"+rno,
         type:"get",
         success:(data)=>{
            console.log(data);
            callback(data);
         },
         error:()=>{
            alert("요청실패!!");
         }
      });
   }
   
   // 댓글 수정
   function update(reply, callback){
      console.log(reply);
      $.ajax({
         url:"/pwEncoder/replies/"+reply.rno,
         type:"put",
         data:JSON.stringify(reply),
         contentType: "application/json; charset=utf8",
         success:(result)=>{               
            callback(result);
         },
         error:()=>{alert("요청실패!!!")}
      })
   }
   
   // 특정 게시글에 대한 댓글 리스트
/*   function getList(bid, callback){
      $.ajax({
         url:"/reply2/replies/list/"+bid,
         type:"get",
         success:(result)=>{   
            console.log(result);            
            callback(result);
         },
         error:()=>{alert("요청실패!!!")}
      });
   } 
*/
   function getList(param, callback){
      let bid = param.bid;
      let viewPage = param.viewPage;
      
      $.ajax({      
         url:"/pwEncoder/replies/list/"+bid+"/"+viewPage,
         type:"get",
         success:(result)=>{   
            console.log(result);            
            callback(result);
         },
         error:()=>{alert("요청실패!!!")}
      });
   } 
   
   
   
   // 댓글 시간/날짜 표시(24시간이 지난 경우: 날짜, 그렇지 않으면 : 시간)
   function showDateTime(replyTime){ // replyTime:댓글 등록시간
   
      //console.log("replyTime :"+replyTime);
      
      // 현재 시간 구하고
      let now = new Date();
      
      // 현재시간과 댓글 등록시간의 갭(gap:차이)
      let gap = now.getTime() - replyTime; 
      
      // 댓글 등록시간을 Date객체로 생성      
      let rDate = new Date(replyTime);
      //console.log("rDate : " + rDate); 

      // gap이 24시간 이상이면 날짜로 표시, 24시간 미만이면 시간으로 표시
      if(gap < (1000 * 60 * 60 * 24)){ // 24시간 미만이면
         let hh = rDate.getHours();
         let mi = rDate.getMinutes();
         let ss = rDate.getSeconds();
         
         return [(hh >= 10 ? '': '0')+hh, ":", (mi >= 10 ? '': '0')+mi, ":", (ss >= 10 ? '': '0')+ss].join(''); // [09,":",03,":",23] ==> "09:03:23", 배열 ==> 문자열 바꾸는 함수 join(구분자)
          
          // join(구분자)의 반대함수 split(구분자) 
          
          // "손흥민/박지성/차범근".split('/')
          // "손흥민/박지성/차범근" ==> [손흥민, 박지성, 차범근]
         
      }else{ // 24시간 이상
         let yy =rDate.getFullYear();
         let mm =rDate.getMonth() + 1; // 0(1월) ~ 11
         let dd =rDate.getDate();
         // console.log("mm:"+mm);
         return [yy, '-', (mm >= 10 ? '': '0')+mm, '-',(dd >= 10 ? '': '0')+dd].join('');
      }
      
   }
   
   
   return {
      register : register,
      remove : remove,
      read : read,
      update : update,
      getList : getList,
      showDateTime : showDateTime
   }      
})();