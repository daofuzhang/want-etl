/**
 * -------------------------------------------------------
 * @FileName：TYPE.java
 * @Description：简要描述本文件的内容
 * @Author：Dirk.Lee
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.domain.setting;

/**
 * @author 80005151
 *
 */
public enum DataBaseType {
	MYSQL("Database - MySQL", "mysql"), ORACLE("Database - Oracle", "oracle"), HANA("Database - HANA",
			"hana"), JCO("SAP - JCO", "jco");

	DataBaseType(String name, String type) {
		this.name = name;
		this.type = type;
	}

	private String name;

	private String type;

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

}
