/**
 * -------------------------------------------------------
 * @FileName：RuleType.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.worker.dto;

public enum RuleType {
	EQUALS("eq");

	final String text;

	RuleType(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public static RuleType fromText(String text) {
		for (RuleType r : RuleType.values()) {
			if (r.text.equals(text)) {
				return r;
			}
		}
		return null;
	}
}


