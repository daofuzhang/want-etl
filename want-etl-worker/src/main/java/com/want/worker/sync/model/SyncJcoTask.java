/**
 * -------------------------------------------------------
 * @FileName：SyncJcoTask.java
 * @Description：简要描述本文件的内容
 * @Author：Dirk.Lee
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.worker.sync.model;

import java.util.List;

import com.want.worker.dto.FormDTO;
import com.want.worker.dto.JCOServerDTO;

/**
 * @author 80005151
 *
 */
public class SyncJcoTask {
	private JCOServerDTO jCOServer;
	private String fTable;
	private List<FormDTO> fMap;
	private ConnectionType tType;
	private String tUrl;
	private String tUser;
	private String tPwd;
	private String tDb;
	private String tTable;
	private int coreSize;

	public JCOServerDTO getjCOServer() {
		return jCOServer;
	}

	public void setjCOServer(JCOServerDTO jCOServer) {
		this.jCOServer = jCOServer;
	}

	public String getfTable() {
		return fTable;
	}

	public void setfTable(String fTable) {
		this.fTable = fTable;
	}

	public List<FormDTO> getfMap() {
		return fMap;
	}

	public void setfMap(List<FormDTO> fMap) {
		this.fMap = fMap;
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
