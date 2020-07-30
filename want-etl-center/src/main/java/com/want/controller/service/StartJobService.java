/**
 * -------------------------------------------------------
 * @FileName：StartJobService.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
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
import com.want.service.center.CenterService;
import com.want.service.center.component.JobRequst.Priority;
import com.want.util.StringUtil;

@Service
public class StartJobService extends ResponseService<String, String> {

	@Autowired
	private CenterService centerService;

	@Override
	protected void checkParameters(String parameters) throws Exception {
		if (StringUtil.isEmpty(parameters)) {
			throw ResponseExceptionFactory.createParameterEmpty("jobId不為空值.");
		}
	}

	@Override
	protected String parseParameters(InputArgumentDTO argument, String parameters) throws Exception {
		return parameters;
	}

	@Override
	protected Void dataAccess(InputArgumentDTO argument, String parameters) throws Exception {
		centerService.startJob(parameters, Priority.IMMEDIATE);
		return null;
	}

}
