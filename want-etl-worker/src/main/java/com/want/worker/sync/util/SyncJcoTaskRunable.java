/**
 * -------------------------------------------------------
 * @FileName：SyncJcoTaskRunable.java
 * @Description：简要描述本文件的内容
 * @Author：Dirk.Lee
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
import com.want.worker.sync.model.SyncJcoTask;
import com.want.worker.sync.util.SyncTaskRunable.SyncTaskRunableCallback;

/**
 * @author 80005151
 *
 */
public class SyncJcoTaskRunable implements Runnable, Consumer<Map<String, String>> {
	final SyncJcoTask task;
	final List<Column> columns;
	// final int start;
	final int size;
	final int insertFetchSize;
	private Connection connection;
	private PreparedStatement statement;
	private int fetchIndex = 0;
	private int rowIndex = 0;
	private List<Map<String, String>> result;
	private SyncTaskRunableCallback callback;

	// public SyncJcoTaskRunable(SyncJcoTask task, List<Column> columns, int start,
	// int size) {
	// this.task = task;
	// this.columns = columns;
	// this.start = start;
	// this.size = size;
	// this.insertFetchSize = JDBCUtil.getInsertFetchSize(task.gettType());
	// }

	/**
	 * @param task2
	 * @param columns2
	 * @param result
	 */
	public SyncJcoTaskRunable(SyncJcoTask task, List<Column> columns, List<Map<String, String>> result) {
		this.task = task;
		this.columns = columns;
		this.insertFetchSize = JDBCUtil.getInsertFetchSize(task.gettType());
		this.result = result;
		size = result.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {
			connection = JDBCUtil.getConnection(task.gettType(), task.gettUrl(), task.gettUser(), task.gettPwd());
			connection.setCatalog(task.gettDb());
			connection.setAutoCommit(false);

			result.forEach(this);

			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.function.Consumer#accept(java.lang.Object)
	 */
	@Override
	public void accept(Map<String, String> t) {
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
				if (dataType == Types.DATE && obj.equals("0000-00-00")) {
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

	public void setCallback(SyncTaskRunableCallback callback) {
		this.callback = callback;
	}
}
