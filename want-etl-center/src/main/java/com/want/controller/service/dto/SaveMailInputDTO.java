/**
 * -------------------------------------------------------
 * @FileName：SaveMailInputDTO.java
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
public class SaveMailInputDTO {
	@ApiModelProperty(value = "編號")
	private String id;
	@ApiModelProperty(value = "名稱")
	private String name;
	@ApiModelProperty(value = "信箱")
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

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

}
