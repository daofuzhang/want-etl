/**
 * -------------------------------------------------------
 * @FileName：ServerDefine.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.modal;

import java.util.List;

public class Mail {

	private List<MailServerDefine> mailServerDefine;
	private List<MailGroup> mailGroup;

	public List<MailServerDefine> getMailServerDefine() {
		return mailServerDefine;
	}

	public void setMailServerDefine(List<MailServerDefine> mailServerDefine) {
		this.mailServerDefine = mailServerDefine;
	}

	public List<MailGroup> getMailGroup() {
		return mailGroup;
	}

	public void setMailGroup(List<MailGroup> mailGroup) {
		this.mailGroup = mailGroup;
	}
	
	

}
