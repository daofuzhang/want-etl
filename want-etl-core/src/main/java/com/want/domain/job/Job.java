/**
 * -------------------------------------------------------
 * @FileName：Job.java
 * @Description：简要描述本文件的内容
 * @Author：Dirk.Lee
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.domain.job;

/**
 * @author 80005151
 *
 */
public class Job {
	private String id;
	private String name;
	private String strategy;
	private String mailGroupIds;
	private String memo;
	private String dynamics;
	private String batchFuncId;
	private int sendBatchLogger;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getMailGroupIds() {
		return mailGroupIds;
	}

	public void setMailGroupIds(String mailGroupIds) {
		this.mailGroupIds = mailGroupIds;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getDynamics() {
		return dynamics;
	}

	public void setDynamics(String dynamics) {
		this.dynamics = dynamics;
	}

	public String getBatchFuncId() {
		return batchFuncId;
	}

	public void setBatchFuncId(String batchFuncId) {
		this.batchFuncId = batchFuncId;
	}

	public int getSendBatchLogger() {
		return sendBatchLogger;
	}

	public void setSendBatchLogger(int sendBatchLogger) {
		this.sendBatchLogger = sendBatchLogger;
	}

}
