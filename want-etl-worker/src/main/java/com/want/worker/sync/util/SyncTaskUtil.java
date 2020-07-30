/**
 * -------------------------------------------------------
 * @FileName：SyncTaskWorkerImpl.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.worker.sync.util;

import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.want.worker.sync.model.Column;
import com.want.worker.sync.model.SyncJcoTask;
import com.want.worker.sync.model.SyncTask;

public class SyncTaskUtil {

	public static String sync(SyncTask task) throws Exception {
		// 檢查線程數
		int availableProcessors = Runtime.getRuntime().availableProcessors();
		if (task.getCoreSize() > availableProcessors) {
			task.setCoreSize(availableProcessors);
		}

		// 設定線程池
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(task.getCoreSize(), task.getCoreSize() * 2, 5L,
				TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
		threadPoolExecutor.allowCoreThreadTimeOut(true);

		// 取得總數
		int totalCount = JDBCUtil.getTotalCount(task.getfType(), task.getfUrl(), task.getfUser(), task.getfPwd(),
				task.getfQueryCommand());

		// 取得來源欄位
		List<String> columnLabels = JDBCUtil.getColumnLabels(task.getfType(), task.getfUrl(), task.getfUser(),
				task.getfPwd(), task.getfQueryCommand());

		// 取得目標欄位資訊
		List<Column> columns = JDBCUtil.getColumns(task.gettType(), task.gettUrl(), task.gettUser(), task.gettPwd(),
				task.gettDb(), task.gettTable());

		if (!equalsColumns(columnLabels, columns)) {
			throw new Exception("欄位不一致。");
		}

		int insertFetchSize = JDBCUtil.getInsertFetchSize(task.gettType());

		// 計算連線數量
		int partSize = (int) (Math.ceil((double) totalCount / (double) insertFetchSize) > threadPoolExecutor
				.getCorePoolSize() ? threadPoolExecutor.getCorePoolSize()
						: Math.ceil((double) totalCount / (double) insertFetchSize));

		// 計算單連線同步資料量
		int partRowSize = totalCount > 0 ? totalCount / partSize : 0;

		// 分批執行同步
		SyncTaskHandler syncTaskHandler = new SyncTaskHandler(totalCount);
		for (int i = 0; i < partSize; i++) {
			int start = i * partRowSize;
			int size = (i + 1) == partSize ? totalCount - start : partRowSize;
			SyncTaskRunable runable = new SyncTaskRunable(task, columns, start, size);
			runable.setCallback(syncTaskHandler);
			threadPoolExecutor.execute(runable);
		}

		// 等待同步結束
		threadPoolExecutor.shutdown();
		try {
			while (!threadPoolExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
				if (syncTaskHandler.getCurrent() == syncTaskHandler.getTotal()) {
					break;
				}
			}
		} catch (InterruptedException ex) {
			ex.printStackTrace();
			threadPoolExecutor.shutdownNow();
			Thread.currentThread().interrupt();
		} finally {
			System.out.println();
		}
		return syncTaskHandler.getCurrent() + "/" + syncTaskHandler.getTotal();
	}

	public static String syncJco(SyncJcoTask task) throws Exception {
		// 檢查線程數
		int availableProcessors = Runtime.getRuntime().availableProcessors();
		if (task.getCoreSize() > availableProcessors) {
			task.setCoreSize(availableProcessors);
		}

		// 設定線程池
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(task.getCoreSize(), task.getCoreSize() * 2, 5L,
				TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
		threadPoolExecutor.allowCoreThreadTimeOut(true);

		List<Map<String, String>> result = JcoUtil.getSyncData(task.getjCOServer(), task.getfTable(), task.getfMap());

		// 取得總數
		int totalCount = result.size();

		// 取得來源欄位
		List<String> columnLabels = JcoUtil.getColumnLabels(task.getjCOServer(), task.getfTable(), task.getfMap());

		// 取得目標欄位資訊
		List<Column> columns = JDBCUtil.getColumns(task.gettType(), task.gettUrl(), task.gettUser(), task.gettPwd(),
				task.gettDb(), task.gettTable());

		if (!equalsColumns(columnLabels, columns)) {
			throw new Exception("欄位不一致。");
		}

		// int insertFetchSize = JDBCUtil.getInsertFetchSize(task.gettType());
		//
		// // 計算連線數量
		// int partSize = (int) (Math.ceil((double) totalCount / (double)
		// insertFetchSize) > threadPoolExecutor
		// .getCorePoolSize() ? threadPoolExecutor.getCorePoolSize()
		// : Math.ceil((double) totalCount / (double) insertFetchSize));
		//
		// // 計算單連線同步資料量
		// int partRowSize = totalCount > 0 ? totalCount / partSize : 0;

		// 分批執行同步
		SyncTaskHandler syncTaskHandler = new SyncTaskHandler(totalCount);
		// for (int i = 0; i < partSize; i++) {
		// int start = i * partRowSize;
		// int size = (i + 1) == partSize ? totalCount - start : partRowSize;

		SyncJcoTaskRunable runable = new SyncJcoTaskRunable(task, columns, result);
		runable.setCallback(syncTaskHandler);
		threadPoolExecutor.execute(runable);
		// }

		// 等待同步結束
		threadPoolExecutor.shutdown();
		try {
			while (!threadPoolExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
				if (syncTaskHandler.getCurrent() == syncTaskHandler.getTotal()) {
					break;
				}
			}
		} catch (InterruptedException ex) {
			ex.printStackTrace();
			threadPoolExecutor.shutdownNow();
			Thread.currentThread().interrupt();
		} finally {
			System.out.println();
		}
		return syncTaskHandler.getCurrent() + "/" + syncTaskHandler.getTotal();
	}

	private static boolean equalsColumns(List<String> columnLabels, List<Column> columns) {
		if (columnLabels.size() != columns.size()) {
			return false;
		}
		for (Column column : columns) {
			if (columnLabels.contains(column.getName())) {
				continue;
			}
			return false;
		}
		return true;
	}

}
