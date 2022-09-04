package com.danny.web.controller.admin.notice;

import java.io.IOException;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.danny.web.entity.Notice;
import com.danny.web.service.NoticeService;

@WebServlet("/admin/board/notice/detail")
public class DetailController extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String url = "jdbc:oracle:thin:@DB20220609152312_high?TNS_ADMIN=/Library/opt/OracleCloudWallet/ATP";
		String uid = "ADMIN";
		String pwd = "Rlarldnd5345411!";
		String driver = "oracle.jdbc.driver.OracleDriver";
		
		int id = Integer.parseInt(request.getParameter("id"));
		
		NoticeService service = new NoticeService();
		Notice notice = service.getNotice(id);
		request.setAttribute("n", notice);
		
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/admin/board/notice/detail.jsp");
		dispatcher.forward(request, response);
		
	} // doGet
	
} // end class
