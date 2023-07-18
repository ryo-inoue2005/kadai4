package common;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DataBaseAcessクラス. <br>
 * DataBaseAcessクラスは、データベースまわりの作業を管理します。
 *
 * @author Ryo.inoue
 * @version 1.00
 */
public final class DataBaseAcess {

	/** 接続用オブジェクト */
	private Connection connection;
	/** データベース操作オブジェクト */
	private PreparedStatement preparedStatement;

	/**
	 * データベースにアクセスします。
	 * 
	 * @throws ClassNotFoundException
	 * 			ファイル未発見例外
	 * @throws SQLException
	 * 			SQL例外
	 */
	public void open() throws ClassNotFoundException, SQLException {
		// ドライバ読み込み
		Class.forName("org.postgresql.Driver");
		this.connection = DriverManager.getConnection("jdbc:postgresql://localhost/uranai", "postgres", "aaaaaajh");
	}

	/**
	 * PreparedStatementにSQL文をセットします
	 * 
	 * @param sql
	 * 			SQL文
	 * @throws SQLException
	 * 			SQL例外
	 */
	public void setSql(String sql) throws SQLException {
		this.preparedStatement = this.connection.prepareStatement(sql);
	}

	/**
	 * 指定されたパラメータを指定されたインデックスにセットします。
	 * 
	 * @param index
	 * 			インデックス
	 * @param data
	 * 			セットする値
	 * @throws SQLException
	 * 			SQL例外
	 */
	public void setData(int index, String data) throws SQLException {
		this.preparedStatement.setString(index, data);
	}
	
	/**
	 * 指定されたパラメータを指定されたインデックスにセットします。
	 * 
	 * @param index
	 * 			インデックス
	 * @param data
	 * 			セットする値
	 * @throws SQLException
	 * 			SQL例外
	 */
	public void setData(int index, int data) throws SQLException {
		this.preparedStatement.setInt(index, data);
	}
	
	/**
	 * 指定されたパラメータを指定されたインデックスにセットします。
	 * 
	 * @param index
	 * 			インデックス
	 * @param data
	 * 			セットする値
	 * @throws SQLException
	 * 			SQL例外
	 */
	public void setData(int index, double data) throws SQLException {
		this.preparedStatement.setDouble(index, data);
	}

	/**
	 * 指定されたパラメータを指定されたインデックスにセットします。
	 * 
	 * @param index
	 * 			インデックス
	 * @param data
	 * 			セットする値
	 * @throws SQLException
	 * 			SQL例外
	 */
	public void setData(int index, Date data) throws SQLException {
		this.preparedStatement.setDate(index, data);
	}

	/**
	 * バッチ処理をするSQL文を追加します。
	 * 
	 * @throws SQLException
	 * 			SQL例外
	 */
	public void addBatch() throws SQLException {
		this.preparedStatement.addBatch();
	}

	/**
	 * SELECT文を実行します。
	 * 
	 * @throws SQLException
	 * 			SQL例外
	 */
	public ResultSet select() throws SQLException {
		return this.preparedStatement.executeQuery();
	}

	/**
	 * UPDATE文、INSERT文を実行します。
	 * 
	 * @throws SQLException
	 * 			SQL例外
	 */
	public int update() throws SQLException {
		return this.preparedStatement.executeUpdate();
	}

	/**
	 * バッチ処理を実行します。
	 * 
	 * @throws SQLException
	 * 			SQL例外
	 */
	public int[] executeBatch() throws SQLException {
		return this.preparedStatement.executeBatch();
	}

	/**
	 * データベースから切断します。
	 * 
	 * @throws SQLException
	 * 			SQL例外
	 */
	public void close() throws SQLException {

		if (this.connection != null) {
			this.connection.close();
		}

		if (this.preparedStatement != null) {
			this.preparedStatement.close();
		}
	}
}
