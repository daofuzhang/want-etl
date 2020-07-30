/**
 * -------------------------------------------------------
 * @FileName：Message.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.graylog.to;

import com.google.gson.annotations.SerializedName;

public class Message {
	@SerializedName("pid")
	private String pid;
	@SerializedName("gl2_remote_ip")
	private String gl2RemoteIp;
	@SerializedName("source")
	private String source;
	@SerializedName("timestampMs")
	private Long timestampMs;
	@SerializedName("job-version")
	private String jobVersion;
	@SerializedName("job-name")
	private String jobName;
	@SerializedName("job-task-count")
	private String jobTaskCount;
	@SerializedName("task-id")
	private String taskId;
	@SerializedName("status")
	private Integer status;
	@SerializedName("full_message")
	private String fullMessage;

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getGl2RemoteIp() {
		return gl2RemoteIp;
	}

	public void setGl2RemoteIp(String gl2RemoteIp) {
		this.gl2RemoteIp = gl2RemoteIp;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Long getTimestampMs() {
		return timestampMs;
	}

	public void setTimestampMs(Long timestampMs) {
		this.timestampMs = timestampMs;
	}

	public String getJobVersion() {
		return jobVersion;
	}

	public void setJobVersion(String jobVersion) {
		this.jobVersion = jobVersion;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobTaskCount() {
		return jobTaskCount;
	}

	public void setJobTaskCount(String jobTaskCount) {
		this.jobTaskCount = jobTaskCount;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getFullMessage() {
		return fullMessage;
	}

	public void setFullMessage(String fullMessage) {
		this.fullMessage = fullMessage;
	}

}
