package kr.ezen.pwEncoder.test;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor{
   @Override
   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
         throws Exception {
      
      // System.out.println("LoginInterceptor~~~");
      HttpSession session = request.getSession();
      
      System.out.println(request.getRequestURL());
      System.out.println(request.getQueryString());
      
//      request.getRequestURL();
      String queryString = request.getQueryString();
//      System.out.println("queryString: " + queryString);
      
      // URL 인코딩(URLEncoder.encode)? <==> URL 디코딩(URLDecoder.decode)
      // url을 서버가 이해할 수 있는 표준 형식으로 변환하는 것
      String urlParam= URLEncoder.encode(request.getRequestURL()+"?"+queryString, "utf-8");
      
      // StringBuilder 사용하기
//      StringBuilder builder = new StringBuilder();
//      builder.append(request.getRequestURL());
//      builder.append("?");
//      builder.append(queryString);
//      String url = builder.toString();
//      String urlParam= URLEncoder.encode(url, "utf-8");
      
      
      if(session.getAttribute("loginDTO") == null) {
//         response.sendRedirect(request.getContextPath() + "/member/login.do?moveUrl="+request.getRequestURL()+"?"+queryString);
         response.sendRedirect(request.getContextPath() + "/member/login.do?moveUrl="+urlParam);
         return false; // false는 인터셉터 더이상 진행이 안되고 중지
      }
      
      return true;
   }
}