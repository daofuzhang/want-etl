/**
 * -------------------------------------------------------
 * @FileName：MonitorUtil.java
 * @Description：简要描述本文件的内容
 * @Author：Dirk.Lee
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.worker.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.want.worker.dto.JDBCServerDTO;
import com.want.worker.sync.util.JDBCUtil;

/**
 * @author 80005151
 *
 */
public class MonitorUtil {
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static int getSid(JDBCServerDTO server) {
		int sid = -1;
		if (server == null) {
			return sid;
		}
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		try {
			connection = JDBCUtil.getConnection("oracle.jdbc.driver.OracleDriver", server.getUrl(),
					server.getUsername(), server.getPassword());
			String sql = "select BATCH.BATCH_STATUS_SEQ.nextval as sid from dual";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.execute();
			ResultSet resultSet = preparedStatement.getResultSet();
			while (resultSet.next()) {
				sid = resultSet.getInt("SID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return sid;
	}

	public static void sendStartJob(JDBCServerDTO server, String jobName, int sid) {
		if (server == null || sid == -1) {
			return;
		}
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		try {
			Date startDate = new Date();
			connection = JDBCUtil.getConnection("oracle.jdbc.driver.OracleDriver", server.getUrl(),
					server.getUsername(), server.getPassword());
			String sql = "INSERT INTO BATCH.BATCH_STATUS(SID, FUNC_ID, START_DATE, END_DATE, STATUS, REASON, IS_SEND) "
					+ "VALUES  (?, ?, to_date(? ,'yyyy-mm-dd hh24:mi:ss'), ?, ?, ?, ?)";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, sid);
			preparedStatement.setString(2, jobName);
			preparedStatement.setString(3, format.format(startDate));
			preparedStatement.setString(4, "");
			preparedStatement.setString(5, "2");
			preparedStatement.setString(6, "");
			preparedStatement.setString(7, "");
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void sendErrorJob(JDBCServerDTO server, int sid, String errorMessage) {
		if (server == null || sid == -1) {
			return;
		}
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		try {
			Date errorDate = new Date();
			connection = JDBCUtil.getConnection("oracle.jdbc.driver.OracleDriver", server.getUrl(),
					server.getUsername(), server.getPassword());
			String sql = "UPDATE BATCH.BATCH_STATUS SET " + "END_DATE = to_date(? ,'yyyy-mm-dd hh24:mi:ss'), "
					+ "STATUS = ?, " + "REASON = ? " + "WHERE SID = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, format.format(errorDate));
			preparedStatement.setString(2, "0");
			preparedStatement.setString(3, errorMessage);
			preparedStatement.setInt(4, sid);
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void sendSuccessJob(JDBCServerDTO server, int sid) {
		if (server == null || sid == -1) {
			return;
		}
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		try {
			Date endDate = new Date();
			connection = JDBCUtil.getConnection("oracle.jdbc.driver.OracleDriver", server.getUrl(),
					server.getUsername(), server.getPassword());
			preparedStatement = null;
			String sql = "UPDATE BATCH.BATCH_STATUS SET " + "END_DATE = to_date(? ,'yyyy-mm-dd hh24:mi:ss'), "
					+ "STATUS = ? " + "WHERE SID = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, format.format(endDate));
			preparedStatement.setString(2, "1");
			preparedStatement.setInt(3, sid);
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
