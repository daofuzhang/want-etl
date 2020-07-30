/**
 * -------------------------------------------------------
 * @FileName：GetEndpointsService.java
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
import com.want.controller.service.dto.EndpointDTO;
import com.want.domain.server.JCOServer;
import com.want.domain.server.JDBCServer;
import com.want.domain.setting.DataBaseType;
import com.want.service.setting.SettingService;

/**
 * @author 80005151
 *
 */
@Service
public class GetEndpointsService extends ResponseDataService<String, String, List<EndpointDTO>> {
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
	protected List<EndpointDTO> dataAccess(InputArgumentDTO argument, String parameters) throws Exception {
		List<EndpointDTO> result = new ArrayList<EndpointDTO>();
		settingService.getJDBCServers().forEach(new Consumer<JDBCServer>() {

			@Override
			public void accept(JDBCServer t) {
				EndpointDTO dto = new EndpointDTO();
				dto.setId(t.getId());
				dto.setName(t.getName());
				dto.setType(getName(t.getType()));
				dto.setUrl(t.getUrl());
				dto.setUser(t.getUserName());
				dto.setPasswd("");
				result.add(dto);
			}
		});

		settingService.getJCOServers().forEach(new Consumer<JCOServer>() {

			@Override
			public void accept(JCOServer t) {
				EndpointDTO dto = new EndpointDTO();
				dto.setId(t.getId());
				dto.setName(t.getName());
				dto.setType(DataBaseType.JCO.getName());
				dto.setAshost(t.getAshost());
				dto.setClient(t.getClient());
				dto.setSysnr(t.getSysnr());
				dto.setLang(t.getLang());
				dto.setPeakLimit(t.getPeakLimit());
				dto.setPoolCapacity(t.getPoolCapacity());
				dto.setUser(t.getUser());
				dto.setPasswd("");
				result.add(dto);
			}
		});
		return result;
	}

	private String getName(String type) {
		String name = "";
		if (DataBaseType.MYSQL.getType().equals(type)) {
			name = DataBaseType.MYSQL.getName();
		} else if (DataBaseType.ORACLE.getType().equals(type)) {
			name = DataBaseType.ORACLE.getName();
		} else if (DataBaseType.HANA.getType().equals(type)) {
			name = DataBaseType.HANA.getName();
		}

		return name;
	}

}
