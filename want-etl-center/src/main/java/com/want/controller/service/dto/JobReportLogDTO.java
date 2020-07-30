/**
 * -------------------------------------------------------
 * @FileName：JobLogDTO.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.controller.service.dto;

import java.util.List;

import com.want.worker.dto.JobDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "工作紀錄")
public class JobReportLogDTO {
	@ApiModelProperty(value = "工作紀錄編號")
	private String jobLogId;
	@ApiModelProperty(value = "執行代理編號")
	private String agentId;
	@ApiModelProperty(value = "開始時間")
	private String startTime;
	@ApiModelProperty(value = "結束時間")
	private String endTime;
	@ApiModelProperty(value = "狀態")
	private String status;
	@ApiModelProperty(value = "執行參數")
	private JobDTO parameter;
	@ApiModelProperty(value = "執行紀錄訊息")
	private List<JobReportLogMessageDTO> message;

	public String getJobLogId() {
		return jobLogId;
	}

	public void setJobLogId(String jobLogId) {
		this.jobLogId = jobLogId;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public JobDTO getParameter() {
		return parameter;
	}

	public void setParameter(JobDTO parameter) {
		this.parameter = parameter;
	}

	public List<JobReportLogMessageDTO> getMessage() {
		return message;
	}

	public void setMessage(List<JobReportLogMessageDTO> message) {
		this.message = message;
	}

}
