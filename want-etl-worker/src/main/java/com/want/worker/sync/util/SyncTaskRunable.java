/**
 * -------------------------------------------------------
 * @FileName：SyncTaskBlockImpl.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.worker.sync.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.want.worker.sync.model.Column;
import com.want.worker.sync.model.SyncTask;

public class SyncTaskRunable implements Runnable, Consumer<Map<String, Object>> {
	final SyncTask task;
	final List<Column> columns;
	final int start;
	final int size;
	final int insertFetchSize;
	private Connection connection;
	private PreparedStatement statement;
	private int fetchIndex = 0;
	private int rowIndex = 0;
	private SyncTaskRunableCallback callback;

	/**
	 * 
	 */
	public SyncTaskRunable(SyncTask task, List<Column> columns, int start, int size) {
		this.task = task;
		this.columns = columns;
		this.start = start;
		this.size = size;
		this.insertFetchSize = JDBCUtil.getInsertFetchSize(task.gettType());
	}

	/**
	 * @param callback
	 *            the callback to set
	 */
	public void setCallback(SyncTaskRunableCallback callback) {
		this.callback = callback;
	}

	@Override
	public void run() {
		try {
			connection = JDBCUtil.getConnection(task.gettType(), task.gettUrl(), task.gettUser(), task.gettPwd());
			connection.setCatalog(task.gettDb());
			connection.setAutoCommit(false);

			String queryCommand = JDBCUtil.getRangeQueryCommand(task.getfType(), task.getfQueryCommand(), start, size);
			System.out.println(queryCommand);
			JDBCUtil.stream(JDBCUtil.getConnection(task.getfType(), task.getfUrl(), task.getfUser(), task.getfPwd()),
					JDBCUtil.getQueryFetchSize(task.getfType()), queryCommand).forEach(this);

			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void accept(Map<String, Object> t) {
		if (statement == null) {
			String insertCommand = JDBCUtil.getInsertCommand(task.gettType(), task.gettDb(), task.gettTable(), columns,
					size - rowIndex > insertFetchSize ? insertFetchSize : size - rowIndex);
			try {
				statement = connection.prepareStatement(insertCommand);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		for (int j = 0; j < columns.size(); j++) {
			int parameterIndex = fetchIndex * columns.size() + j + 1;
			Column column = columns.get(j);
			try {
				int dataType = column.getDataType();
				Object obj = t.get(column.getName());
				if (dataType == Types.DATE && obj != null && obj.equals("0000-00-00")) {
					obj = null;
				}
				statement.setObject(parameterIndex, obj == null ? JDBCUtil.getDefaultValue(dataType) : obj, dataType);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (fetchIndex == (insertFetchSize - 1) || rowIndex == (size - 1)) {
			try {
				statement.executeUpdate();
				statement.close();
				connection.commit();
				callback.insertSucess(fetchIndex + 1);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				statement = null;
			}
			fetchIndex = 0;
		} else {
			fetchIndex++;
		}
		rowIndex++;
	}

	public interface SyncTaskRunableCallback {

		void insertSucess(int size);

	}

}
