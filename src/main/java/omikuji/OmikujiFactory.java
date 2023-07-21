package omikuji;

/**
 * OmikujiFactoryクラス. <br>
 * OmikujiFactoryクラスは、おみくじオブジェクトを生成します。
 *
 * @author Ryo.inoue
 * @version 1.00
 */
public class OmikujiFactory {

	/**
	 * 運勢を元におみくじオブジェクトを生成して返します。
	 * 
	 * @return omikuji おみくじオブジェクト
	 * @return null 不正な運勢の場合
	 */
	public static Omikuji create(String unsei) {

		switch (unsei) {

		case "大吉":
			return new Daikichi();

		case "中吉":
			return new Chukichi();

		case "吉":
			return new Kichi();

		case "小吉":
			return new Shokichi();

		case "末吉":
			return new Suekichi();
			
		case "凶":
			return new Kyo();
			
		default:
			System.out.println("不正な運勢です");
			return null;
		}
	}
}
