package com.danny.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/calc2")
public class Calc2 extends HttpServlet{

	
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");


//		ServletContext application = request.getServletContext(); // 값을 저장하는 도구(Application 저장소)
//		HttpSession session =request.getSession();
		Cookie[] cookies = request.getCookies(); // 쿠키 받기 
		
		PrintWriter out = response.getWriter();
		
		String v_ = request.getParameter("v");
		String op = request.getParameter("operator");
		
		int v = 0;
		if(!v_.equals("")) v = Integer.parseInt(v_);
		
		if(op.equals("=")) { // = 이라면 값을 계산
			
//			int x = (Integer)application.getAttribute("value"); // value에 해당하는 값을 가져온다
//			int x = (Integer)session.getAttribute("value"); // value에 해당하는 값을 가져온다
			
			int x = 0;
			
			for(Cookie c : cookies) {
				if(c.getName().equals("value")) {
					x = Integer.parseInt(c.getValue());
					break;
				} // if
			} // for

			int y = v;
			
			
//			String operator = (String)application.getAttribute("op"); // op에 해당하는 값을 가져온다 
//			String operator = (String)session.getAttribute("op"); // op에 해당하는 값을 가져온다 
			
			String operator = "";
			
			for(Cookie c : cookies) {
				if(c.getName().equals("op")) {
					operator = c.getValue();
					break;
				} // if
			} // for
			int result = 0;
			
			if(operator.equals("+")) result = x + y; 
			if(operator.equals("-")) result = x - y;

			out.printf("result : %d", result);
		
		}else { // 기본 연산 +, - 이라면 값을 저장
//			application.setAttribute("value", v); // value값 저장 
//			application.setAttribute("op", op); // operator값 저장
			
//			session.setAttribute("value", v); // value값 저장 
//			session.setAttribute("op", op); // operator값 저장
			
			Cookie valueCookie = new Cookie("value", String.valueOf(v)); // 쿠키 생성 및 v 값을 문자열로 cookie 에 저장 
			Cookie opCookie = new Cookie("op", op); // 쿠키 생성 및 op 을 쿠키에 저장
			
			valueCookie.setPath("/calc2"); // 해당 uri를 요청 시에만 cookie를 보낸다는 옵션을 준다.
			valueCookie.setMaxAge(24*60*60);			
			opCookie.setPath("/calc2");
			
			response.addCookie(valueCookie); // 클라이언트에게 쿠키 전달 
			response.addCookie(opCookie); // 클라이언트에게 쿠키 전달
			
			response.sendRedirect("calc2.html");
		}
		
	} // service
	
} // end class