package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * FormServletクラス. <br>
 * FormServletクラスは、誕生日入力画面を制御します。
 *
 * @author Ryo.inoue
 * @version 1.00
 */
@WebServlet("/form")
public class FormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 誕生日入力画面を出力します
	 * 
	 * @throws ServletException サーブレット例外
	 * @throws IOException 入出力処理例外
	 * @param request リクエスト
	 * @param response レスポンス
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//---------------以下ブラウザ表示----------------//
		PrintWriter out = response.getWriter();

		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset='UTF-8'>");
		out.println("<title>誕生日入力画面</title>");
		out.println("</head>");

		out.println("<h1>生年月日で今日の運勢を占います</h1>");
		out.println("<h2>yyyyMMddの形式で生年月日を入力してください</h2>");

		HttpSession session = request.getSession();

		// 入力エラーメッセージを取得
		String errMsg = (String) session.getAttribute("errMsg");

		// エラーがあれば表示し、エラーメッセージのセッションを消す
		if (errMsg != null) {
			out.println("<p style='color: red; font-size: 30px; font-weight: bold';>" + errMsg + "</p>");
			session.removeAttribute("errMsg");
		}

		out.println("<form action='./result' method='post'>");
		out.println("<p>誕生日：<input type='text' name='birthday'></p>");
		out.println("<input type='submit' value='送信'>");
		out.println("</form>");

		out.close();
	}

	/**
	 * doGetメソッドに処理を渡します。
	 * 
	 * @throws ServletException サーブレット例外
	 * @throws IOException 入出力処理例外
	 * @param request リクエスト
	 * @param response レスポンス
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
