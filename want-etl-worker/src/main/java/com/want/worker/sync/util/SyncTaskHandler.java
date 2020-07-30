/**
 * -------------------------------------------------------
 * @FileName：SyncTaskHandler.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.worker.sync.util;

import java.text.DecimalFormat;

public class SyncTaskHandler implements SyncTaskRunable.SyncTaskRunableCallback {

	private final int total;
	private int current;

	/**
	 * @param totalCount
	 */
	public SyncTaskHandler(int total) {
		this.total = total;
	}

	public int getCurrent() {
		return current;
	}

	public int getTotal() {
		return total;
	}

	@Override
	public synchronized void insertSucess(int size) {
		current += size;
		System.out.print(convertProgress(current, total) + "(" + current + "/" + total + ")\r");
	}

	private String convertProgress(int remain, int total) {
		if (remain > total) {
			throw new IllegalArgumentException();
		}
		double maxBareSize = 50;
		double remainProcent = ((double) remain / (double) total) * maxBareSize;

		String icon = "█";
		StringBuilder progress = new StringBuilder();
		progress.append("[");
		for (int i = 0; i < remainProcent; i++) {
			progress.append(icon);
		}
		char defaultChar = '-';
		progress.append(new String(new char[(int) (maxBareSize - remainProcent)]).replace('\0', defaultChar));
		progress.append("] ");
		progress.append(paddingLeft(new DecimalFormat("##.00").format(remainProcent * (100 / maxBareSize)), 6));
		progress.append("%");
		return progress.toString();
	}

	private String paddingLeft(String str, int... lengths) {
		int size = str.length();
		for (int length : lengths) {
			if (str.length() < length && (size == str.length() || size > length)) {
				size = length;
			}
		}
		return String.format("%1$" + size + "s", str);
	}

}
