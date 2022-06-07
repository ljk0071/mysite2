<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="./assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="./assets/css/main.css" rel="stylesheet" type="text/css">

</head>

<body>
	<div id="header" class="clearfix">
		<h1>
			<a href="./main">MySite</a>
		</h1>
			
		<c:if test="${authUser.name == null}">
			<!-- 로그인 전 -->
			<ul>
				<li><a href="./user?action=login" class="btn_s">로그인</a></li>
				<li><a href="./user?action=join" class="btn_s">회원가입</a></li>
			</ul>
		</c:if>
				
		<c:if test="${authUser.name != null}">
			<!-- 로그인 후 -->
			<ul>
				<li> ${authUser.name}님 안녕하세요^^;</li>
				<li><a href="./user?action=logout" class="btn_s">로그아웃</a></li> 
				<li><a href="./user?action=modify" class="btn_s">회원정보수정</a></li>
			</ul>
		</c:if>	
	</div>
