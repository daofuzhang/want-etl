/**
 * -------------------------------------------------------
 * @FileName：Job.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.modal;

import java.util.List;

public class Job {

	public static final String ERROR_STRATEGY_CONTINUNES = "continues";
	public static final String ERROR_STRATEGY_INTERRUPTED = "interrupted";

	private Integer version;
	private String jobName;
	private String errorStrategy;
	private String mailServer;
	private String mailGroup;
	private String monitorName;
	private List<Task> tasks;

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getErrorStrategy() {
		return errorStrategy;
	}

	public void setErrorStrategy(String errorStrategy) {
		this.errorStrategy = errorStrategy;
	}

	public String getMailServer() {
		return mailServer;
	}

	public void setMailServer(String mailServer) {
		this.mailServer = mailServer;
	}

	public String getMailGroup() {
		return mailGroup;
	}

	public void setMailGroup(String mailGroup) {
		this.mailGroup = mailGroup;
	}

	public String getMonitorName() {
		return monitorName;
	}

	public void setMonitorName(String monitorName) {
		this.monitorName = monitorName;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

}
