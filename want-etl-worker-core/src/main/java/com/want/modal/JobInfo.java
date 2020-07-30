/**
 * -------------------------------------------------------
 * @FileName：JobInfo.java
 * @Description：简要描述本文件的内容
 * @Author：Dirk.Lee
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.modal;

/**
 * @author 80005151
 *
 */
public class JobInfo {
	private String jobName;
	private String path;
	private Integer poistion;

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getPoistion() {
		return poistion;
	}

	public void setPoistion(Integer poistion) {
		this.poistion = poistion;
	}

}
