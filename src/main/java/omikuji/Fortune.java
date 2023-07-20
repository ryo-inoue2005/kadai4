package omikuji;

import java.util.ResourceBundle;

/**
 * Fortuneインターフェース. <br>
 * Fortuneインターフェースは、占い結果を表示します。
 *
 * @author Ryo.inoue
 * @version 1.00
 */
public interface Fortune {

	/** プロパティファイルから取得した値を表します */
	String DISP_STR = ResourceBundle.getBundle("fortune").getString("disp_str");

	/**
	 * 占い結果を文字列をとして返します
	 *
	 * @return 占い結果
	 */
	String[] disp();
}
