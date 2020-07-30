/**
 * -------------------------------------------------------
 * @FileName：MailGroup.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.worker.dto;

import java.util.List;

public class MailGroupDTO {
	private String serverName;
	private String serverHost;
	private String serverUser;
	private String serverPassword;
	private List<String> toMails;
	private List<String> ccMails;

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getServerHost() {
		return serverHost;
	}

	public void setServerHost(String serverHost) {
		this.serverHost = serverHost;
	}

	public String getServerUser() {
		return serverUser;
	}

	public void setServerUser(String serverUser) {
		this.serverUser = serverUser;
	}

	public String getServerPassword() {
		return serverPassword;
	}

	public void setServerPassword(String serverPassword) {
		this.serverPassword = serverPassword;
	}

	public List<String> getToMails() {
		return toMails;
	}

	public void setToMails(List<String> toMails) {
		this.toMails = toMails;
	}

	public List<String> getCcMails() {
		return ccMails;
	}

	public void setCcMails(List<String> ccMails) {
		this.ccMails = ccMails;
	}

}
