/**
 * -------------------------------------------------------
 * @FileName：JDBCService.java
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
import com.want.controller.service.dto.JDBCServerDTO;
import com.want.domain.server.JDBCServer;
import com.want.service.setting.SettingService;

/**
 * @author 80005151
 *
 */
@Service
public class GetJDBCServersService extends ResponseDataService<String, String, List<JDBCServerDTO>> {

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
	protected List<JDBCServerDTO> dataAccess(InputArgumentDTO argument, String parameters) throws Exception {
		List<JDBCServerDTO> results = new ArrayList<JDBCServerDTO>();
		List<JDBCServer> servers = settingService.getJDBCServers();
		if (servers != null && !servers.isEmpty()) {
			servers.forEach(new Consumer<JDBCServer>() {

				@Override
				public void accept(JDBCServer t) {
					JDBCServerDTO dto = new JDBCServerDTO();
					dto.setId(t.getId());
					dto.setName(t.getName());
					dto.setType(t.getType());
					dto.setUrl(t.getUrl());
					results.add(dto);
				}
			});
		}
		return results;
	}

}
