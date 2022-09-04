package com.danny.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/spag")
public class spag extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 
		int num = 0;

		String num_ = request.getParameter("n");

		if(num_ != null && !num_.equals("")){
			num = Integer.parseInt(num_);
		} // if

		String model;

		if(num%2 !=0){
			model = "홀수";
		}else{
			model = "짝수";
		} // if
		
		request.setAttribute("model", model);
		
		String[] names = {"danny", "hailey"};
		request.setAttribute("names", names);
		
		Map<String, Object> notice = new HashMap<>();
		notice.put("id", 1);
		notice.put("title", "EL 표기법");
		request.setAttribute("notice", notice);
		
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("spag.jsp");
		
		dispatcher.forward(request, response);
	} // doGet
	
} // end class
