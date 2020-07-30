/**
 * -------------------------------------------------------
 * @FileName：ReportDTO.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.controller.service.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "工作報表")
public class JobReportDTO {
	@ApiModelProperty(value = "工作編號")
	private String jobId;
	@ApiModelProperty(value = "工作名稱")
	private String name;
	@ApiModelProperty(value = "工作描述")
	private String description;
	@ApiModelProperty(value = "工作記錄編號")
	private String jobLogId;
	@ApiModelProperty(value = "開始時間")
	private String startTime;
	@ApiModelProperty(value = "結束時間")
	private String endTime;
	@ApiModelProperty(value = "花費時間")
	private String duration;
	@ApiModelProperty(value = "狀態")
	private String status;
	@ApiModelProperty(value = "工作紀錄")
	private List<JobReportLogDTO> recentLogs;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getJobLogId() {
		return jobLogId;
	}

	public void setJobLogId(String jobLogId) {
		this.jobLogId = jobLogId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<JobReportLogDTO> getRecentLogs() {
		return recentLogs;
	}

	public void setRecentLogs(List<JobReportLogDTO> recentLogs) {
		this.recentLogs = recentLogs;
	}

}
