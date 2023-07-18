package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;

/**
 * FortuneFilterクラス. <br>
 * FortuneFilterクラスは、サーブレットのフィルター処理を行います。
 *
 * @author Ryo.inoue
 * @version 1.00
 */
@WebFilter("/*")
public class FortuneFilter extends HttpFilter implements Filter {

	/**
	 * 送受信される文字の文字コードをUTF-8にフィルタリングします。
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// 文字コードをセットする
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		chain.doFilter(request, response);
	}

}
