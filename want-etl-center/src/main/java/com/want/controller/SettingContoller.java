/**
 * -------------------------------------------------------
 * @FileName：SettingContoller.java
 * @Description：简要描述本文件的内容
 * @Author：Dirk.Lee
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.want.base.service.dto.ResultDTO;
import com.want.controller.service.DeleteEndpointService;
import com.want.controller.service.DeleteMailGroupService;
import com.want.controller.service.DeleteUserService;
import com.want.controller.service.GetDataBaseTypeService;
import com.want.controller.service.GetEndpointsService;
import com.want.controller.service.GetJCOServersService;
import com.want.controller.service.GetJDBCServersService;
import com.want.controller.service.GetMailGroupService;
import com.want.controller.service.GetMailsService;
import com.want.controller.service.GetOracleServersService;
import com.want.controller.service.GetUsersService;
import com.want.controller.service.SaveBatchLoggerService;
import com.want.controller.service.SaveEndpointService;
import com.want.controller.service.SaveMailGroupService;
import com.want.controller.service.SaveUserService;
import com.want.controller.service.dto.EndpointDTO;
import com.want.controller.service.dto.GetMailGroupInputDTO;
import com.want.controller.service.dto.GetOracleServersDTO;
import com.want.controller.service.dto.GetUsersInputDTO;
import com.want.controller.service.dto.JCOServerDTO;
import com.want.controller.service.dto.JDBCServerDTO;
import com.want.controller.service.dto.MailDTO;
import com.want.controller.service.dto.MailGroupDTO;
import com.want.controller.service.dto.PageDTO;
import com.want.controller.service.dto.SaveEndpointInputDTO;
import com.want.controller.service.dto.SaveMailGroupInputDTO;
import com.want.controller.service.dto.SaveUserInputDTO;
import com.want.controller.service.dto.UserDTO;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

/**
 * @author 80005151
 *
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("setting")
public class SettingContoller {

	@Autowired
	GetDataBaseTypeService getDataBaseTypeService;

	@PostMapping(value = "/getDataBaseTypes")
	@ApiImplicitParams({})
	public ResultDTO<List<String>> getDataBaseTypes() {
		return getDataBaseTypeService.request(null, null);
	}

	@Autowired
	private GetJDBCServersService getJDBCServersService;

	@PostMapping(value = "/getJDBCServers")
	@ApiImplicitParams({})
	public ResultDTO<List<JDBCServerDTO>> getJDBCServers() {
		return getJDBCServersService.request(null, null);
	}

	@Autowired
	private GetJCOServersService getJCOServersService;

	@PostMapping(value = "/getJCOServers")
	@ApiImplicitParams({})
	public ResultDTO<List<JCOServerDTO>> getJCOServers() {
		return getJCOServersService.request(null, null);
	}

	@Autowired
	private GetOracleServersService getOracleServersService;

	@PostMapping(value = "/getOracleServers")
	@ApiImplicitParams({})
	public ResultDTO<List<GetOracleServersDTO>> getOracleServers() {
		return getOracleServersService.request(null, null);
	}

	@Autowired
	GetEndpointsService getEndpointsService;

	@PostMapping(value = "/getEndpoints")
	@ApiImplicitParams({})
	public ResultDTO<List<EndpointDTO>> getEndpoints() {
		return getEndpointsService.request(null, null);
	}

	@Autowired
	private GetMailGroupService getMailGroupService;

	@PostMapping(value = "/getMailGroups")
	@ApiImplicitParams({})
	public ResultDTO<PageDTO<MailGroupDTO>> getMailGroups(@RequestBody GetMailGroupInputDTO input) {
		return getMailGroupService.request(null, input);
	}

	@Autowired
	GetMailsService getMailsService;

	@PostMapping(value = "/getMails")
	@ApiImplicitParams({})
	public ResultDTO<List<MailDTO>> getMails() {
		return getMailsService.request(null, null);
	}

	@Autowired
	GetUsersService getUsersService;

	@PostMapping(value = "/getUsers")
	@ApiImplicitParams({})
	public ResultDTO<PageDTO<UserDTO>> getUsers(@RequestBody GetUsersInputDTO input) {
		return getUsersService.request(null, input);
	}

	@Autowired
	SaveEndpointService saveEndpointService;

	@PostMapping(value = "/saveEndpoint")
	@ApiImplicitParams({})
	public ResultDTO<Boolean> saveEndpoint(@RequestBody SaveEndpointInputDTO input) {
		return saveEndpointService.request(null, input);
	}

	@Autowired
	SaveMailGroupService saveMailGroupService;

	@PostMapping(value = "/saveMailGroup")
	@ApiImplicitParams({})
	public ResultDTO<Boolean> saveMailGroup(@RequestBody SaveMailGroupInputDTO input) {
		return saveMailGroupService.request(null, input);
	}

	@Autowired
	SaveUserService saveUserService;

	@PostMapping(value = "/saveUser")
	@ApiImplicitParams({})
	public ResultDTO<Boolean> saveAccount(@RequestBody SaveUserInputDTO input) {
		return saveUserService.request(null, input);
	}

	@Autowired
	SaveBatchLoggerService saveBatchLoggerService;

	@PostMapping(value = "/saveBatchLogger")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "編號", required = true) })
	public ResultDTO<Boolean> saveBatchLogger(String id) {
		return saveBatchLoggerService.request(null, id);
	}

	@Autowired
	private DeleteEndpointService deleteEndpointService;

	@PostMapping(value = "/deleteEndpoint")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "編號", required = true) })
	public ResultDTO<Void> deleteEndpoint(String id) {
		return deleteEndpointService.request(null, id);
	}

	@Autowired
	private DeleteMailGroupService deleteMailGroupService;

	@PostMapping(value = "/deleteMailGroup")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "編號", required = true) })
	public ResultDTO<Void> deleteMailGroup(String id) {
		return deleteMailGroupService.request(null, id);
	}

	@Autowired
	private DeleteUserService deleteUserService;

	@PostMapping(value = "/deleteUser")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "編號", required = true) })
	public ResultDTO<Void> deleteUser(String id) {
		return deleteUserService.request(null, id);
	}
}
