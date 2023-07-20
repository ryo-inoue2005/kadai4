<%@page import="omikuji.Fortune"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
// リクエストスコープからのデータの取得
Fortune fortune = (Fortune) request.getAttribute("fortune");
// 改行文字で文字を分ける
String[] dispArray = fortune.disp().split(System.getProperty("line.separator"));
// 表示用文字
StringBuilder disp = new StringBuilder();

// 改行文字があった場所に<br>を付ける
for (String str : dispArray) {
	disp.append(str);
	disp.append("<br>");
}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>運勢結果</title>
</head>
<body>

	<h1>運勢結果</h1>

	<p><%=disp%></p>

	<a href="fortune">占いフォームに戻る</a>

</body>
</html>