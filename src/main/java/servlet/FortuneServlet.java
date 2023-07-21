package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import common.InputCheck;
import dao.GetOmikujiDao;
import dao.RegisterOmikujiDao;

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
	 * 初期表示や入力エラー発生時は誕生日入力画面を出力します。
	 * 誕生日のチェックを行い、おみくじを引き、結果をデータベースに保存及び
	 * 結果表示画面に遷移させます。
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
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {

		// 結果表示画面のURIをセット
		final String target = "/jsp/resultView.jsp";

		// 入力エラーメッセージを取得
		String errMsg = (String) request.getAttribute("errMsg");

		// 初期表示
		if (errMsg != null) {
			//---------------ブラウザ表示Start----------------//
			PrintWriter out = response.getWriter();

			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<meta charset='UTF-8'>");
			out.println("<title>占いフォーム</title>");
			out.println("</head>");

			out.println("<h1>生年月日で今日の運勢を占います</h1>");
			out.println("<h2>yyyyMMddの形式で生年月日を入力してください</h2>");

			// 初期表示でなければ、エラーメッセージを表示
			if (!errMsg.equals("")) {
				out.println("<p style='color: red; font-size: 30px; font-weight: bold';>" + errMsg + "</p>");
			}

			out.println("<form action='./fortune' method='post'>");
			out.println("<p>誕生日：<input type='text' name='birthday'></p>");
			out.println("<input type='submit' value='送信'>");
			out.println("</form>");

			out.close();
			//---------------ブラウザ表示End----------------//

			//-------------結果登録、フォワード処理-------------//
		} else {
			try {
				// 誕生日を取得
				String birthday = request.getParameter("birthday");

				// リクエストパラメータから取得した誕生日をチェックする
				// 初期アクセス時は空文字返す
				errMsg = InputCheck.isInputNg(birthday);

				// エラーメッセージが格納されていたら、誕生日入力画面に戻る
				if (errMsg != null) {
					request.setAttribute("errMsg", errMsg);
					service(request, response);
					return;
				}

				// 誕生日と今日の日付を元にresultテーブルに存在するか検索をする
				GetOmikujiDao getDao = new GetOmikujiDao();
				int omikujiCode = getDao.getResult(birthday);

				// resultテーブルに無かった場合、おみくじを引き、resultテーブルに登録
				if (omikujiCode == 0) {
					Random random = new Random();

					// データベースからおみくじの数を取得し、ランダムにおみくじコードを発行する
					omikujiCode = random.nextInt(getDao.getMaxOmikujiCode()) + 1;

					// resultテーブルに登録する
					RegisterOmikujiDao registerDao = new RegisterOmikujiDao();
					int result = registerDao.registerResult(omikujiCode, birthday);

					// 登録件数が1件以外だったら強制終了させ、誕生日入力画面に戻る
					if (result != 1) {
						throw new SQLException();
					}
				}

				// おみくじコードでおみくじを引き、リクエストスコープに格納
				request.setAttribute("omikuji", getDao.drawOmikuji(omikujiCode));

				// 最後まで実行出来たらフォワード
				ServletContext context = this.getServletContext();
				RequestDispatcher dispatcher = context.getRequestDispatcher(target);
				dispatcher.forward(request, response);

				// データベースエラーが発生したら誕生日入力画面に戻る
			} catch (SQLException e) {
				e.printStackTrace();
				request.setAttribute("errMsg", "データベースエラー");
				service(request, response);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}