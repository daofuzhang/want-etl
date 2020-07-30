/**
 * -------------------------------------------------------
 * @FileName：AddJobServcie.java
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

import com.google.gson.Gson;
import com.want.base.service.ResponseDataService;
import com.want.base.service.dto.InputArgumentDTO;
import com.want.base.service.exception.ResponseExceptionFactory;
import com.want.controller.service.SaveJobService.InnerJob;
import com.want.controller.service.dto.SaveJobInputDTO;
import com.want.controller.service.dto.TaskDTO;
import com.want.domain.job.Job;
import com.want.domain.job.Task;
import com.want.domain.schedule.Schedule;
import com.want.service.job.JobService;
import com.want.service.schedule.ScheduleService;
import com.want.util.StringUtil;
import com.want.worker.dto.JobStrategy;

/**
 * @author 80005151
 *
 */
@Service
public class SaveJobService extends ResponseDataService<SaveJobInputDTO, InnerJob, Boolean> {

	static final Gson gson = new Gson();

	@Autowired
	private JobService jobService;
	@Autowired
	private ScheduleService scheduleService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.want.base.service.BaseService#checkParameters(java.lang.Object)
	 */
	@Override
	protected void checkParameters(SaveJobInputDTO parameters) throws Exception {
		if (parameters == null) {
			throw ResponseExceptionFactory.createParameterEmpty("Input不能為空值.");
		}
		if (parameters.getGroupId() == null || StringUtil.isEmpty(parameters.getGroupId())) {
			throw ResponseExceptionFactory.createParameterEmpty("groupId不能為空值.");
		}
		if (parameters.getFolderId() == null || StringUtil.isEmpty(parameters.getFolderId())) {
			throw ResponseExceptionFactory.createParameterEmpty("folderId不能為空值.");
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
	protected InnerJob parseParameters(InputArgumentDTO argument, SaveJobInputDTO parameters) throws Exception {
		JobStrategy jobStrategy = JobStrategy.fromText(parameters.getStrategy());
		if (jobStrategy == null) {
			jobStrategy = JobStrategy.CONTINUNES;
		}

		InnerJob innerJob = new InnerJob();
		Job job = new Job();
		job.setId(parameters.getJobId() == null ? "" : parameters.getJobId());
		job.setName(parameters.getName());
		job.setStrategy(jobStrategy.getText());
		job.setMailGroupIds(parameters.getMailGroupIds() == null || parameters.getMailGroupIds().isEmpty() ? ""
				: String.join(",", parameters.getMailGroupIds()));
		job.setMemo(parameters.getMemo());
		job.setDynamics(
				parameters.getDynamics() == null || parameters.getDynamics().isEmpty() ? gson.toJson(Arrays.asList())
						: gson.toJson(parameters.getDynamics()));
		job.setBatchFuncId(parameters.getBatchFuncId() == null ? "" : parameters.getBatchFuncId());
		job.setSendBatchLogger(parameters.isSendBatchLogger() ? 1 : 0);

		if (parameters.getTasks() != null && !parameters.getTasks().isEmpty()) {
			List<Task> tasks = new ArrayList<>();

			parameters.getTasks().forEach(new Consumer<TaskDTO>() {

				@Override
				public void accept(TaskDTO t) {
					Task task = new Task();
					task.setId(t.getId());
					task.setJobId(t.getJobId());
					task.setType(t.getType());
					task.setOrder(t.getOrder());
					task.setStatement(t.getStatement() == null || t.getStatement().isEmpty() ? "" : t.getStatement());
					task.setSourceServerId(t.getSourceServerId() == null || t.getSourceServerId().isEmpty() ? ""
							: t.getSourceServerId());
					task.setFunctionServerId(t.getFunctionServerId() == null || t.getFunctionServerId().isEmpty() ? ""
							: t.getFunctionServerId());
					task.setFunction(t.getFunction() == null || t.getFunction().isEmpty() ? "" : t.getFunction());
					task.setImportForm(
							t.getImportForm() == null || t.getImportForm().isEmpty() ? "" : t.getImportForm());
					task.setTargetServerId(t.getTargetServerId() == null || t.getTargetServerId().isEmpty() ? ""
							: t.getTargetServerId());
					task.setDatabase(t.getDatabase() == null || t.getDatabase().isEmpty() ? "" : t.getDatabase());
					task.setTable(t.getTable() == null || t.getTable().isEmpty() ? "" : t.getTable());
					task.setCondition(t.getCondition() == null || t.getCondition().isEmpty() ? "" : t.getCondition());
					task.setCoreSize(t.getCoreSize() == null || t.getCoreSize().isEmpty() ? "" : t.getCoreSize());
					task.setRules(t.getRules() == null || t.getRules().isEmpty() ? "" : t.getRules());
					task.setMemo(t.getMemo() == null || t.getMemo().isEmpty() ? "" : t.getMemo());
					tasks.add(task);
				}
			});

			innerJob.setTasks(tasks);
		}

		List<Schedule> schedules = new ArrayList<>();
		if (parameters.getExpressions() != null && !parameters.getExpressions().isEmpty()) {
			for (String expression : parameters.getExpressions()) {
				Schedule schedule = new Schedule();
				schedule.setJobId(parameters.getJobId());
				schedule.setId("");
				schedule.setExpression(expression);
				schedule.setEnable(1);
				schedule.setPriority(1);
				schedules.add(schedule);
			}
		}
		innerJob.setSchedules(schedules);

		innerJob.setGroupId(parameters.getGroupId());
		innerJob.setFolderId(parameters.getFolderId());
		innerJob.setJob(job);
		return innerJob;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.want.base.service.BaseService#dataAccess(com.want.base.service.dto.
	 * InputArgumentDTO, java.lang.Object)
	 */
	@Override
	protected Boolean dataAccess(InputArgumentDTO argument, InnerJob parameters) throws Exception {
		jobService.saveJob(parameters.getJob(), parameters.getTasks(), parameters.getGroupId(),
				parameters.getFolderId(), parameters.getSchedules());
		scheduleService.restAll();
		return true;
	}

	class InnerJob {
		String groupId;
		String folderId;
		Job job;
		List<Task> tasks;
		List<Schedule> schedules;

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

		public Job getJob() {
			return job;
		}

		public void setJob(Job job) {
			this.job = job;
		}

		public List<Task> getTasks() {
			return tasks;
		}

		public void setTasks(List<Task> tasks) {
			this.tasks = tasks;
		}

		public List<Schedule> getSchedules() {
			return schedules;
		}

		public void setSchedules(List<Schedule> schedules) {
			this.schedules = schedules;
		}

	}
}
