package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/resultView")
public class FortuneController extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String target = null;
		try {
			// 運勢結果画面をセット
			target = "/jsp/resultView.jsp";

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			ServletContext context = this.getServletContext();
			RequestDispatcher dispatcher = context.getRequestDispatcher(target);
			dispatcher.forward(request, response);
		}

	}

}
