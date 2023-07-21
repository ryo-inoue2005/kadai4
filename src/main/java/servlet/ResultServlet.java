package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.InputCheck;
import dao.GetOmikujiDao;
import dao.RegisterOmikujiDao;

/**
 * ResultServletクラス. <br>
 * ResultServletクラスは、運勢結果をJSPにフォワードします。
 *
 * @author Ryo.inoue
 * @version 1.00
 */
@WebServlet("/result")
public class ResultServlet extends HttpServlet {

	/**
	 * doPostメソッドに処理を渡します。
	 * 
	 * @throws ServletException サーブレット例外
	 * @throws IOException 入出力処理例外
	 * @param request リクエスト
	 * @param response レスポンス
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * 初期表示や入力エラー発生時は誕生日入力画面を出力します。
	 * 誕生日のチェックを行い、おみくじを引き、結果をデータベースに保存及び
	 * 結果表示画面に遷移させます。
	 * 
	 * @throws ServletException サーブレット例外
	 * @throws IOException 入出力処理例外
	 * @param request リクエスト
	 * @param response レスポンス
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 結果表示画面のURIをセット
		final String target = "/jsp/resultView.jsp";

		// リダイレクトするか判定する変数
		boolean redirect = false;

		HttpSession session = request.getSession();

		try {
			// 誕生日を取得
			String birthday = request.getParameter("birthday");

			// リクエストパラメータから取得した誕生日をチェックする
			String errMsg = InputCheck.isInputNg(birthday);

			// エラーメッセージが格納されていたら、誕生日入力画面に戻る
			if (errMsg != null) {
				session.setAttribute("errMsg", errMsg);
				redirect = true;
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

			// エラーが発生したら誕生日入力画面に戻る
		} catch (SQLException e) {
			e.printStackTrace();
			session.setAttribute("errMsg", "データベースエラー");
			redirect = true;

		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("errMsg", "予期しないエラー");
			redirect = true;

		} finally {
			if (redirect) {
				response.sendRedirect("form");
			}
		}
	}
}