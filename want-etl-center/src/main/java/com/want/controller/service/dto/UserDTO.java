/**
 * -------------------------------------------------------
 * @FileName：AccountDTO.java
 * @Description：简要描述本文件的内容
 * @Author：Dirk.Lee
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.controller.service.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author 80005151
 *
 */
public class UserDTO {
	@ApiModelProperty(value = "員工編號")
	private String id;
	@ApiModelProperty(value = "名稱")
	private String name;
	@ApiModelProperty(value = "")
	private String roleId;
	@ApiModelProperty(value = "")
	private String enable;
	@ApiModelProperty(value = "")
	private String mail;

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

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

}
