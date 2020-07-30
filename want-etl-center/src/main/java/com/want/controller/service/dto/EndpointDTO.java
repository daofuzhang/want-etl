/**
 * -------------------------------------------------------
 * @FileName：EndpointDTO.java
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
public class EndpointDTO {
	@ApiModelProperty(value = "編號")
	private String id;
	@ApiModelProperty(value = "名稱")
	private String name;
	@ApiModelProperty(value = "連線類型")
	private String type;
	@ApiModelProperty(value = "連線位置")
	private String url;
	@ApiModelProperty(value = "使用者")
	private String user;
	@ApiModelProperty(value = "密碼")
	private String passwd;
	@ApiModelProperty(value = "語系")
	private String lang;
	@ApiModelProperty(value = "系統編號")
	private String sysnr;
	@ApiModelProperty(value = "客戶端")
	private String client;
	@ApiModelProperty(value = "最大空閑連接數")
	private String poolCapacity;
	@ApiModelProperty(value = "最大連接數")
	private String peakLimit;
	@ApiModelProperty(value = "位置")
	private String ashost;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getSysnr() {
		return sysnr;
	}

	public void setSysnr(String sysnr) {
		this.sysnr = sysnr;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getPoolCapacity() {
		return poolCapacity;
	}

	public void setPoolCapacity(String poolCapacity) {
		this.poolCapacity = poolCapacity;
	}

	public String getPeakLimit() {
		return peakLimit;
	}

	public void setPeakLimit(String peakLimit) {
		this.peakLimit = peakLimit;
	}

	public String getAshost() {
		return ashost;
	}

	public void setAshost(String ashost) {
		this.ashost = ashost;
	}

}
