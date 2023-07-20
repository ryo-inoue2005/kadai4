package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import common.ConvertData;
import common.DataBaseAcess;
import omikuji.Omikuji;
import omikuji.OmikujiFactory;

/**
 * GetDaoクラス. <br>
 * GetDaoクラスは、データベースからテーブル情報を取得します。
 *
 * @author Ryo.inoue
 * @version 1.00
 */
public class GetOmikujiDao {

	/** データベースアクセスオブジェクト */
	DataBaseAcess dba = new DataBaseAcess();

	/**
	 * resultテーブルに保存されているおみくじ結果を取得します。
	 * 同一誕生日、同一日ならば、おみくじ結果を返す。
	 * 
	 * @throws ClassNotFoundException
	 *			クラス未発見例外
	 * @throws SQLException
	 * 			SQL例外
	 * 
	 * @param birthday
	 * 			誕生日
	 * 
	 * @return Omikuji
	 * 			おみくじオブジェクト
	 * @return null
	 * 			取得できない場合
	 */
	public int getResult(String birthDay) throws ClassNotFoundException, SQLException {

		try {
			// データベース接続
			dba.open();

			StringBuilder sql = new StringBuilder();

			sql.append("SELECT omikuji_code ");
			sql.append("FROM result ");
			sql.append("WHERE birthday = ? ");
			sql.append("AND create_date = CURRENT_DATE ");

			// SQL文をセット
			dba.setSql(sql.toString());

			// 文字列をDate型に変換してセット
			dba.setData(1, ConvertData.toSqlDate(birthDay));

			// SQLを実行
			ResultSet resultSet = dba.select();

			if (resultSet.next()) {

				return resultSet.getInt("omikuji_code");
			}
			return 0;

		} finally {
			// データベース切断
			dba.close();
		}

	}

	/**
	 * データベースからおみくじを取得します。
	 * 
	 * @throws ClassNotFoundException
	 *			クラス未発見例外
	 * @throws SQLException
	 * 			SQL例外
	 * 
	 * @param random
	 * 			ランダムな数値
	 * 
	 * @return Omikuji
	 * 			おみくじオブジェクト
	 * @return null
	 * 			取得できない場合
	 */
	public Omikuji drawOmikuji(int random) throws ClassNotFoundException, SQLException {

		try {
			// データベース接続
			dba.open();

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT u.unsei_name, o.negaigoto, o.akinai, o.gakumon ");
			sql.append("FROM unseimaster AS u ");
			sql.append("INNER JOIN omikuji AS o ON u.unsei_code = o.unsei_code ");
			sql.append("WHERE o.omikuji_code = ?");

			dba.setSql(sql.toString());
			dba.setData(1, random);

			ResultSet resultSet = dba.select();

			Omikuji omikuji = null;

			if (resultSet.next()) {
				// 運勢を元におみくじオブジェクトを生成
				omikuji = OmikujiFactory.create(resultSet.getString("unsei_name"));
				// 運勢をセット
				omikuji.setUnsei(resultSet.getString("negaigoto"), resultSet.getString("akinai"),
						resultSet.getString("gakumon"));

				return omikuji;
			}
			return null;

		} finally {
			// データベース切断
			dba.close();
		}
	}

	/**
	 * データベースからおみくじの数を取得します。
	 * 
	 * @throws ClassNotFoundException
	 *			クラス未発見例外
	 * @throws SQLException
	 * 			SQL例外
	 * 
	 * @return	おみくじの数
	 * 
	 * @return 0
	 *			おみくじがない場合
	 */
	public int getMaxOmikujiCode() throws ClassNotFoundException, SQLException {
		try {
			dba.open();
			dba.setSql("SELECT MAX(omikuji_code) AS MAX FROM omikuji");
			ResultSet resultSet = dba.select();

			if (resultSet.next()) {
				return resultSet.getInt("MAX");
			}
			return 0;

		} finally {
			dba.close();
		}
	}
}
