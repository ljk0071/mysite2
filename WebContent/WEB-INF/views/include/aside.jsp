<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="./assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="./assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="aside">
	<c:choose>
		<c:when test="${authUser == null}">
			<h2>게시판</h2>
			<ul>
				<li><a href="./user?action=login">로그인</a></li>
				<li><a href="./user?action=join">회원가입</a></li>
			</ul>
		</c:when>
		<c:when test="${authUser != null}">
			<h2>게시판</h2>
			<ul>
				<li><a href="./user?action=modify">회원정보</a></li>
				<li><a href="./user?action=login">로그인</a></li>
				<li><a href="./user?action=join">회원가입</a></li>
			</ul>
		</c:when>
	</c:choose>
	</div>
</body>
</html>