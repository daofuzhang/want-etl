/**
 * -------------------------------------------------------
 * @FileName：SaveUserService.java
 * @Description：简要描述本文件的内容
 * @Author：Dirk.Lee
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.controller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.want.base.service.ResponseDataService;
import com.want.base.service.dto.InputArgumentDTO;
import com.want.base.service.exception.ResponseExceptionFactory;
import com.want.controller.service.SaveUserService.InnerUser;
import com.want.controller.service.dto.SaveUserInputDTO;
import com.want.domain.account.Account;
import com.want.service.setting.SettingService;
import com.want.util.StringUtil;

/**
 * @author 80005151
 *
 */
@Service
public class SaveUserService extends ResponseDataService<SaveUserInputDTO, InnerUser, Boolean> {
	@Autowired
	SettingService settingService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.want.base.service.BaseService#checkParameters(java.lang.Object)
	 */
	@Override
	protected void checkParameters(SaveUserInputDTO parameters) throws Exception {
		if (parameters == null) {
			throw ResponseExceptionFactory.createParameterEmpty("Input不能為空值.");
		}

		if (StringUtil.isEmpty(parameters.getId())) {
			throw ResponseExceptionFactory.createParameterEmpty("員工編號不能為空.");
		}

		if (StringUtil.isEmpty(parameters.getName())) {
			throw ResponseExceptionFactory.createParameterEmpty("姓名不能為空.");
		}

		if (StringUtil.isEmpty(parameters.getMail())) {
			throw ResponseExceptionFactory.createParameterEmpty("mail不能為空.");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.want.base.service.BaseService#parseParameters(com.want.base.service.dto.
	 * InputArgumentDTO, java.lang.Object)
	 */
	@Override
	protected InnerUser parseParameters(InputArgumentDTO argument, SaveUserInputDTO parameters) throws Exception {
		InnerUser innerUser = new InnerUser();
		Account account = new Account();
		account.setId(parameters.getId());
		account.setName(parameters.getName());
		account.setPassword("");
		account.setRoleId("");
//		account.setEnable(parameters.getEnable() == null || parameters.getEnable().isEmpty() ? "0" : "1");
		account.setEnable("1");
		account.setMailId("");

		innerUser.setAccount(account);
		innerUser.setMail(parameters.getMail());

		return innerUser;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.want.base.service.BaseService#dataAccess(com.want.base.service.dto.
	 * InputArgumentDTO, java.lang.Object)
	 */
	@Override
	protected Boolean dataAccess(InputArgumentDTO argument, InnerUser parameters) throws Exception {
		settingService.saveAccount(parameters.getAccount(), parameters.getMail());
		return true;
	}

	class InnerUser {
		Account account;
		String mail;

		public Account getAccount() {
			return account;
		}

		public void setAccount(Account account) {
			this.account = account;
		}

		public String getMail() {
			return mail;
		}

		public void setMail(String mail) {
			this.mail = mail;
		}

	}

}
