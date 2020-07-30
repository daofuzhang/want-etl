/**
 * -------------------------------------------------------
 * @FileName：MailGroup.java
 * @Description：简要描述本文件的内容
 * @Author：Dirk.Lee
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.domain.mail;

/**
 * @author 80005151
 *
 */
public class MailGroup {
	private String id;
	private String name;
	private String description;
	private String mailServerId;
	private String toMailIds;
	private String ccMailIds;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMailServerId() {
		return mailServerId;
	}

	public void setMailServerId(String mailServerId) {
		this.mailServerId = mailServerId;
	}

	public String getToMailIds() {
		return toMailIds;
	}

	public void setToMailIds(String toMailIds) {
		this.toMailIds = toMailIds;
	}

	public String getCcMailIds() {
		return ccMailIds;
	}

	public void setCcMailIds(String ccMailIds) {
		this.ccMailIds = ccMailIds;
	}

}
