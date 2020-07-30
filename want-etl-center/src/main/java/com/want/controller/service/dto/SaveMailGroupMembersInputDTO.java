/**
 * -------------------------------------------------------
 * @FileName：SaveMailGroupMembersInputDTO.java
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
public class SaveMailGroupMembersInputDTO {
	@ApiModelProperty(value = "編號")
	private String id;
	@ApiModelProperty(value = "信箱列表")
	private List<String> mails;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<String> getMails() {
		return mails;
	}

	public void setMails(List<String> mails) {
		this.mails = mails;
	}

}
