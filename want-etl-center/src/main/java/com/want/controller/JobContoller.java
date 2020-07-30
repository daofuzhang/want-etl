/**
 * -------------------------------------------------------
 * @FileName：AgentContoller.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
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
import com.want.controller.service.DeleteFolderService;
import com.want.controller.service.DeleteGroupService;
import com.want.controller.service.DeleteJobService;
import com.want.controller.service.EditJobTaskService;
import com.want.controller.service.GetGroupsService;
import com.want.controller.service.GetJobService;
import com.want.controller.service.ManualCommandService;
import com.want.controller.service.SaveFolderService;
import com.want.controller.service.SaveGroupService;
import com.want.controller.service.SaveJobService;
import com.want.controller.service.SaveMailService;
import com.want.controller.service.StartJobService;
import com.want.controller.service.StopJobService;
import com.want.controller.service.dto.EditJobTaskInputDTO;
import com.want.controller.service.dto.GroupDTO;
import com.want.controller.service.dto.JobDTO;
import com.want.controller.service.dto.SaveFolderInputDTO;
import com.want.controller.service.dto.SaveGroupInputDTO;
import com.want.controller.service.dto.SaveJobInputDTO;
import com.want.controller.service.dto.SaveMailInputDTO;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("job")
public class JobContoller {

	@Autowired
	private StartJobService startJobService;

	@PostMapping(value = "/start")
	@ApiImplicitParams({ @ApiImplicitParam(name = "jobId", value = "工作編號", required = true) })
	public ResultDTO<Void> start(String jobId) {
		return startJobService.request(null, jobId);
	}

	@Autowired
	private StopJobService stopJobService;

	@PostMapping(value = "/stop")
	@ApiImplicitParams({ @ApiImplicitParam(name = "jobLogId", value = "工作紀錄編號", required = true) })
	public ResultDTO<Void> stop(String jobLogId) {
		return stopJobService.request(null, jobLogId);
	}

	@Autowired
	private GetGroupsService getGroupsService;

	@PostMapping(value = "/getGroups")
	@ApiImplicitParams({})
	public ResultDTO<List<GroupDTO>> getGroups() {
		return getGroupsService.request(null, null);
	}

	@Autowired
	private GetJobService getJobService;

	@PostMapping(value = "/getJob")
	@ApiImplicitParams({ @ApiImplicitParam(name = "jobId", value = "工作編號", required = true) })
	public ResultDTO<JobDTO> getJob(String jobId) {
		return getJobService.request(null, jobId);
	}

	@Autowired
	private SaveGroupService saveGroupService;

	@PostMapping(value = "/saveGroup")
	@ApiImplicitParams({})
	public ResultDTO<Boolean> saveGroup(@RequestBody SaveGroupInputDTO input) {
		return saveGroupService.request(null, input);
	}

	@Autowired
	private SaveFolderService saveFolderService;

	@PostMapping(value = "/saveFolder")
	@ApiImplicitParams({})
	public ResultDTO<Boolean> saveFolder(@RequestBody SaveFolderInputDTO input) {
		return saveFolderService.request(null, input);
	}

	@Autowired
	private SaveJobService saveJobService;

	@PostMapping(value = "/saveJob")
	@ApiImplicitParams({})
	public ResultDTO<Boolean> saveJob(@RequestBody SaveJobInputDTO input) {
		return saveJobService.request(null, input);
	}

	@Autowired
	private SaveMailService saveMailService;

	@PostMapping(value = "/saveMail")
	@ApiImplicitParams({})
	public ResultDTO<Boolean> saveMail(@RequestBody SaveMailInputDTO input) {
		return saveMailService.request(null, input);
	}

	@Autowired
	private DeleteGroupService deleteGroupService;

	@PostMapping(value = "/deleteGroup")
	@ApiImplicitParams({ @ApiImplicitParam(name = "groupId", value = "群組編號", required = true) })
	public ResultDTO<Void> deleteGroup(String groupId) {
		return deleteGroupService.request(null, groupId);
	}

	@Autowired
	private DeleteFolderService deleteFolderService;

	@PostMapping(value = "/deleteFolder")
	@ApiImplicitParams({ @ApiImplicitParam(name = "folderId", value = "中層編號", required = true) })
	public ResultDTO<Void> deleteFolder(String folderId) {
		return deleteFolderService.request(null, folderId);
	}

	@Autowired
	private DeleteJobService deleteJobService;

	@PostMapping(value = "/deleteJob")
	@ApiImplicitParams({ @ApiImplicitParam(name = "jobId", value = "工作編號", required = true) })
	public ResultDTO<Void> deleteJob(String jobId) {
		return deleteJobService.request(null, jobId);
	}

	@Autowired
	private EditJobTaskService editJobTaskService;

	@PostMapping(value = "/editJobTasks")
	@ApiImplicitParams({})
	public ResultDTO<Boolean> editJobTasks(@RequestBody EditJobTaskInputDTO input) {
		return editJobTaskService.request(null, input);
	}
	
	@Autowired
	private ManualCommandService manualCommandService;

	@PostMapping(value = "/manualCommand")
	@ApiImplicitParams({ @ApiImplicitParam(name = "jobId", value = "工作編號", required = true) })
	public ResultDTO<String> editJobTasks(String jobId) {
		return manualCommandService.request(null, jobId);
	}

}
