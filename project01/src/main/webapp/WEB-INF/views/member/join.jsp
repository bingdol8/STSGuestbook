<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title> 
<%@ include file="../include/header.jsp" %>
</head>
<body>
<%@ include file="../include/menu.jsp" %>
<form method="post" action="${path}/member/insert.do">
아이디: <input name="userid"><br>
비번:	<input id="password" name="passwd"><br>
이름: <input name="name"><br>
이메일: <input name="email"><br>
<input type="submit" value="확인">
</form>
</body>
</html>