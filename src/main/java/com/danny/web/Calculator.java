package com.danny.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/calculator")
public class Calculator extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException {
Cookie[] cookies = request.getCookies(); // 쿠키 받기
		
		String exp = "0";
		if(cookies !=null) {
			for(Cookie c : cookies) {
				if(c.getName().equals("exp")) {
					exp = c.getValue();
					break;
				} // if
			} // for			
		} // if
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		
		
		out.write("<!DOCTYPE html>");
		out.write("<html>");
		out.write("<head>");
		out.write("<meta charset=\"UTF-8\">");
		out.write("<title>calculator</title>");

		out.write("<style>");
		out.write("input{");
		out.write("width:50px;");
		out.write("height:50px;");
		out.write("}");
				
		out.write(".output{");
		out.write("height:50px;");
		out.write("background: #e9e9e9;");
		out.write("font-size:24px;");
		out.write("font-weight:bold;");
		out.write("text-align:right;");
		out.write("padding:0px 5px;");
		out.write("}");			
		out.write("</style>");
		out.write("</head>");

		out.write("<body>");
		out.write("<form method=\"post\">");
		out.write("<table>");
		out.write("<tr>");
		out.printf("<td colspan=\"4\" class=\"output\">%s</td>",exp);
		out.write("</tr>");
		out.write("<tr>");
		out.write("<td><input type=\"submit\" name=\"operator\" value=\"CE\"></td>");
		out.write("<td><input type=\"submit\" name=\"operator\" value=\"C\"></td>");
		out.write("<td><input type=\"submit\" name=\"operator\" value=\"BS\"></td>");
		out.write("<td><input type=\"submit\" name=\"operator\" value=\"/\"></td>");
		out.write("</tr> ");
		out.write("<tr>");
		out.write("<td><input type=\"submit\" name=\"value\" value=\"7\"></td>");
		out.write("<td><input type=\"submit\" name=\"value\" value=\"8\"></td>");
		out.write("<td><input type=\"submit\" name=\"value\" value=\"9\"></td>");
		out.write("<td><input type=\"submit\" name=\"operator\" value=\"*\"></td>");
		out.write("</tr>");
		out.write("<tr>");
		out.write("<td><input type=\"submit\" name=\"value\" value=\"4\"></td>");
		out.write("<td><input type=\"submit\" name=\"value\" value=\"5\"></td>");
		out.write("<td><input type=\"submit\" name=\"value\" value=\"6\"></td>");
		out.write("<td><input type=\"submit\" name=\"operator\" value=\"-\"></td>");
		out.write("</tr>");
		out.write("<tr>");
		out.write("<td><input type=\"submit\" name=\"value\" value=\"1\"></td>");
		out.write("<td><input type=\"submit\" name=\"value\" value=\"2\"></td>");
		out.write("<td><input type=\"submit\" name=\"value\" value=\"3\"></td>");
		out.write("<td><input type=\"submit\" name=\"operator\" value=\"+\"></td>");
		out.write("</tr>");
		out.write("<tr>");
		out.write("<td></td>");
		out.write("<td><input type=\"submit\" name=\"value\" value=\"0\"></td>");
		out.write("<td><input type=\"submit\" name=\"dot\" value=\".\"></td>");
		out.write("<td><input type=\"submit\" name=\"operator\" value=\"=\"></td>");
		out.write("</tr>");
		out.write("</table>");
		out.write("</form>");
		out.write("</body>");

		out.write("</html>");
	} // doGet
	
	@Override
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException {
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
		expCookie.setPath("/calculator");
		response.addCookie(expCookie);
		response.sendRedirect("calculator");
	} // doPost
	
} // end class