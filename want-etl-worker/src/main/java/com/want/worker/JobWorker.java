/**
 * -------------------------------------------------------
 * @FileName：JobWorker.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.worker;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.want.util.StringUtil;
import com.want.worker.dto.JDBCServerDTO;
import com.want.worker.dto.JobDTO;
import com.want.worker.dto.JobStrategy;
import com.want.worker.dto.MailGroupDTO;
import com.want.worker.dto.RuleDTO;
import com.want.worker.dto.RuleType;
import com.want.worker.dto.TaskDTO;
import com.want.worker.dto.TaskType;
import com.want.worker.sync.model.ConnectionType;
import com.want.worker.sync.model.SyncJcoTask;
import com.want.worker.sync.model.SyncTask;
import com.want.worker.sync.util.JDBCUtil;
import com.want.worker.sync.util.SyncTaskUtil;
import com.want.worker.util.MailUtil;
import com.want.worker.util.MonitorUtil;

public class JobWorker {

	public final static Logger logger = Logger.getLogger(JobWorker.class);

	public static void run(JobDTO job) {
		int sid = MonitorUtil.getSid(job.getBatchLoggerServer());
		MonitorUtil.sendStartJob(job.getBatchLoggerServer(), job.getBatchFuncId(), sid);

		StringBuffer errMsg = new StringBuffer();

		boolean interrupted = interrupted(JobStrategy.fromText(job.getJobStrategy()));
		logger.info("任務異常策略:" + (interrupted ? "中斷" : "持續"));
		for (TaskDTO task : job.getTasks()) {
			try {
				switch (TaskType.fromText(task.getType())) {
				case SYNC:
					logger.info("任務開始(任務類型:" + task.getType() + ")");
					SyncTask syncTask = new SyncTask();
					syncTask.setfType(ConnectionType.fromText(task.getSourceServer().getType()));
					syncTask.setfUrl(task.getSourceServer().getUrl());
					syncTask.setfUser(task.getSourceServer().getUsername());
					syncTask.setfPwd(task.getSourceServer().getPassword());
					syncTask.setfQueryCommand(task.getStatement());
					syncTask.settType(ConnectionType.fromText(task.getTargetServer().getType()));
					syncTask.settUrl(task.getTargetServer().getUrl());
					syncTask.settUser(task.getTargetServer().getUsername());
					syncTask.settPwd(task.getTargetServer().getPassword());
					syncTask.settDb(task.getDatabase());
					syncTask.settTable(task.getTable());
					syncTask.setCoreSize(Integer.valueOf(task.getCoreSize()));
					logger.info("任務完成(同步結果：" + SyncTaskUtil.sync(syncTask) + ")");
					break;
				case TRUNCATE:
					task.setStatement("TRUNCATE TABLE " + task.getDatabase() + "." + JDBCUtil
							.formatName(ConnectionType.fromText(task.getTargetServer().getType()), task.getTable()));
					logger.info("任務開始(任務類型:" + task.getType() + ", 執行語法:" + task.getStatement() + ")");
					JDBCUtil.execute(ConnectionType.fromText(task.getTargetServer().getType()),
							task.getTargetServer().getUrl(), task.getTargetServer().getUsername(),
							task.getTargetServer().getPassword(), task.getStatement());
					logger.info("任務完成");
					break;
				case DELETE:
					task.setStatement("DELETE FROM " + task.getDatabase() + "." + JDBCUtil
							.formatName(ConnectionType.fromText(task.getTargetServer().getType()), task.getTable())
							+ " " + task.getCondition());
					logger.info("任務開始(任務類型:" + task.getType() + ", 執行語法:" + task.getStatement() + ")");
					int count = JDBCUtil.executeUpdate(ConnectionType.fromText(task.getTargetServer().getType()),
							task.getTargetServer().getUrl(), task.getTargetServer().getUsername(),
							task.getTargetServer().getPassword(), task.getStatement());
					logger.info("任務完成(刪除筆數:" + count + ")");
					break;
				case CALL:
					logger.info("任務開始(任務類型:" + task.getType() + ", 執行語法:" + task.getStatement() + ")");
					JDBCUtil.call(ConnectionType.fromText(task.getTargetServer().getType()),
							task.getTargetServer().getUrl(), task.getTargetServer().getUsername(),
							task.getTargetServer().getPassword(), task.getStatement());
					logger.info("任務完成");
					break;
				case CHECK:
					logger.info("任務開始(任務類型:" + task.getType() + ")");
					List<Map<String, Object>> result = JDBCUtil.query(
							ConnectionType.fromText(task.getTargetServer().getType()), task.getTargetServer().getUrl(),
							task.getTargetServer().getUsername(), task.getTargetServer().getPassword(),
							task.getStatement());
					boolean abnormal = false;
					for (Map<String, Object> map : result) {
						for (RuleDTO rule : task.getRules()) {
							switch (RuleType.fromText(rule.getType())) {
							case EQUALS:
								if (!map.get(rule.getField()).equals(rule.getValue())) {
									logger.info("檢查欄位" + rule.getField() + "數值異常.");
									abnormal = true;
								} else {
									logger.info("檢查欄位" + rule.getField() + "數值正常.");
								}
								break;
							}
						}
					}
					logger.info("任務完成");
					if (abnormal) {
						throw new Exception("檢查完成數值異常.");
					}
					break;
				case JCO:
					logger.info("任務開始(任務類型:" + task.getType() + ")");
					SyncJcoTask syncJcoTask = new SyncJcoTask();
					syncJcoTask.setjCOServer(task.getFunctionServer());
					syncJcoTask.setfTable(task.getFunction());
					syncJcoTask.setfMap(task.getImportForm());
					syncJcoTask.settType(ConnectionType.fromText(task.getTargetServer().getType()));
					syncJcoTask.settUrl(task.getTargetServer().getUrl());
					syncJcoTask.settUser(task.getTargetServer().getUsername());
					syncJcoTask.settPwd(task.getTargetServer().getPassword());
					syncJcoTask.settDb(task.getDatabase());
					syncJcoTask.settTable(task.getTable());
					syncJcoTask.setCoreSize(Integer.valueOf(task.getCoreSize()));
					logger.info("任務完成(同步結果：" + SyncTaskUtil.syncJco(syncJcoTask) + ")");
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
				sendMail(job, task, e);
				// 將執行排程中間錯誤訊息整合
				errMsg.append(e.getStackTrace().toString());
				errMsg.append("\n");
				if (interrupted) {
					break;
				}
			}
		}

		// 判斷執行排程中間是否有錯誤
		String finalErrMsg = errMsg.toString();
		if (StringUtil.isEmpty(finalErrMsg)) {
			MonitorUtil.sendSuccessJob(job.getBatchLoggerServer(), sid);
		} else {
			MonitorUtil.sendErrorJob(job.getBatchLoggerServer(), sid, finalErrMsg);
		}
	}

	private static void sendMail(JobDTO job, TaskDTO task, Exception exception) {
		try {
			MailGroupDTO mailGroup = job.getMailGroup();
			if (mailGroup == null) {
				logger.warn("未設定警示信件通知.");
				return;
			}
			String subject = "警告排程異常 - " + job.getJobName();
			String text = convertMailMessage(task, getErrorMessage(exception));
			MailUtil.send(mailGroup.getServerHost(), mailGroup.getServerUser(), mailGroup.getServerPassword(),
					mailGroup.getServerName(),
					mailGroup.getToMails().toArray(new String[mailGroup.getToMails().size()]),
					mailGroup.getCcMails().toArray(new String[mailGroup.getCcMails().size()]), subject, text, null,
					null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getErrorMessage(final Throwable throwable) {
		final StringWriter sw = new StringWriter();
		final PrintWriter pw = new PrintWriter(sw, true);
		throwable.printStackTrace(pw);
		return sw.getBuffer().toString();
	}

	private static String convertMailMessage(TaskDTO task, String error) {
		JDBCServerDTO source = task.getSourceServer();
		JDBCServerDTO target = task.getTargetServer();
		StringBuffer buffer = new StringBuffer();
		switch (TaskType.fromText(task.getType())) {
		case SYNC:
			buffer.append("<p>同步數據任務異常。</p>");
			buffer.append("<p>來源：</p>");
			buffer.append("<p>	" + source.getUrl() + "</p>");
			buffer.append("<p>	" + task.getStatement() + "</p>");
			buffer.append("<p>目的:</p>");
			buffer.append("<p>	" + target.getUrl() + "</p>");
			buffer.append("<p>	" + task.getDatabase() + "." + task.getTable() + "</p>");
			buffer.append("<p>錯誤訊息:</p>");
			buffer.append("<p>	" + error + "</p>");
			buffer.append("<p>資訊中心</p>");
			break;
		case TRUNCATE:
			buffer.append("<p>清除數據任務異常。</p>");
			buffer.append("<p>目的:</p>");
			buffer.append("<p>	" + target.getUrl() + "</p>");
			buffer.append("<p>	" + task.getDatabase() + "." + task.getTable() + "</p>");
			buffer.append("<p>	" + error + "</p>");
			buffer.append("<p>資訊中心</p>");
			break;
		case DELETE:
			buffer.append("<p>刪除數據任務異常。</p>");
			buffer.append("<p>目的:</p>");
			buffer.append("<p>	" + target.getUrl() + "</p>");
			buffer.append("<p>	" + task.getDatabase() + "." + task.getTable() + "</p>");
			buffer.append("<p>	" + task.getCondition() + "</p>");
			buffer.append("<p>錯誤訊息:</p>");
			buffer.append("<p>	" + error + "</p>");
			buffer.append("<p>資訊中心</p>");
			break;
		case CALL:
			buffer.append("<p>執行腳本任務異常。</p>");
			buffer.append("<p>目的:</p>");
			buffer.append("<p>	" + target.getUrl() + "</p>");
			buffer.append("<p>	" + task.getStatement() + "</p>");
			buffer.append("<p>錯誤訊息:</p>");
			buffer.append("<p>	" + error + "</p>");
			buffer.append("<p>資訊中心</p>");
			break;
		case CHECK:
			buffer.append("<p>檢查數值任務異常。</p>");
			buffer.append("<p>目的:</p>");
			buffer.append("<p>	" + target.getUrl() + "</p>");
			buffer.append("<p>	" + task.getStatement() + "</p>");
			buffer.append("<p>錯誤訊息:</p>");
			buffer.append("<p>	" + error + "</p>");
			buffer.append("<p>資訊中心</p>");
			break;
		case JCO:
			buffer.append("<p>同步數據任務異常。</p>");
			buffer.append("<p>來源：</p>");
			buffer.append("<p>	" + task.getFunctionServer().getAshost() + "</p>");
			buffer.append("<p>	" + task.getFunction() + "</p>");
			buffer.append("<p>	" + task.getImportForm() + "</p>");
			buffer.append("<p>目的:</p>");
			buffer.append("<p>	" + target.getUrl() + "</p>");
			buffer.append("<p>	" + task.getDatabase() + "." + task.getTable() + "</p>");
			buffer.append("<p>錯誤訊息:</p>");
			buffer.append("<p>	" + error + "</p>");
			buffer.append("<p>資訊中心</p>");
			break;
		}
		return buffer.toString();
	}

	private static boolean interrupted(JobStrategy jobStrategy) {
		return jobStrategy == JobStrategy.INTERRUPTED;
	}

}
