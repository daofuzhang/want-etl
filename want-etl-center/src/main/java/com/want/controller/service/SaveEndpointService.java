/**
 * -------------------------------------------------------
 * @FileName：SaveEndpointService.java
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
import com.want.controller.service.SaveEndpointService.InnerEndpoint;
import com.want.controller.service.dto.SaveEndpointInputDTO;
import com.want.domain.server.JCOServer;
import com.want.domain.server.JDBCServer;
import com.want.domain.setting.DataBaseType;
import com.want.service.setting.SettingService;
import com.want.util.StringUtil;

/**
 * @author 80005151
 *
 */
@Service
public class SaveEndpointService extends ResponseDataService<SaveEndpointInputDTO, InnerEndpoint, Boolean> {

	@Autowired
	private SettingService settingService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.want.base.service.BaseService#checkParameters(java.lang.Object)
	 */
	@Override
	protected void checkParameters(SaveEndpointInputDTO parameters) throws Exception {
		if (parameters == null) {
			throw ResponseExceptionFactory.createParameterEmpty("Input不能為空值.");
		}

		if (StringUtil.isEmpty(getType(parameters.getType()))) {
			throw ResponseExceptionFactory.createParameterEmpty("無此資料庫類型.");
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
	protected InnerEndpoint parseParameters(InputArgumentDTO argument, SaveEndpointInputDTO parameters)
			throws Exception {
		InnerEndpoint innerEndpoint = new InnerEndpoint();

		if (DataBaseType.JCO.getName().equals(parameters.getType())) {
			JCOServer jcoServer = new JCOServer();
			jcoServer.setId(StringUtil.isEmpty(parameters.getId()) ? "" : parameters.getId());
			jcoServer.setName(parameters.getName());
			jcoServer.setAshost(parameters.getAshost());
			jcoServer.setUser(parameters.getUser());
			jcoServer.setPasswd(parameters.getPasswd());
			jcoServer.setClient(parameters.getClient());
			jcoServer.setLang(parameters.getLang());
			jcoServer.setPeakLimit(parameters.getPeakLimit());
			jcoServer.setPoolCapacity(parameters.getPoolCapacity());
			jcoServer.setSysnr(parameters.getSysnr());
			innerEndpoint.setJcoServer(jcoServer);
		} else {
			JDBCServer jdbcServer = new JDBCServer();
			jdbcServer.setId(StringUtil.isEmpty(parameters.getId()) ? "" : parameters.getId());
			jdbcServer.setName(parameters.getName());
			jdbcServer.setType(getType(parameters.getType()));
			jdbcServer.setUrl(parameters.getUrl());
			jdbcServer.setUserName(parameters.getUser());
			jdbcServer.setPassword(parameters.getPasswd());
			innerEndpoint.setJdbcServer(jdbcServer);
		}

		return innerEndpoint;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.want.base.service.BaseService#dataAccess(com.want.base.service.dto.
	 * InputArgumentDTO, java.lang.Object)
	 */
	@Override
	protected Boolean dataAccess(InputArgumentDTO argument, InnerEndpoint parameters) throws Exception {
		if (parameters.getJdbcServer() != null) {
			settingService.saveJDBCServer(parameters.getJdbcServer());
		} else if (parameters.getJcoServer() != null) {
			settingService.saveJCOServer(parameters.getJcoServer());
		}

		return true;
	}

	private String getType(String name) {
		String type = "";
		if (DataBaseType.MYSQL.getName().equals(name)) {
			type = DataBaseType.MYSQL.getType();
		} else if (DataBaseType.ORACLE.getName().equals(name)) {
			type = DataBaseType.ORACLE.getType();
		} else if (DataBaseType.HANA.getName().equals(name)) {
			type = DataBaseType.HANA.getType();
		} else if (DataBaseType.JCO.getName().equals(name)) {
			type = DataBaseType.JCO.getType();
		}
		return type;
	}

	class InnerEndpoint {
		private JDBCServer jdbcServer;
		private JCOServer jcoServer;

		public JDBCServer getJdbcServer() {
			return jdbcServer;
		}

		public void setJdbcServer(JDBCServer jdbcServer) {
			this.jdbcServer = jdbcServer;
		}

		public JCOServer getJcoServer() {
			return jcoServer;
		}

		public void setJcoServer(JCOServer jcoServer) {
			this.jcoServer = jcoServer;
		}

	}
}
