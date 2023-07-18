<%@page import="omikuji.Fortune"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
// リクエストスコープからのデータの取得
@SuppressWarnings("unchecked")
Fortune fortune = (Fortune) request.getAttribute("fortune");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>運勢結果</title>
</head>
<body>

	<h1>運勢結果</h1>

	<%
	if (fortune != null) {
	%>
	<p><%=fortune.disp()%></p>
	<%
	}
	%>

	<a href="form">占いフォームに戻る</a>

</body>
</html>