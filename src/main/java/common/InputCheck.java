package common;

import java.text.SimpleDateFormat;

/**
 * InputCheckクラス. <br>
 * InputCheckクラスは、入力された値をチェックします。
 *
 * @author Ryo.inoue
 * @version 1.00
 */
public class InputCheck {

	/**
	 * 入力された日付が正しい日付どうかチェックします。
	 * 
	 * @return チェック結果
	 */
	public static String isInputNg(String input) {

		// 数値がどうかチェック
		if (input == null || !input.matches("^[0-9]+$|-[0-9]+$")) {
			return "数値を入力してください";
		}

		String fs = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

		// 正しい形式かチェック
		try {
			fs = df.format(df.parse(input));
		} catch (Exception e) {

			return "指定された入力形式ではありません";
		}

		// 存在する日付かチェック
		if (!input.equals(fs)) {
			return "日付が存在しません";
		}

		return null;
	}
}
