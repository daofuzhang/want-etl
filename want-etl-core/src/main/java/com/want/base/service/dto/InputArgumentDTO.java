package com.want.base.service.dto;

public class InputArgumentDTO {
	private String deviceId;
	private String remoteAddr;
	private AccountInfoDTO accountInfo;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getRemoteAddr() {
		return remoteAddr;
	}

	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

	public AccountInfoDTO getAccountInfo() {
		return accountInfo;
	}

	public void setAccountInfo(AccountInfoDTO accountInfo) {
		this.accountInfo = accountInfo;
	}

}
