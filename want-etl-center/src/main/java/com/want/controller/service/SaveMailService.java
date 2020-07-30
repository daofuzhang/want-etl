/**
 * -------------------------------------------------------
 * @FileName：SaveMailService.java
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
import com.want.controller.service.dto.SaveMailInputDTO;
import com.want.domain.mail.Mail;
import com.want.service.job.JobService;
import com.want.util.StringUtil;

/**
 * @author 80005151
 *
 */
@Service
public class SaveMailService extends ResponseDataService<SaveMailInputDTO, Mail, Boolean> {

	@Autowired
	private JobService jobService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.want.base.service.BaseService#checkParameters(java.lang.Object)
	 */
	@Override
	protected void checkParameters(SaveMailInputDTO parameters) throws Exception {
		if (parameters == null) {
			throw ResponseExceptionFactory.createParameterEmpty("Input不能為空值.");
		}
		if (parameters.getName() == null || StringUtil.isEmpty(parameters.getName())) {
			throw ResponseExceptionFactory.createParameterEmpty("name不能為空值.");
		}
		if (parameters.getMail() == null || StringUtil.isEmpty(parameters.getMail())) {
			throw ResponseExceptionFactory.createParameterEmpty("mail不能為空值.");
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
	protected Mail parseParameters(InputArgumentDTO argument, SaveMailInputDTO parameters) throws Exception {
		Mail mail = new Mail();
		mail.setId(parameters.getId() == null || parameters.getId().isEmpty() ? null : parameters.getId());
		mail.setName(parameters.getName());
		mail.setMail(parameters.getMail());
		return mail;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.want.base.service.BaseService#dataAccess(com.want.base.service.dto.
	 * InputArgumentDTO, java.lang.Object)
	 */
	@Override
	protected Boolean dataAccess(InputArgumentDTO argument, Mail parameters) throws Exception {
		jobService.saveMail(parameters);
		return true;
	}

}
