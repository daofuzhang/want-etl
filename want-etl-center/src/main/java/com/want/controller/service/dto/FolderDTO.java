/**
 * -------------------------------------------------------
 * @FileName：FolderVO.java
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
public class FolderDTO {
	@ApiModelProperty(value = "編號")
	private String id;
	@ApiModelProperty(value = "名稱")
	private String name;
	@ApiModelProperty(value = "說明")
	private String memo;
	@ApiModelProperty(value = "寄件群")
	private List<String> mailGroupIds;
	@ApiModelProperty(value = "工作列表")
	private List<JobDTO> jobs;

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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public List<String> getMailGroupIds() {
		return mailGroupIds;
	}

	public void setMailGroupIds(List<String> mailGroupIds) {
		this.mailGroupIds = mailGroupIds;
	}

	public List<JobDTO> getJobs() {
		return jobs;
	}

	public void setJobs(List<JobDTO> jobs) {
		this.jobs = jobs;
	}

}
