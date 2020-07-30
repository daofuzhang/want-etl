/**
 * -------------------------------------------------------
 * @FileName：TaskService.java
 * @Description：简要描述本文件的内容
 * @Author：Dirk.Lee
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.controller.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.want.base.service.ResponseDataService;
import com.want.base.service.dto.InputArgumentDTO;
import com.want.base.service.exception.ResponseExceptionFactory;
import com.want.controller.service.dto.DynamicDTO;
import com.want.controller.service.dto.JobDTO;
import com.want.controller.service.dto.TaskDTO;
import com.want.domain.job.Job;
import com.want.domain.job.JobBelong;
import com.want.domain.job.Task;
import com.want.domain.schedule.Schedule;
import com.want.service.job.JobService;
import com.want.util.StringUtil;

/**
 * @author 80005151
 *
 */
@Service
public class GetJobService extends ResponseDataService<String, String, JobDTO> {

	static final Gson gson = new Gson();

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
			throw ResponseExceptionFactory.createParameterEmpty("jobId不為空值.");
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
	@SuppressWarnings("serial")
	@Override
	protected JobDTO dataAccess(InputArgumentDTO argument, String parameters) throws Exception {

		Job job = jobService.getJob(parameters);
		List<Schedule> schedules = jobService.getSchedule(parameters);

		JobBelong jobBelong = jobService.getJobBelongByJobId(job.getId());

		JobDTO jobDTO = new JobDTO();
		jobDTO.setGroupId(jobBelong == null ? "" : jobBelong.getGroupId());
		jobDTO.setFolderId(jobBelong == null ? "" : jobBelong.getFolderId());
		jobDTO.setJobId(job.getId());
		jobDTO.setName(job.getName());
		jobDTO.setMemo(job.getMemo());
		jobDTO.setBatchFuncId(job.getBatchFuncId());
		jobDTO.setSendBatchLogger(job.getSendBatchLogger() != 0);
		jobDTO.setDynamics(job.getDynamics() == null || job.getDynamics().isEmpty() ? Arrays.asList()
				: gson.fromJson(job.getDynamics(), new TypeToken<List<DynamicDTO>>() {
				}.getType()));
		jobDTO.setStrategy(job.getStrategy());
		jobDTO.setExpressions(new ArrayList<>());
		jobDTO.setMailGroupIds(
				job.getMailGroupIds() == null || StringUtil.isEmpty(job.getMailGroupIds()) ? new ArrayList<>()
						: Arrays.asList(job.getMailGroupIds().split(",")));
		jobDTO.setTasks(new ArrayList<>());

		for (Schedule schedule : schedules) {
			jobDTO.getExpressions().add(schedule.getExpression());
		}

		List<Task> tasks = jobService.getTasks(parameters);
		if (tasks != null && !tasks.isEmpty()) {
			tasks.forEach(new Consumer<Task>() {

				@Override
				public void accept(Task t) {
					TaskDTO dto = new TaskDTO();
					dto.setId(t.getId() == null ? "" : t.getId());
					dto.setJobId(t.getJobId());
					dto.setType(t.getType());
					dto.setOrder(t.getOrder() == null ? "" : t.getOrder());
					dto.setStatement(t.getStatement() == null ? "" : t.getStatement());
					dto.setSourceServerId(t.getSourceServerId() == null ? "" : t.getSourceServerId());
					dto.setFunctionServerId(t.getFunctionServerId() == null ? "" : t.getFunctionServerId());
					dto.setFunction(t.getFunction() == null ? "" : t.getFunction());
					dto.setImportForm(t.getImportForm() == null ? "" : t.getImportForm());
					dto.setTargetServerId(t.getTargetServerId() == null ? "" : t.getTargetServerId());
					dto.setDatabase(t.getDatabase() == null ? "" : t.getDatabase());
					dto.setTable(t.getTable() == null ? "" : t.getTable());
					dto.setCondition(t.getCondition() == null ? "" : t.getCondition());
					dto.setCoreSize(t.getCoreSize() == null ? "" : t.getCoreSize());
					dto.setRules(t.getRules() == null ? "" : t.getRules());
					dto.setMemo(t.getMemo() == null ? "" : t.getMemo());
					jobDTO.getTasks().add(dto);
				}
			});
		}
		return jobDTO;
	}
}
