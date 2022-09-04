<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<%pageContext.setAttribute("model","hello"); %>
<body>
<%-- ${model}<br>
${requestScope.model}<br>
${names[0]}<br>
${notice.title}<br> --%>
${param.n/2}<br>
<%-- ${param.n gt 3}<br>
${param.n ge 3}<br>
${header.accept}<br> --%>


</body>
</html>