/**
 * -------------------------------------------------------
 * @FileName：StreamGobbler.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.service.worker.component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Consumer;

public class StreamGobbler extends Thread {

	final InputStream stream;
	final Consumer<String> action;

	public StreamGobbler(InputStream stream, Consumer<String> action) {
		this.stream = stream;
		this.action = action;
	}

	@Override
	public void run() {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(stream));) {
			br.lines().forEach(action);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}


