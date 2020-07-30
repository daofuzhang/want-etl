/**
 * -------------------------------------------------------
 * @FileName：JDBCServer.java
 * @Description：简要描述本文件的内容
 * @Author：Jerry.Chen
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.domain.server;

/**
 * @author 80005499
 *
 */
public class JDBCServer {
	private String id; // 編號
	private String name; // 名稱
	private String type; // db類型
	private String url; // 位置
	private String userName; // 帳號
	private String password; // 密碼

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}