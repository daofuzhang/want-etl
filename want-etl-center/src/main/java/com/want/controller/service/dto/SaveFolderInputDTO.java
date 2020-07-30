/**
 * -------------------------------------------------------
 * @FileName：AddFolderInputVO.java
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
public class SaveFolderInputDTO {
	@ApiModelProperty(value = "群組編號")
	private String groupId;
	@ApiModelProperty(value = "中層編號")
	private String folderId;
	@ApiModelProperty(value = "名稱")
	private String name;
	@ApiModelProperty(value = "說明")
	private String memo;
	@ApiModelProperty(value = "寄件群")
	private List<String> mailGroupIds;

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
}
