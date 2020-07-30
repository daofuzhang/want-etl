/**
 * -------------------------------------------------------
 * @FileName：Crontab.java
 * @Description：简要描述本文件的内容
 * @Author：Dirk.Lee
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.modal;

import java.util.List;

/**
 * @author 80005151
 *
 */
public class CronJob {

	private String name;
	private String minutes;
	private String hours;
	private String day;
	private String month;
	private String dow;
	private List<JobInfo> jobInfos;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMinutes() {
		return minutes;
	}

	public void setMinutes(String minutes) {
		this.minutes = minutes;
	}

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDow() {
		return dow;
	}

	public void setDow(String dow) {
		this.dow = dow;
	}

	public List<JobInfo> getJobInfos() {
		return jobInfos;
	}

	public void setJobInfos(List<JobInfo> jobInfos) {
		this.jobInfos = jobInfos;
	}

}
