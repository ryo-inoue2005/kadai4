package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.ParseException;

import common.ConvertData;
import common.DataBaseAcess;

/**
 * RegisterOmikujiDaoクラス. <br>
 * RegisterOmikujiDaoクラスは、データベース登録処理を行います。
 *
 * @author Ryo.inoue
 * @version 1.00
 */
public class RegisterOmikujiDao {

	/** データベースアクセスオブジェクト */
	private final DataBaseAcess dba = new DataBaseAcess();

	/**
	 * CSVに入っているおみくじをデータベースに登録します。
	 * 既に登録されているおみくじは登録されません
	 * 
	 * @throws ClassNotFoundException
	 *			クラス未発見の例外
	 * @throws SQLException
	 * 			SQL例外
	 * @throws IOException 
	 * 
	 */
	public void registerOmikuji(String filePath) throws ClassNotFoundException, SQLException, IOException {

		//CSVデータ読み込み
		File file = new File(filePath);
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "Shift-JIS"));

		try {
			// データベース接続
			dba.open();

			StringBuilder sql = new StringBuilder();
			sql.append(
					"INSERT INTO omikuji (omikuji_code, unsei_code, negaigoto, akinai, gakumon, create_by, create_date) ");
			sql.append("SELECT ?, ");
			sql.append("(SELECT unsei_code "
					+ "FROM unseimaster "
					+ "WHERE unsei_name = ?), ");
			sql.append("?, ?, ?, 'Ryo.inoue', CURRENT_DATE ");
			sql.append("ON CONFLICT DO NOTHING; ");

			// SQL文をセット
			dba.setSql(sql.toString());

			// 初期化
			String line = null;
			GetOmikujiDao getTable = new GetOmikujiDao();
			int nextCode = 1;

			// 登録されているおみくじコードの最大値の次のおみくじコードを格納
			int omikujiCode = getTable.getMaxOmikujiCode() + nextCode;

			// CSVの行数がなくなるまで実行し、一行ずつデータベースに登録する
			while ((line = br.readLine()) != null) {

				// 一行をコンマごとに分解する
				String[] csvData = line.split(",");

				// CSVから受け取ったデータをセットし、データベースに登録
				dba.setData(1, omikujiCode);
				dba.setData(2, csvData[0]);
				dba.setData(3, csvData[1]);
				dba.setData(4, csvData[2]);
				dba.setData(5, csvData[3]);

				// 登録処理が行われた場合、おみくじコードをインクリメントする
				if (dba.update() == 1) {
					omikujiCode++;
				}
			}

		} finally {
			// クローズ処理
			dba.close();
			br.close();
		}
	}

	/**
	 * おみくじの結果をデータベースに登録します。
	 * 
	 * @throws ClassNotFoundException
	 *			クラス未発見例外
	 * @throws SQLException
	 * 			SQL例外
	 * @throws ParseException
	 * 			変換時の例外
	 * 
	 * @param birthday
	 * 			誕生日
	 * @param omikujiCode
	 * 			おみくじコード
	 * 
	 * @return 登録件数
	 */
	public int registerResult(int omikujiCode, String birthDay)
			throws ClassNotFoundException, SQLException, ParseException {

		try {
			// データベース接続
			dba.open();

			StringBuilder sql = new StringBuilder();

			sql.append("INSERT INTO result (uranai_date, birthday, omikuji_code, create_by, create_date) ");
			sql.append("VALUES (CURRENT_DATE, ?, ");
			sql.append("(SELECT omikuji_code FROM omikuji WHERE omikuji_code = ? ), ");
			sql.append("'Ryo.inoue', CURRENT_DATE) ");

			// SQL文セット
			dba.setSql(sql.toString());
			dba.setData(1, ConvertData.toSqlDate(birthDay));
			dba.setData(2, omikujiCode);

			// UPDATE処理実行
			return dba.update();

		} finally {
			// クローズ処理
			dba.close();
		}
	}
}
