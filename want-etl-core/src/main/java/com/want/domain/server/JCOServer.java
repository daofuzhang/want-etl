/**
 * -------------------------------------------------------
 * @FileName：JCOServer.java
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
public class JCOServer {
	private String id; // 編號
	private String name; // 名稱
	private String lang; // 語系
	private String passwd; // 密碼
	private String sysnr; // 系統編號
	private String client; // 客戶端
	private String poolCapacity; // 最大空閑連接數
	private String user; // 使用者
	private String peakLimit; // 最大連接數
	private String ashost; // 位置

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

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
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

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
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