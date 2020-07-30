/**
 * -------------------------------------------------------
 * @FileName：TaskType.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.worker.dto;

public enum TaskType {
	SYNC("sync"), TRUNCATE("truncate"), DELETE("delete"), CALL("call"), CHECK("check"), JCO("jco");

	final String text;

	TaskType(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public static TaskType fromText(String text) {
		for (TaskType t : TaskType.values()) {
			if (t.text.equals(text)) {
				return t;
			}
		}
		return null;
	}
}
