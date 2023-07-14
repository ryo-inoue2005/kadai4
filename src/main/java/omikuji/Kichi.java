package omikuji;

/**
 * Kichiクラス. <br>
 * Kichiクラスは、吉を設定します。
 *
 * @author Ryo.inoue
 * @version 1.00
 */
public class Kichi extends Omikuji {

	public Kichi() {

		this.unsei = "吉";
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
