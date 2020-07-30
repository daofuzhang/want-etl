/**
 * -------------------------------------------------------
 * @FileName：Column.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.worker.sync.model;

public class Column {
	private String name;
	private Integer dataType;
	private Integer keySeq;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public Integer getKeySeq() {
		return keySeq;
	}

	public void setKeySeq(Integer keySeq) {
		this.keySeq = keySeq;
	}

}
