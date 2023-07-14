package init;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import dao.RegisterDao;

public class InitServlet extends HttpServlet {

	@Override
	public void init(ServletConfig config) throws ServletException {
		try {
			System.out.println("呼ばれた");
			RegisterDao dao = new RegisterDao();
			dao.registerOmikuji();

		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException();
		}
	}

}
