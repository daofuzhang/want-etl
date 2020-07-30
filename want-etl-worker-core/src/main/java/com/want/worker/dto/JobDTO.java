/**
 * -------------------------------------------------------
 * @FileName：Job.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.worker.dto;

import java.util.List;

public class JobDTO {
	private String jobName;
	private String jobStrategy;
	private String batchFuncId;
	private MailGroupDTO mailGroup;
	private List<TaskDTO> tasks;
	private JDBCServerDTO batchLoggerServer;
	private String dynamics;

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobStrategy() {
		return jobStrategy;
	}

	public void setJobStrategy(String jobStrategy) {
		this.jobStrategy = jobStrategy;
	}

	public String getBatchFuncId() {
		return batchFuncId;
	}

	public void setBatchFuncId(String batchFuncId) {
		this.batchFuncId = batchFuncId;
	}

	public MailGroupDTO getMailGroup() {
		return mailGroup;
	}

	public void setMailGroup(MailGroupDTO mailGroup) {
		this.mailGroup = mailGroup;
	}

	public List<TaskDTO> getTasks() {
		return tasks;
	}

	public void setTasks(List<TaskDTO> tasks) {
		this.tasks = tasks;
	}

	public JDBCServerDTO getBatchLoggerServer() {
		return batchLoggerServer;
	}

	public void setBatchLoggerServer(JDBCServerDTO batchLoggerServer) {
		this.batchLoggerServer = batchLoggerServer;
	}

	public String getDynamics() {
		return dynamics;
	}

	public void setDynamics(String dynamics) {
		this.dynamics = dynamics;
	}

}
