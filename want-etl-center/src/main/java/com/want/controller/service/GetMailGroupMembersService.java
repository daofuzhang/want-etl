/**
 * -------------------------------------------------------
 * @FileName：GetMailGroupMembersService.java
 * @Description：简要描述本文件的内容
 * @Author：Dirk.Lee
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.controller.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.want.base.service.ResponseDataService;
import com.want.base.service.dto.InputArgumentDTO;
import com.want.base.service.exception.ResponseExceptionFactory;
import com.want.controller.service.dto.MailGroupMemberDTO;
import com.want.domain.mail.Mail;
import com.want.service.setting.SettingService;
import com.want.util.StringUtil;

/**
 * @author 80005151
 *
 */
@Service
public class GetMailGroupMembersService extends ResponseDataService<String, String, List<MailGroupMemberDTO>> {

	@Autowired
	private SettingService settingService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.want.base.service.BaseService#checkParameters(java.lang.Object)
	 */
	@Override
	protected void checkParameters(String parameters) throws Exception {
		if (StringUtil.isEmpty(parameters)) {
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
	protected String parseParameters(InputArgumentDTO argument, String parameters) throws Exception {
		return parameters;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.want.base.service.BaseService#dataAccess(com.want.base.service.dto.
	 * InputArgumentDTO, java.lang.Object)
	 */
	@Override
	protected List<MailGroupMemberDTO> dataAccess(InputArgumentDTO argument, String parameters) throws Exception {
		List<MailGroupMemberDTO> result = new ArrayList<MailGroupMemberDTO>();
		List<Mail> mails = settingService.getMailByMailGroupId(parameters);
		if (mails != null && !mails.isEmpty()) {
			for (Mail mail : mails) {
				MailGroupMemberDTO dto = new MailGroupMemberDTO();
				dto.setId(mail.getId());
				dto.setName(mail.getName());
				result.add(dto);
			}
		}
		return result;
	}

}
