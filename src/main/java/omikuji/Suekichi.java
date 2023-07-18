package omikuji;

/**
 * Suekichiクラス. <br>
 * Suekichiクラスは、末吉を設定します。
 *
 * @author Ryo.inoue
 * @version 1.00
 */
public class Suekichi extends Omikuji {

	public Suekichi() {

		this.unsei = "末吉";
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
