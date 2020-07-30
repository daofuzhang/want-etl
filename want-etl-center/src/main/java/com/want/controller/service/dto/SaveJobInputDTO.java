/**
 * -------------------------------------------------------
 * @FileName：AddJobInputVO.java
 * @Description：简要描述本文件的内容
 * @Author：Dirk.Lee
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.controller.service.dto;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author 80005151
 *
 */
public class SaveJobInputDTO {
	@ApiModelProperty(value = "群組編號")
	private String groupId;
	@ApiModelProperty(value = "中層編號")
	private String folderId;
	@ApiModelProperty(value = "工作編號")
	private String jobId;
	@ApiModelProperty(value = "名稱")
	private String name;
	@ApiModelProperty(value = "策略")
	private String strategy;
	@ApiModelProperty(value = "說明")
	private String memo;
	@ApiModelProperty(value = "batch logger 名稱")
	private String batchFuncId;
	@ApiModelProperty(value = "是否傳送batch logger")
	private boolean sendBatchLogger;
	@ApiModelProperty(value = "排程格式")
	private List<String> expressions;
	@ApiModelProperty(value = "寄件群")
	private List<String> mailGroupIds;
	@ApiModelProperty(value = "任務列表")
	private List<TaskDTO> tasks;
	@ApiModelProperty(value = "動態變數")
	private List<DynamicDTO> dynamics;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getFolderId() {
		return folderId;
	}

	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getBatchFuncId() {
		return batchFuncId;
	}

	public void setBatchFuncId(String batchFuncId) {
		this.batchFuncId = batchFuncId;
	}

	public boolean isSendBatchLogger() {
		return sendBatchLogger;
	}

	public void setSendBatchLogger(boolean sendBatchLogger) {
		this.sendBatchLogger = sendBatchLogger;
	}

	public List<String> getExpressions() {
		return expressions;
	}

	public void setExpressions(List<String> expressions) {
		this.expressions = expressions;
	}

	public List<String> getMailGroupIds() {
		return mailGroupIds;
	}

	public void setMailGroupIds(List<String> mailGroupIds) {
		this.mailGroupIds = mailGroupIds;
	}

	public List<TaskDTO> getTasks() {
		return tasks;
	}

	public void setTasks(List<TaskDTO> tasks) {
		this.tasks = tasks;
	}

	public List<DynamicDTO> getDynamics() {
		return dynamics;
	}

	public void setDynamics(List<DynamicDTO> dynamics) {
		this.dynamics = dynamics;
	}

}
