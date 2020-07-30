/**
 * -------------------------------------------------------
 * @FileName：StreamFactory.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.worker.sync.util;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.want.worker.sync.model.Column;
import com.want.worker.sync.model.ConnectionType;

public class JDBCUtil {

	public static boolean execute(ConnectionType type, String url, String user, String password, String command)
			throws SQLException {
		Connection connection = getConnection(type, url, user, password);
		PreparedStatement statement = connection.prepareStatement(command);
		statement.execute();
		statement.close();
		connection.close();
		return true;
	}

	public static int executeUpdate(ConnectionType type, String url, String user, String password, String command)
			throws SQLException {
		Connection connection = getConnection(type, url, user, password);
		PreparedStatement statement = connection.prepareStatement(command);
		int count = statement.executeUpdate();
		statement.close();
		connection.close();
		return count;
	}

	public static String call(ConnectionType type, String url, String user, String password, String command)
			throws SQLException {
		StringBuffer buffer = new StringBuffer();
		Connection connection = getConnection(type, url, user, password);
		CallableStatement statement = connection.prepareCall(command);
		try (ResultSet rs = statement.executeQuery()) {
			while (rs.next()) {
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					buffer.append(rs.getString(i));
				}
				buffer.append(System.lineSeparator());
			}
		} catch (SQLException e) {
			if (!e.getMessage().equals("ResultSet is from UPDATE. No Data.")) {
				throw e;
			}
		}
		while (statement.getMoreResults()) {
			try (ResultSet rs = statement.getResultSet()) {
				while (rs.next()) {
					for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
						buffer.append(rs.getString(i));
					}
					buffer.append(System.lineSeparator());
				}
			} catch (SQLException e) {
				if (!e.getMessage().equals("ResultSet is from UPDATE. No Data.")) {
					throw e;
				}
			}
		}
		statement.close();
		connection.close();

		String msg = buffer.toString();
		System.out.println(msg);
		return msg;
	}

	/**
	 * 查詢資料。
	 * 
	 * @param type
	 * @param url
	 * @param username
	 * @param password
	 * @param statement
	 * @return
	 *
	 * @author Luke.Tsai
	 * @throws SQLException
	 */
	public static List<Map<String, Object>> query(ConnectionType type, String url, String user, String password,
			String command) throws SQLException {
		System.out.println(command);
		Connection connection = getConnection(type, url, user, password);
		PreparedStatement statement = connection.prepareStatement(command);
		ResultSet rs = statement.executeQuery();
		ResultSetMetaData rsmd = rs.getMetaData();
		List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
		while (rs.next()) {
			Map<String, Object> value = new HashMap<String, Object>();
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				value.put(rsmd.getColumnLabel(i), rs.getString(i));
			}
			ret.add(value);
		}
		return ret;
	}

	public static int getTotalCount(ConnectionType type, String url, String user, String password, String command)
			throws SQLException {
		int totalCount = 0;
		Connection connection = getConnection(type, url, user, password);
		PreparedStatement statement = connection.prepareStatement("SELECT COUNT(1) FROM (" + command + ") A");
		ResultSet rs = statement.executeQuery();
		if (rs.next()) {
			totalCount = rs.getInt(1);
		}
		statement.close();
		connection.close();
		return totalCount;
	}

	public static List<String> getColumnLabels(ConnectionType type, String url, String user, String password,
			String command) throws SQLException {
		String sql;
		int fetchSize;
		switch (type) {
		case MYSQL:
			sql = "SELECT * FROM (" + command + ") A LIMIT 1";
			fetchSize = Integer.MIN_VALUE;
			break;
		case ORACLE:
			sql = "SELECT * FROM (" + command + ") A  WHERE ROWNUM = 1";
			fetchSize = 1;
			break;
		case HANA:
			sql = "SELECT * FROM (" + command + ") A LIMIT 1";
			fetchSize = 1;
			break;
		default:
			sql = "SELECT * FROM (" + command + ") A LIMIT 1";
			fetchSize = 1;
			break;
		}
		List<String> columnLabels = new ArrayList<>();
		Connection connection = getConnection(type, url, user, password);
		Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		statement.setFetchSize(fetchSize);
		ResultSet rs = statement.executeQuery(sql);
		ResultSetMetaData rsmd = rs.getMetaData();
		for (int i = 1; i <= rsmd.getColumnCount(); i++) {
			columnLabels.add(rsmd.getColumnLabel(i));
		}
		statement.close();
		connection.close();
		return columnLabels;
	}

	public static List<Column> getColumns(ConnectionType type, String url, String user, String password, String db,
			String table) throws SQLException {
		Connection connection = getConnection(type, url, user, password);

		List<Column> columns = new ArrayList<>();
		DatabaseMetaData dmd = connection.getMetaData();
		Map<String, Integer> temp = new HashMap<String, Integer>();
		ResultSet rs = dmd.getPrimaryKeys(db, db, table);
		while (rs.next()) {
			if (rs.getString("PK_NAME").equalsIgnoreCase("PRIMARY")) {
				temp.put(rs.getString("COLUMN_NAME"), rs.getInt("KEY_SEQ"));
			}
		}

		rs = dmd.getColumns(db, db, table, null);
		while (rs.next()) {
			Column column = new Column();
			column.setName(rs.getString("COLUMN_NAME"));
			column.setDataType(rs.getInt("DATA_TYPE"));
			column.setKeySeq(temp.get(column.getName()));
			columns.add(column);
		}
		connection.close();
		return columns;
	}

	public static Stream<Map<String, Object>> stream(String className, String url, String user, String password,
			int fetchSize, String sql) throws SQLException {
		return stream(getConnection(className, url, user, password), fetchSize, sql);
	}

	public static Stream<Map<String, Object>> stream(Connection connection, int fetchSize, String sql)
			throws SQLException {
		Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		statement.setFetchSize(fetchSize);
		ResultSet rs = statement.executeQuery(sql);
		int columns = rs.getMetaData().getColumnCount();
		Map<String, Object> resultMap = new HashMap<String, Object>(columns);

		Stream<Map<String, Object>> resultStream = StreamSupport
				.stream(new Spliterators.AbstractSpliterator<Map<String, Object>>(Long.MAX_VALUE,
						Spliterator.ORDERED | Spliterator.NONNULL | Spliterator.IMMUTABLE) {
					@Override
					public boolean tryAdvance(Consumer<? super Map<String, Object>> action) {
						try {
							if (!rs.next()) {
								return false;
							}
							resultMap.clear();
							for (int i = 1; i <= columns; i++) {
								resultMap.put(rs.getMetaData().getColumnLabel(i), rs.getObject(i));
							}
							action.accept(resultMap);
							return true;
						} catch (SQLException e) {
							throw new RuntimeException(e);
						}
					}
				}, false).onClose(new Runnable() {

					@Override
					public void run() {
						if (statement != null) {
							try {
								statement.close();
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
						if (connection != null) {
							try {
								connection.close();
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
					}
				});
		return resultStream;
	}

	public static Connection getConnection(String className, String url, String user, String password) {
		try {
			Class.forName(className);
			return DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Connection getConnection(ConnectionType type, String url, String user, String password) {
		String className = null;
		switch (type) {
		case MYSQL:
			className = "com.mysql.cj.jdbc.Driver";
			break;
		case ORACLE:
			className = "oracle.jdbc.driver.OracleDriver";
			break;
		case HANA:
			className = "com.sap.db.jdbc.Driver";
			break;
		}
		return JDBCUtil.getConnection(className, url, user, password);
	}

	public static int getQueryFetchSize(ConnectionType type) {
		switch (type) {
		case MYSQL:
			return Integer.MIN_VALUE;
		case ORACLE:
			return 2000;
		case HANA:
			return 10000;
		}
		return -1;
	}

	public static int getInsertFetchSize(ConnectionType type) {
		switch (type) {
		case MYSQL:
			return 10000;
		case ORACLE:
			return 100;
		case HANA:
			return -1;
		}
		return -1;
	}

	public static String formatName(ConnectionType type, String table) {
		switch (type) {
		case MYSQL:
			return "`" + table + "`";
		case ORACLE:
			return "\"" + table + "\"";
		case HANA:
			return "\"" + table + "\"";
		}
		return null;
	}

	public static String getRangeQueryCommand(ConnectionType type, String queryCommand, int start, int size) {
		switch (type) {
		case MYSQL:
			return "SELECT * FROM (" + queryCommand + ") A LIMIT " + start + ", " + size + ";";
		case ORACLE:
			return "SELECT a.* FROM ( SELECT b.*, ROWNUM AS RNUM FROM( " + queryCommand + " ) b WHERE ROWNUM <= "
					+ (start + size) + " ) a WHERE RNUM > " + start;
		case HANA:
			return "SELECT * FROM (" + queryCommand + ") A LIMIT " + size + " OFFSET " + start;
		}
		return null;
	}

	public static String getInsertCommand(ConnectionType type, String db, String table, List<Column> columns,
			int fetchSize) {
		StringBuffer buffer;
		switch (type) {
		case MYSQL:
			buffer = new StringBuffer();
			for (int i = 0; i < fetchSize; i++) {
				if (i > 0) {
					buffer.append(",");
				}
				buffer.append("(");
				for (int j = 0; j < columns.size(); j++) {
					if (j > 0) {
						buffer.append(",");
					}
					buffer.append("?");
				}
				buffer.append(")");
			}
			return "REPLACE INTO " + db + "." + formatName(type, table) + " VALUES" + buffer.toString() + ";";
		case ORACLE:
			buffer = new StringBuffer();

			StringBuffer temp = new StringBuffer("INTO " + db + "." + formatName(type, table) + "(");
			for (int index = 0; index < columns.size(); index++) {
				Column column = columns.get(index);
				if (index > 0) {
					temp.append(", ");
				}
				temp.append("\"");
				temp.append(column.getName());
				temp.append("\"");
			}
			temp.append(") VALUES( ");

			for (int i = 0; i < fetchSize; i++) {
				buffer.append(temp.toString());
				for (int j = 0; j < columns.size(); j++) {
					if (j > 0) {
						buffer.append(", ");
					}
					buffer.append("?");
				}
				buffer.append(") ");
			}

			return "INSERT ALL " + buffer.toString() + "SELECT 1 FROM DUAL";
		case HANA:
			return "";
		}
		return null;
	}

	public static Object getDefaultValue(int dataType) {
		switch (dataType) {
		case Types.CHAR:
		case Types.VARCHAR:
		case Types.NCHAR:
		case Types.NVARCHAR:
		case Types.LONGVARCHAR:
		case Types.LONGNVARCHAR:
		case Types.BLOB:
		case Types.CLOB:
		case Types.NCLOB:
			return "";
		case Types.INTEGER:
		case Types.SMALLINT:
		case Types.BIGINT:
			return 0;
		case Types.REAL:
			return 0f;
		case Types.FLOAT:
		case Types.DOUBLE:
			return 0d;
		case Types.NUMERIC:
		case Types.DECIMAL:
			return BigDecimal.ZERO;
		case Types.DATE:
			return new Date(0);
		default:
			break;
		}
		return null;
	}

}
