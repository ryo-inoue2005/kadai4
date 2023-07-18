package omikuji;

/**
 * Daikichiクラス. <br>
 * Daikichiクラスは、大吉を設定します。
 *
 * @author Ryo.inoue
 * @version 1.00
 */
public class Daikichi extends Omikuji {

	public Daikichi() {

		this.unsei = "大吉";
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
