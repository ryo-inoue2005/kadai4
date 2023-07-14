<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>占いフォーム</title>
</head>
<body>

	<h1>生年月日を入力してください</h1>

	<!-- 登録内容を送信する -->
	<form action="./resultView" method="post">
		<p>
			誕生日：<input type="text" name="birthDay">
		</p>
		<input type="submit" value="送信">
	</form>

</body>
</html>