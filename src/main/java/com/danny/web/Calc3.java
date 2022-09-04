package com.danny.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/calc3")
public class Calc3 extends HttpServlet{

	
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");


//		ServletContext application = request.getServletContext(); // 값을 저장하는 도구(Application 저장소)
//		HttpSession session =request.getSession();
		Cookie[] cookies = request.getCookies(); // 쿠키 받기 
		
		
		String value = request.getParameter("value");
		String operator = request.getParameter("operator");
		String dot = request.getParameter("dot");
		
		String exp = "";
		if(cookies != null) {
			for(Cookie c : cookies) {
				if(c.getName().equals("exp")) {
					exp = c.getValue();
					break;
				};
			} // for
		} // if
		if(operator != null && operator.equals("=")) {
			// 자바스크립트의 구문 실행자를 통해서 계산 
			ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
			try {
				exp = String.valueOf(engine.eval(exp));
			} catch (ScriptException e) {
				e.printStackTrace();
			}
		}else {
			exp += (value == null)?"":value;
			exp += (operator == null)?"":operator;
			exp += (dot == null)?"":dot;
						
		} // if-else
		
		Cookie expCookie = new Cookie("exp",exp);
		if(operator != null && operator.equals("C")) {
			expCookie.setMaxAge(0);			
		};
		response.addCookie(expCookie);
		response.sendRedirect("calcpage");
		
	} // service
	
} // end class