package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.InputCheck;
import dao.GetOmikujiDao;
import dao.RegisterOmikujiDao;
import omikuji.Fortune;

/**
 * FortuneServletクラス. <br>
 * FortuneServletクラスは、誕生日入力画面を制御します。
 *
 * @author Ryo.inoue
 * @version 1.00
 */
@WebServlet("/fortune")
public class FortuneServlet extends HttpServlet {

	/**
	 * 誕生日入力画面を出力します。
	 * 
	 * @throws ServletException
	 * 			サーブレット例外
	 * @throws IOException
	 * 			入出力処理例外
	 * 
	 * @param request
	 * 			リクエスト
	 * @param response
	 * 			レスポンス
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 入力エラーメッセージを取得
		String errMsg = (String) request.getAttribute("errMsg");

		PrintWriter out = response.getWriter();

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

			out.println("<form action='./fortune' method='post'>");
			out.println("<p>誕生日：<input type='text' name='birthday'></p>");
			out.println("<input type='submit' value='送信'>");
			out.println("</form>");

		} finally {
			out.close();
		}
	}

	/**
	 * 誕生日の入力チェックをし、結果表示画面にフォワードさせます。
	 * 
	 * @throws ServletException
	 * 			サーブレット例外
	 * @throws IOException
	 * 			入出力処理例外
	 * 
	 * @param request
	 * 			リクエスト
	 * @param response
	 * 			レスポンス
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 結果表示画面のURIをセット
		String target = "/jsp/resultView.jsp";

		try {
			// リクエストパラメータから取得した誕生日をチェックする
			String birthday = request.getParameter("birthday");
			String errMsg = InputCheck.isInputNg(birthday);

			// エラーメッセージが格納されていたら、誕生日入力画面に戻る
			if (errMsg != null) {
				request.setAttribute("errMsg", errMsg);
				doGet(request, response);
				return;
			}

			// 誕生日と今日の日付を元にresultテーブルに存在するか検索をする
			GetOmikujiDao getDao = new GetOmikujiDao();
			int omikujiCode = getDao.getResult(birthday);

			// resultテーブルに無かった場合、おみくじを引き、resultテーブルに登録
			if (omikujiCode == 0) {
				Random random = new Random();
				int min = 1;
				int max = 1;

				// データベースからおみくじの数を取得し、ランダムにおみくじコードを発行する
				omikujiCode = random.nextInt(min, getDao.getMaxOmikujiCode() + max);

				// resultテーブルに登録する
				RegisterOmikujiDao registerDao = new RegisterOmikujiDao();
				int result = registerDao.registerResult(omikujiCode, birthday);

				// 登録件数が1件以外だったら強制終了させ、誕生日入力画面に戻る
				if (result != 1) {
					request.setAttribute("errMsg", "登録エラー");
					doGet(request, response);
					return;
				}
			}

			// おみくじコードでおみくじを引き、JSPに送信
			Fortune fortune = getDao.drawOmikuji(omikujiCode);
			request.setAttribute("fortune", fortune);

			// 最後まで実行出来たらフォワード
			ServletContext context = this.getServletContext();
			RequestDispatcher dispatcher = context.getRequestDispatcher(target);
			dispatcher.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
