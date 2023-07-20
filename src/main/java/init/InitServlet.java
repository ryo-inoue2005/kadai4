package init;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import dao.RegisterOmikujiDao;

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
	public void init() throws ServletException {
		try {
			System.out.println("おみくじテーブル初期化処理実行");

			// プロパティファイルからデータベースにおみくじを登録する
			RegisterOmikujiDao dao = new RegisterOmikujiDao();
			ServletContext context = this.getServletContext();
			dao.registerOmikuji(context.getRealPath("data/fortune.csv"));

		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
	}

}
