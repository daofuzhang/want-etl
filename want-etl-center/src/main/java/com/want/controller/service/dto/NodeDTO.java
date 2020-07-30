/**
 * -------------------------------------------------------
 * @FileName：NodeDTO.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.controller.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "工作節點")
public class NodeDTO {
	public enum Status {
		sunny, rain
	}
	@ApiModelProperty(value = "編號")
	private String id;
	@ApiModelProperty(value = "名稱")
	private String name;
	@ApiModelProperty(value = "位置")
	private String address;
	@ApiModelProperty(value = "當前執行數")
	private Integer active;
	@ApiModelProperty(value = "最大執行數")
	private Integer maxActive;
	@ApiModelProperty(value = "狀態")
	private Status status;

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}

	public Integer getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(Integer maxActive) {
		this.maxActive = maxActive;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
