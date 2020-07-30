/**
 * -------------------------------------------------------
 * @FileName：Folder.java
 * @Description：简要描述本文件的内容
 * @Author：Dirk.Lee
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.domain.job;

/**
 * @author 80005151
 *
 */
public class Folder {
	private String id;
	private String name;
	private String mailGroupIds;
	private String memo;

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

	public String getMailGroupIds() {
		return mailGroupIds;
	}

	public void setMailGroupIds(String mailGroupIds) {
		this.mailGroupIds = mailGroupIds;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}
