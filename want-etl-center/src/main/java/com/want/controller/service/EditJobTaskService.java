/**
 * -------------------------------------------------------
 * @FileName：EditJobTaskService.java
 * @Description：简要描述本文件的内容
 * @Author：Dirk.Lee
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.controller.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

import org.springframework.stereotype.Service;

import com.want.base.service.ResponseDataService;
import com.want.base.service.dto.InputArgumentDTO;
import com.want.base.service.exception.ResponseExceptionFactory;
import com.want.controller.service.EditJobTaskService.InnerEditJobTask;
import com.want.controller.service.dto.EditJobTaskInputDTO;
import com.want.controller.service.dto.TaskDTO;
import com.want.domain.job.Job;
import com.want.domain.job.Task;
import com.want.util.StringUtil;

/**
 * @author 80005151
 *
 */
@Service
public class EditJobTaskService extends ResponseDataService<EditJobTaskInputDTO, InnerEditJobTask, Boolean> {

	// @Autowired
	// private JobService jobService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.want.base.service.BaseService#checkParameters(java.lang.Object)
	 */
	@Override
	protected void checkParameters(EditJobTaskInputDTO parameters) throws Exception {
		if (parameters == null) {
			throw ResponseExceptionFactory.createParameterEmpty("Input不能為空值.");
		}
		if (parameters.getGroupId() == null || StringUtil.isEmpty(parameters.getGroupId())) {
			throw ResponseExceptionFactory.createParameterEmpty("groupId不能為空值.");
		}
		if (parameters.getFolderId() == null || StringUtil.isEmpty(parameters.getFolderId())) {
			throw ResponseExceptionFactory.createParameterEmpty("folderId不能為空值.");
		}
		if (parameters.getJobId() == null || StringUtil.isEmpty(parameters.getJobId())) {
			throw ResponseExceptionFactory.createParameterEmpty("jobId不能為空值.");
		}
		if (parameters.getName() == null || StringUtil.isEmpty(parameters.getName())) {
			throw ResponseExceptionFactory.createParameterEmpty("name不能為空值.");
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
	protected InnerEditJobTask parseParameters(InputArgumentDTO argument, EditJobTaskInputDTO parameters)
			throws Exception {
		Map<Job, List<Task>> map = new HashMap<Job, List<Task>>();
		Job job = new Job();
		List<Task> list = new ArrayList<>();
		map.put(job, list);

		job.setName(parameters.getName());
		job.setStrategy(parameters.getStrategy());
		job.setMailGroupIds(parameters.getMailGroupIds() == null || parameters.getMailGroupIds().isEmpty() ? ""
				: String.join(",", parameters.getMailGroupIds()));
		job.setMemo(parameters.getMemo());

		if (!StringUtil.isEmpty(parameters.getJobId())) {
			job.setId(parameters.getJobId());
		}

		if (parameters.getTasks() == null && !parameters.getTasks().isEmpty()) {
			parameters.getTasks().forEach(new Consumer<TaskDTO>() {

				@Override
				public void accept(TaskDTO t) {
					Task task = new Task();
					task.setId(t.getId());
					task.setJobId(t.getJobId());
					task.setType(t.getType());
					task.setOrder(t.getOrder());
					task.setStatement(t.getStatement());
					task.setSourceServerId(t.getSourceServerId());
					task.setFunctionServerId(t.getFunctionServerId());
					task.setFunction(t.getFunction());
					task.setImportForm(t.getImportForm());
					task.setTargetServerId(t.getTargetServerId());
					task.setDatabase(t.getDatabase());
					task.setTable(t.getTable());
					task.setCondition(t.getCondition());
					task.setCoreSize(t.getCoreSize());
					task.setRules(t.getRules());
					task.setMemo(t.getMemo());

					list.add(task);
				}
			});
		}

		InnerEditJobTask innerEditJobTask = new InnerEditJobTask();
		innerEditJobTask.setGroupId(parameters.getGroupId());
		innerEditJobTask.setFolderId(parameters.getFolderId());
		innerEditJobTask.setMap(map);

		return innerEditJobTask;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.want.base.service.BaseService#dataAccess(com.want.base.service.dto.
	 * InputArgumentDTO, java.lang.Object)
	 */
	@Override
	protected Boolean dataAccess(InputArgumentDTO argument, InnerEditJobTask parameters) throws Exception {
		parameters.getMap().entrySet().forEach(new Consumer<Entry<Job, List<Task>>>() {

			@Override
			public void accept(Entry<Job, List<Task>> t) {
				// jobService.saveJob(t.getKey(), t.getValue(), parameters.getGroupId(),
				// parameters.getFolderId());
			}

		});
		return true;
	}

	class InnerEditJobTask {
		String groupId;
		String folderId;
		Map<Job, List<Task>> map;

		public String getGroupId() {
			return groupId;
		}

		public void setGroupId(String groupId) {
			this.groupId = groupId;
		}

		public String getFolderId() {
			return folderId;
		}

		public void setFolderId(String folderId) {
			this.folderId = folderId;
		}

		public Map<Job, List<Task>> getMap() {
			return map;
		}

		public void setMap(Map<Job, List<Task>> map) {
			this.map = map;
		}

	}
}
