/**
 * -------------------------------------------------------
 * @FileName：ReportInputDTO.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.controller.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "工作報表參數")
public class JobReportInputDTO {


	@ApiModelProperty(value = "關鍵字", position = 1)
	private String keyword;
	@ApiModelProperty(value = "開始時間", position = 2, example = "2020-01-01 00:00:00")
	private String startTime;
	@ApiModelProperty(value = "結束時間", position = 3, example = "2020-12-31 23:59:59")
	private String endTime;
	@ApiModelProperty(value = "群組編號", position = 4)
	private String groupId;
	@ApiModelProperty(value = "資料夾編號", position = 5)
	private String folderId;
	@ApiModelProperty(value = "工作狀態(wait, running, done, interrupt, error)", position = 6)
	private String status;
	@ApiModelProperty(value = "頁碼", position = 7, example = "1")
	private Integer pageIndx;
	@ApiModelProperty(value = "單頁筆數", position = 8, example = "20")
	private Integer pageSize;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getPageIndx() {
		return pageIndx;
	}

	public void setPageIndx(Integer pageIndx) {
		this.pageIndx = pageIndx;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}
