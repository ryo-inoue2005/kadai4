package omikuji;

/**
 * Shokichiクラス. <br>
 * Shokichiクラスは、小吉を設定します。
 *
 * @author Ryo.inoue
 * @version 1.00
 */
public class Shokichi extends Omikuji {

	public Shokichi() {

		this.unsei = "小吉";
	}

	/**
	 * @see Omikuji#setUnsei()
	 */
	@Override
	public void setUnsei(String negaigoto, String akinai, String gakumon) {

		this.negaigoto = negaigoto;
		this.akinai = akinai;
		this.gakumon = gakumon;
	}
}
