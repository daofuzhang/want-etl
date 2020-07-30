/**
 * -------------------------------------------------------
 * @FileName：ProcessUtil.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProcessUtil {

	public static boolean isProcessIdExist(String pid) {
		try {
			String line;
			Process p = Runtime.getRuntime().exec("ps -p " + pid);
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((line = input.readLine()) != null) {
				if (line.contains(pid)) {
					return true;
				}
//				System.out.println(line);
			}
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean runJob(String ymlFileName) {
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append(System.getProperty("java.home"));
			buffer.append("/bin/java JobWorker.jar:resources com.want.App job/");
			buffer.append(ymlFileName);
			buffer.append(".yml &");
			String command = buffer.toString();
			
			Process p = Runtime.getRuntime().exec(command);
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			
			input.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}
