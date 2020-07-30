/**
 * -------------------------------------------------------
 * @FileName：GetMailGroupService.java
 * @Description：简要描述本文件的内容
 * @Author：Dirk.Lee
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.controller.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.want.base.service.ResponseDataService;
import com.want.base.service.dto.InputArgumentDTO;
import com.want.base.service.exception.ResponseExceptionFactory;
import com.want.controller.service.dto.GetMailGroupInputDTO;
import com.want.controller.service.dto.MailGroupDTO;
import com.want.controller.service.dto.PageDTO;
import com.want.domain.mail.MailGroup;
import com.want.service.setting.SettingService;

/**
 * @author 80005151
 *
 */
@Service
public class GetMailGroupService
		extends ResponseDataService<GetMailGroupInputDTO, GetMailGroupInputDTO, PageDTO<MailGroupDTO>> {
	@Autowired
	private SettingService settingService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.want.base.service.BaseService#checkParameters(java.lang.Object)
	 */
	@Override
	protected void checkParameters(GetMailGroupInputDTO parameters) throws Exception {
		if (parameters.getPageIndex() == null) {
			throw ResponseExceptionFactory.createParameterEmpty("pageIndex不能為空值.");
		}
		if (parameters.getPageSize() == null) {
			throw ResponseExceptionFactory.createParameterEmpty("pageSize不能為空值.");
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
	protected GetMailGroupInputDTO parseParameters(InputArgumentDTO argument, GetMailGroupInputDTO parameters)
			throws Exception {
		return parameters;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.want.base.service.BaseService#dataAccess(com.want.base.service.dto.
	 * InputArgumentDTO, java.lang.Object)
	 */
	@Override
	protected PageDTO<MailGroupDTO> dataAccess(InputArgumentDTO argument, GetMailGroupInputDTO parameters)
			throws Exception {
		int offset = (parameters.getPageIndex() - 1) * parameters.getPageSize();
		int limit = parameters.getPageSize();
		long totalCount = settingService.getTotalMailGroupCount();
		List<MailGroupDTO> list = new ArrayList<MailGroupDTO>();
		List<MailGroup> mailGroups = settingService.getMailGroups(offset, limit);
		if (mailGroups != null && !mailGroups.isEmpty()) {
			mailGroups.forEach(new Consumer<MailGroup>() {

				@Override
				public void accept(MailGroup t) {
					MailGroupDTO dto = new MailGroupDTO();
					dto.setId(t.getId());
					dto.setName(t.getName());
					dto.setDescription(t.getDescription());
					dto.setMailIds(t.getToMailIds() == null || t.getToMailIds().isEmpty() ? Arrays.asList()
							: Arrays.asList(t.getToMailIds().split(",")));
					list.add(dto);
				}
			});
		}
		PageDTO<MailGroupDTO> results = new PageDTO<>();
		results.setContent(list);
		results.setTotalElements(totalCount);
		results.setTotalPage((int) (totalCount % limit > 0 ? totalCount / limit + 1 : totalCount / limit));

		return results;
	}
}