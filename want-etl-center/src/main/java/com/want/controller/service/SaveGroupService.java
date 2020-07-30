/**
 * -------------------------------------------------------
 * @FileName：AddGroupService.java
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
import com.want.controller.service.dto.SaveGroupInputDTO;
import com.want.domain.job.Group;
import com.want.service.job.JobService;

/**
 * @author 80005151
 *
 */
@Service
public class SaveGroupService extends ResponseDataService<SaveGroupInputDTO, Group, Boolean> {

	@Autowired
	private JobService jobService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.want.base.service.BaseService#checkParameters(java.lang.Object)
	 */
	@Override
	protected void checkParameters(SaveGroupInputDTO parameters) throws Exception {
		if (parameters == null) {
			throw ResponseExceptionFactory.createParameterEmpty("Input不能為空值.");
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
	protected Group parseParameters(InputArgumentDTO argument, SaveGroupInputDTO parameters) throws Exception {
		Group group = new Group();
		group.setId(parameters.getId() == null ? "" : parameters.getId());
		group.setName(parameters.getName());
		group.setMailGroupIds(parameters.getMailGroupIds() == null || parameters.getMailGroupIds().isEmpty() ? ""
				: String.join(",", parameters.getMailGroupIds()));
		group.setMemo(parameters.getMemo());
		return group;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.want.base.service.BaseService#dataAccess(com.want.base.service.dto.
	 * InputArgumentDTO, java.lang.Object)
	 */
	@Override
	protected Boolean dataAccess(InputArgumentDTO argument, Group parameters) throws Exception {
		jobService.saveGroup(parameters);
		return true;
	}

}
