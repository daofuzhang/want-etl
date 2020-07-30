/**
 * -------------------------------------------------------
 * @FileName：GetOracleServersDTO.java
 * @Description：简要描述本文件的内容
 * @Author：Dirk.Lee
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.controller.service.dto;

/**
 * @author 80005151
 *
 */
public class GetOracleServersDTO {
	private String id;
	private String name;
	private String type;
	private String url;
	private boolean batchLogger;

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

	public boolean isBatchLogger() {
		return batchLogger;
	}

	public void setBatchLogger(boolean batchLogger) {
		this.batchLogger = batchLogger;
	}

}
