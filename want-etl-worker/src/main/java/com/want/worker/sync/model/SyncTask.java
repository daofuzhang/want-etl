/**
 * -------------------------------------------------------
 * @FileName：SyncTask.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.worker.sync.model;

public class SyncTask {

	private ConnectionType fType;
	private String fUrl;
	private String fUser;
	private String fPwd;
	private String fQueryCommand;
	private ConnectionType tType;
	private String tUrl;
	private String tUser;
	private String tPwd;
	private String tDb;
	private String tTable;
	private int coreSize;

	public ConnectionType getfType() {
		return fType;
	}

	public void setfType(ConnectionType fType) {
		this.fType = fType;
	}

	public String getfUrl() {
		return fUrl;
	}

	public void setfUrl(String fUrl) {
		this.fUrl = fUrl;
	}

	public String getfUser() {
		return fUser;
	}

	public void setfUser(String fUser) {
		this.fUser = fUser;
	}

	public String getfPwd() {
		return fPwd;
	}

	public void setfPwd(String fPwd) {
		this.fPwd = fPwd;
	}

	public String getfQueryCommand() {
		return fQueryCommand;
	}

	public void setfQueryCommand(String fQueryCommand) {
		this.fQueryCommand = fQueryCommand;
	}

	public ConnectionType gettType() {
		return tType;
	}

	public void settType(ConnectionType tType) {
		this.tType = tType;
	}

	public String gettUrl() {
		return tUrl;
	}

	public void settUrl(String tUrl) {
		this.tUrl = tUrl;
	}

	public String gettUser() {
		return tUser;
	}

	public void settUser(String tUser) {
		this.tUser = tUser;
	}

	public String gettPwd() {
		return tPwd;
	}

	public void settPwd(String tPwd) {
		this.tPwd = tPwd;
	}

	public String gettDb() {
		return tDb;
	}

	public void settDb(String tDb) {
		this.tDb = tDb;
	}

	public String gettTable() {
		return tTable;
	}

	public void settTable(String tTable) {
		this.tTable = tTable;
	}

	public int getCoreSize() {
		return coreSize;
	}

	public void setCoreSize(int coreSize) {
		this.coreSize = coreSize;
	}

}
