/**
 * -------------------------------------------------------
 * @FileName：GetOracleServersService.java
 * @Description：简要描述本文件的内容
 * @Author：Dirk.Lee
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.controller.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.want.base.service.ResponseDataService;
import com.want.base.service.dto.InputArgumentDTO;
import com.want.controller.service.dto.GetOracleServersDTO;
import com.want.domain.batchlogger.BatchLogger;
import com.want.domain.server.JDBCServer;
import com.want.service.setting.SettingService;
import com.want.util.StringUtil;

/**
 * @author 80005151
 *
 */
@Service
public class GetOracleServersService extends ResponseDataService<String, String, List<GetOracleServersDTO>> {
	@Autowired
	private SettingService settingService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.want.base.service.BaseService#checkParameters(java.lang.Object)
	 */
	@Override
	protected void checkParameters(String parameters) throws Exception {
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
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.want.base.service.BaseService#dataAccess(com.want.base.service.dto.
	 * InputArgumentDTO, java.lang.Object)
	 */
	@Override
	protected List<GetOracleServersDTO> dataAccess(InputArgumentDTO argument, String parameters) throws Exception {
		List<GetOracleServersDTO> results = new ArrayList<GetOracleServersDTO>();
		BatchLogger batchLogger = settingService.getBatchLogger();
		List<JDBCServer> servers = settingService.getOracleServers();
		if (servers != null && !servers.isEmpty()) {
			servers.forEach(new Consumer<JDBCServer>() {

				@Override
				public void accept(JDBCServer t) {
					GetOracleServersDTO dto = new GetOracleServersDTO();
					dto.setId(t.getId());
					dto.setName(t.getName());
					dto.setType(t.getType());
					dto.setUrl(t.getUrl());
					dto.setBatchLogger(batchLogger == null || StringUtil.isEmpty(batchLogger.getId()) ? false
							: batchLogger.getId().equals(t.getId()));
					results.add(dto);
				}
			});
		}
		
		return results;
	}

}
