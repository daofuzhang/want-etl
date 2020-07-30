/**
 * -------------------------------------------------------
 * @FileName：ConnectionType.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.worker.sync.model;

public enum ConnectionType {
	HANA("hana"), MYSQL("mysql"), ORACLE("oracle");

	final String text;

	ConnectionType(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public static ConnectionType fromText(String text) {
		for (ConnectionType c : ConnectionType.values()) {
			if (c.text.equals(text)) {
				return c;
			}
		}
		return null;
	}
}
