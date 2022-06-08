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
	<c:choose>
		<c:when test="${authUser != null}">
			<div id="aside">
				<h2>게시판</h2>
				<ul>
					<li><a href="./board?action=list">일반게시판</a></li>
					<li><a href="">댓글게시판</a></li>
				</ul>
			</div>
		</c:when>
		<c:when test="${authUser == null}">
			<div id="aside">
				<h2>게시판</h2>
				<ul>
					<li><a href="./board?action=list">일반게시판</a></li>
					<li><a href="">댓글게시판</a></li>
				</ul>
			</div>
		</c:when>
	</c:choose>
</body>
</html>