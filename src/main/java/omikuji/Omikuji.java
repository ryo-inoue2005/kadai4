package omikuji;

/**
 * Omikujiクラス. <br>
 * Omikujiクラスは、おみくじ周りを管理します。
 *
 * @author Ryo.inoue
 * @version 1.00
 */
public abstract class Omikuji implements Fortune {

	/** 運勢を表します */
	protected String unsei;
	/** 願い事を表します */
	protected String negaigoto;
	/** 商いを表します */
	protected String akinai;
	/** 学問を表します */
	protected String gakumon;

	/**
	 * 運勢の取得
	 */
	public String getUnsei() {
		return unsei;
	}

	/**
	 * 願い事の取得
	 */
	public String getNegaigoto() {
		return negaigoto;
	}

	/**
	 * 商いの取得
	 */
	public String getAkinai() {
		return akinai;
	}

	/**
	 * 学問の取得
	 */
	public String getGakumon() {
		return gakumon;
	}

	/**
	 * 運勢をセットします。	
	 * 
	 * @param negaigoto
	 * 			願い事
	 * @param akinai
	 * 			商い
	 * @param gakumon
	 * 			学問
	 */
	public abstract void setUnsei(String negaigoto, String akinai, String gakumon);

	/**
	 * @see Fortune#disp()
	 */
	@Override
	public String disp() {
		// システムの改行を使う
		final String lf = System.getProperty("line.separator");
		
		StringBuilder sb = new StringBuilder();
		sb.append(String.format(DISP_STR, getUnsei()));
		sb.append(lf);
		sb.append("願い事：" + getNegaigoto());
		sb.append(lf);
		sb.append("商い：" + getAkinai());
		sb.append(lf);
		sb.append("学問：" + getGakumon());
		sb.append(lf);
		return sb.toString();
	}
}
