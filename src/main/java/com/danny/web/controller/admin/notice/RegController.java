package com.danny.web.controller.admin.notice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.danny.web.entity.Notice;
import com.danny.web.service.NoticeService;


@MultipartConfig(
	    fileSizeThreshold=1024*1024,
	    maxFileSize=1024*1024*50,
	    maxRequestSize=1024*1024*50*5
)
@WebServlet("/admin/board/notice/reg")
public class RegController extends HttpServlet{

	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/admin/board/notice/reg.jsp");
		dispatcher.forward(request, response);
		
	} // doGet
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String isOpen = request.getParameter("open");
		
		Collection<Part> parts =  request.getParts();
		StringBuilder builder = new StringBuilder();
		
		for(Part p : parts) {			
			if(!p.getName().equals("file")) continue;
            if(p.getSize() == 0) continue;			
			
			Part filePart = p;
			String fileName = filePart.getSubmittedFileName();
			builder.append(fileName);
			builder.append(",");
			
			InputStream fis = filePart.getInputStream();
			String realPath = request.getServletContext().getRealPath("/upload");
			
			File path = new File(realPath);
			if(!path.exists()) path.mkdirs();
			
			String filePath = realPath + File.separator + fileName;
			
			FileOutputStream fos = new FileOutputStream(filePath);
			
			byte[] buf = new byte[1024];
			int size = 0;
			while((size = fis.read(buf))!= -1) {
				fos.write(buf,0,size);
			} // while
			
			fos.close();
			fis.close();
			
		} // enhanced-for
		
		if(builder.length()>0)
		builder.delete(builder.length()-1,builder.length());
		
		boolean pub = false;
		if(isOpen != null) pub = true;
		
		
		Notice notice = new Notice();
		notice.setTitle(title);
		notice.setContent(content);
		notice.setPub(pub);
        notice.setWriterId("newlec");
        notice.setFiles(builder.toString());
        
        
		NoticeService service = new NoticeService();
		service.insertNotice(notice);
		
		response.sendRedirect("list");
	} // doPost
	
} // end class



