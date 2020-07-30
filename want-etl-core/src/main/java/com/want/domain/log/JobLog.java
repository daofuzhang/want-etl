/**
 * -------------------------------------------------------
 * @FileName：Log.java
 * @Description：简要描述本文件的内容
 * @Author：Jerry.Chen
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.domain.log;

import java.sql.Timestamp;

/**
 * @author 80005499
 *
 */
public class JobLog {

	public enum Status {

		WAIT(0, "wait"), RUNNING(1, "running"), DONE(2, "done"), INTERRUPT(4, "interrupt"), ERROR(5, "error");

		final int code;

		final String text;

		Status(int code, String text) {
			this.code = code;
			this.text = text;
		}

		public int getCode() {
			return code;
		}

		public String getText() {
			return text;
		}

		public static Status fromCode(int code) {
			for (Status s : Status.values()) {
				if (s.code == code) {
					return s;
				}
			}
			return null;
		}

		public static Status fromText(String text) {
			for (Status s : Status.values()) {
				if (s.text.equals(text)) {
					return s;
				}
			}
			return null;
		}

	}

	private String id; // 編號
	private String jobId; // 所屬工作編號
	private String agentId; // 代理執行者編號
	private String pid;
	private String parameter; // 執行參數(Json格式)
	private String status;
	private Timestamp startTime; // 開始時間
	private Timestamp endTime; // 結束時間
	private Timestamp createTime; // 創建時間
	private Timestamp updateTime; // 更新時間

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

}