package controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.InputCheck;
import dao.GetDao;
import dao.RegisterDao;
import omikuji.Fortune;

/**
 * ResultControllerクラス. <br>
 * ResultControllerクラスは、占い結果表示画面を制御します。
 *
 * @author Ryo.inoue
 * @version 1.00
 */
@WebServlet("/resultView")
public class ResultController extends HttpServlet {

	/**
	 * 誕生日の入力チェックをし、占い結果表示画面にフォワードさせます。
	 * 
	 * @throws ServletException
	 * 			サーブレット例外
	 * @throws IOException
	 * 			入出力処理例外
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 運勢結果を出力するURIをセット
		String target = "/jsp/resultView.jsp";

		// リダイレクト判定変数
		boolean redirect = false;

		try {

			// リクエストパラメータから取得した誕生日をチェックする
			String birthday = request.getParameter("birthday");
			String errMsg = InputCheck.isInputNg(birthday);

			// エラーメッセージが格納されていたら、占いフォーム画面にリダイレクトさせる
			if (errMsg != null) {
				target = "form?errMsg=" + URLEncoder.encode(errMsg, "UTF-8");
				redirect = true;
				return;
			}

			// 誕生日と今日の日付を元にresultテーブルに存在するか検索をする
			GetDao getDao = new GetDao();
			int omikujiCode = getDao.getResult(birthday);

			// resultテーブルに無かった場合、おみくじを引き、resultテーブルに登録
			if (omikujiCode == 0) {

				Random random = new Random();
				int min = 1;
				int max = 1;

				// データベースからおみくじの数を取得し、ランダムにおみくじコードを発行する
				omikujiCode = random.nextInt(min, getDao.getMaxOmikujiCode() + max);

				// resultテーブルに登録する
				RegisterDao registerDao = new RegisterDao();
				int result = registerDao.registerResult(omikujiCode, birthday);

				// 登録件数が1件以外だったら強制終了させ、リダイレクトを行う
				if (result != 1) {
					target = "form?errMsg=" + URLEncoder.encode("登録エラー", "UTF-8");
					redirect = true;
					return;
				}
			}

			// おみくじコードでおみくじを引き、JSPに送信
			Fortune fortune = getDao.drawOmikuji(omikujiCode);
			request.setAttribute("fortune", fortune);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			// リダイレクト
			if (redirect) {
				response.sendRedirect(target);
				//フォワード
			} else {
				ServletContext context = this.getServletContext();
				RequestDispatcher dispatcher = context.getRequestDispatcher(target);
				dispatcher.forward(request, response);
			}
		}
	}
}
