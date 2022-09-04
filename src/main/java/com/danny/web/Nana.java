package com.danny.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/hi")
public class Nana extends HttpServlet{

	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		
		String cnt_ = request.getParameter("cnt");
		
		int cnt = 100; // 기본값
		if(cnt_ != null && cnt_ != "") {
			cnt = Integer.parseInt(cnt_); // 요청 값 
			
		}
		for(int i=0; i<cnt; i++) {
			
			out.println((i+1) + " 안녕 <br>");
			
		} // for
		
	} // service
} // end class
