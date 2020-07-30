/**
 * -------------------------------------------------------
 * @FileName：DeleteFolderService.java
 * @Description：简要描述本文件的内容
 * @Author：Dirk.Lee
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.controller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.want.base.service.ResponseService;
import com.want.base.service.dto.InputArgumentDTO;
import com.want.base.service.exception.ResponseExceptionFactory;
import com.want.service.job.JobService;
import com.want.util.StringUtil;

/**
 * @author 80005151
 *
 */
@Service
public class DeleteFolderService extends ResponseService<String, String> {
	@Autowired
	private JobService jobService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.want.base.service.BaseService#checkParameters(java.lang.Object)
	 */
	@Override
	protected void checkParameters(String parameters) throws Exception {
		if (StringUtil.isEmpty(parameters)) {
			throw ResponseExceptionFactory.createParameterEmpty("folderId不為空值.");
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
	protected Void dataAccess(InputArgumentDTO argument, String parameters) throws Exception {
		jobService.deleteFolder(parameters);
		return null;
	}

}
