package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * FormControllerクラス. <br>
 * FormControllerクラスは、占いフォーム画面を制御します。
 *
 * @author Ryo.inoue
 * @version 1.00
 */
@WebServlet("/form")
public class FormController extends HttpServlet {

	/**
	 * 占いフォーム画面を出力します。
	 * 
	 * @throws ServletException
	 * 			サーブレット例外
	 * @throws IOException
	 * 			入出力処理例外
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		// 入力エラーメッセージを取得
		String errMsg = request.getParameter("errMsg");

		//---------------以下ブラウザ表示-----------------//

		try {
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<meta charset='UTF-8'>");
			out.println("<title>占いフォーム</title>");
			out.println("</head>");

			out.println("<h1>生年月日で今日の運勢を占います</h1>");
			out.println("<h2>yyyyMMddの形式で生年月日を入力してください</h2>");

			// エラーメッセージがあれば表示
			if (errMsg != null) {
				out.println("<p style='color: red; font-size: 30px; font-weight: bold';>" + errMsg + "</p>");
			}

			out.println("<form action='./resultView' method='post'>");
			out.println("<p>誕生日：<input type='text' name='birthday'></p>");
			out.println("<input type='submit' value='送信'>");
			out.println("</form>");

		} finally {
			out.close();
		}
	}
}
