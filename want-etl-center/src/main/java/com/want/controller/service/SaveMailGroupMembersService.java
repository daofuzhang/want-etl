/**
 * -------------------------------------------------------
 * @FileName：SaveMailGroupMembersService.java
 * @Description：简要描述本文件的内容
 * @Author：Dirk.Lee
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.controller.service;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.want.base.service.ResponseDataService;
import com.want.base.service.dto.InputArgumentDTO;
import com.want.base.service.exception.ResponseExceptionFactory;
import com.want.controller.service.dto.SaveMailGroupMembersInputDTO;
import com.want.domain.mail.MailGroup;
import com.want.service.setting.SettingService;
import com.want.util.StringUtil;

/**
 * @author 80005151
 *
 */
@Service
public class SaveMailGroupMembersService extends ResponseDataService<SaveMailGroupMembersInputDTO, MailGroup, Boolean> {

	@Autowired
	private SettingService settingService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.want.base.service.BaseService#checkParameters(java.lang.Object)
	 */
	@Override
	protected void checkParameters(SaveMailGroupMembersInputDTO parameters) throws Exception {
		if (parameters == null) {
			throw ResponseExceptionFactory.createParameterEmpty("Input不能為空值.");
		}
		if (parameters.getId() == null || StringUtil.isEmpty(parameters.getId())) {
			throw ResponseExceptionFactory.createParameterEmpty("id不能為空值.");
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
	protected MailGroup parseParameters(InputArgumentDTO argument, SaveMailGroupMembersInputDTO parameters)
			throws Exception {
		MailGroup mailGroup = new MailGroup();
		mailGroup.setId(parameters.getId() == null || StringUtil.isEmpty(parameters.getId()) ? "" : parameters.getId());
		mailGroup.setToMailIds(parameters.getMails().stream().map(Object::toString).collect(Collectors.joining(",")));
		return mailGroup;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.want.base.service.BaseService#dataAccess(com.want.base.service.dto.
	 * InputArgumentDTO, java.lang.Object)
	 */
	@Override
	protected Boolean dataAccess(InputArgumentDTO argument, MailGroup parameters) throws Exception {
		settingService.saveMailGroupMembers(parameters);
		return true;
	}

}
