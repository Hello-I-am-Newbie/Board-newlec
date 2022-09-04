<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
String cnt_ = request.getParameter("cnt");

int cnt = 100; // 기본값
if(cnt_ != null && cnt_ != "") {
	cnt = Integer.parseInt(cnt_); // 요청 값 
	
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%for(int i=0; i<cnt; i++){%>
		안녕 <br>
	<%}%>
</body>
</html>