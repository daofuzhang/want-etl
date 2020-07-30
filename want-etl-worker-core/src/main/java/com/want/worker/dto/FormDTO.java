/**
 * -------------------------------------------------------
 * @FileName：Form.java
 * @Description：简要描述本文件的内容
 * @Author：Dirk.Lee
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.worker.dto;

import java.util.List;

/**
 * @author 80005151
 *
 */
public class FormDTO {
	private String key;
	private String val;
	private List<FormDTO> value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public List<FormDTO> getValue() {
		return value;
	}

	public void setValue(List<FormDTO> value) {
		this.value = value;
	}

}
