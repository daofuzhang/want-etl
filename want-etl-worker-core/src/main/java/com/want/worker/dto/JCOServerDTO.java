/**
 * -------------------------------------------------------
 * @FileName：JCOServer.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.worker.dto;

public class JCOServerDTO {
	private String destinationName;
	private String lang;
	private String passwd;
	private String sysnr;
	private String client;
	private String poolCapacity;
	private String user;
	private String peakLimit;
	private String ashost;

	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
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
