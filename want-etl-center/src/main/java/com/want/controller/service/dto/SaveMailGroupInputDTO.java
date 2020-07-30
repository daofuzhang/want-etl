/**
 * -------------------------------------------------------
 * @FileName：SaveMailGroupInputDTO.java
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
public class SaveMailGroupInputDTO {
	@ApiModelProperty(value = "編號")
	private String id;
	@ApiModelProperty(value = "名稱")
	private String name;
	@ApiModelProperty(value = "說明")
	private String description;
	@ApiModelProperty(value = "通知人員編號")
	private List<String> mailIds;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getMailIds() {
		return mailIds;
	}

	public void setMailIds(List<String> mailIds) {
		this.mailIds = mailIds;
	}
	
	

}
