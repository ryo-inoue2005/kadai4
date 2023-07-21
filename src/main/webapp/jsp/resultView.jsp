<%@page import="omikuji.Omikuji"%>
<%@page import="omikuji.Fortune"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
// リクエストスコープからのデータの取得
Omikuji omikuji = (Omikuji) request.getAttribute("omikuji");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>運勢結果</title>
</head>
<body>

	<h1>運勢結果</h1>

	<p>今日の運勢は<%=omikuji.getUnsei()%>です</p>
	<p>願い事：<%=omikuji.getNegaigoto()%></p>
	<p>商い：<%=omikuji.getAkinai()%></p>
	<p>学問：<%=omikuji.getGakumon()%></p>

	<a href="fortune">占いフォームに戻る</a>

</body>
</html>