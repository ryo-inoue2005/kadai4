package omikuji;

/**
 * Kyoクラス. <br>
 * Kyoクラスは、凶を設定します。
 *
 * @author Ryo.inoue
 * @version 1.00
 */
public class Kyo extends Omikuji {

	public Kyo() {
		
		this.unsei = "凶";
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
