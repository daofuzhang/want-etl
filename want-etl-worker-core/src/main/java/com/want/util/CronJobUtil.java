/**
 * -------------------------------------------------------
 * @FileName：CronJobUtil.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.want.modal.CronJob;
import com.want.modal.JobInfo;

public class CronJobUtil {

	public static boolean generateCrontab(List<CronJob> list) {
		try {
			FileWriter writer = new FileWriter(new File(System.getProperty("user.dir") + File.separator + "cronjob"));
			for (CronJob crontab : list) {
				File file = generateShellScript(crontab);
				if (file == null) {
					continue;
				}
				writer.append(StringUtil.isEmpty(crontab.getMinutes()) ? "*" : crontab.getMinutes());
				writer.append(" ");
				writer.append(StringUtil.isEmpty(crontab.getHours()) ? "*" : crontab.getHours());
				writer.append(" ");
				writer.append(StringUtil.isEmpty(crontab.getDay()) ? "*" : crontab.getDay());
				writer.append(" ");
				writer.append(StringUtil.isEmpty(crontab.getMonth()) ? "*" : crontab.getMonth());
				writer.append(" ");
				writer.append(StringUtil.isEmpty(crontab.getDow()) ? "*" : crontab.getDow());
				writer.append("\tcd ");
				writer.append(System.getProperty("user.dir"));
				writer.append("; sh ");
				writer.append(file.getName());
				writer.append(System.lineSeparator());
			}
			writer.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static File generateShellScript(CronJob crontab) {
		try {
			File file = new File(
					System.getProperty("user.dir") + File.separator + "cronjob-" + crontab.getName() + ".sh");
			FileWriter writer = new FileWriter(file);
			writer.append("#!/bin/sh");
			writer.append(System.lineSeparator());
			writer.append("export WWRD_PBE_PASSWORD=\"" + System.getenv("WWRD_PBE_PASSWORD") + "\"");
			writer.append(System.lineSeparator());
			for (int i = 0; i < crontab.getJobInfos().size(); i++) {
				JobInfo info = crontab.getJobInfos().get(i);
				writer.append(System.getProperty("java.home"));
				writer.append("/bin/java -cp JobWorker.jar:resources com.want.App ");
				writer.append(info.getPath());
				if (i < crontab.getJobInfos().size() - 1) {
					writer.append(" &&");
					writer.append(System.lineSeparator());
				}
			}
			writer.close();
			return file;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void deleteCronJobScriptFile(String cronJobName) {
		File file = new File(System.getProperty("user.dir") + File.separator + "cronjob-" + cronJobName + ".sh");
		file.delete();
	}
}
