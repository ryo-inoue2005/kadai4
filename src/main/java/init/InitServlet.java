package init;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import dao.RegisterDao;

/**
 * InitServletクラス. <br>
 * InitServletクラスは、サーブレットの初期化処理を行います。
 *
 * @author Ryo.inoue
 * @version 1.00
 */
public class InitServlet extends HttpServlet {

	/**
	 * プロパティファイルに保存しているおみくじデータを起動時にデータベースに登録します。
	 * 
	 * @throws ServletException
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		try {
			// プロパティファイルからデータベースにおみくじを登録する
			System.out.println("おみくじテーブル初期化処理実行");
			RegisterDao dao = new RegisterDao();
			dao.registerOmikuji();

		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException();
		}
	}

}
