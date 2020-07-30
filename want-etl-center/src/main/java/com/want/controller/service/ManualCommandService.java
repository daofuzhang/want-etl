/**
 * -------------------------------------------------------
 * @FileName：ManualCommandService.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.controller.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.want.base.service.ResponseDataService;
import com.want.base.service.dto.InputArgumentDTO;
import com.want.base.service.exception.ResponseExceptionFactory;
import com.want.domain.batchlogger.BatchLogger;
import com.want.domain.job.Job;
import com.want.domain.job.Task;
import com.want.domain.server.JCOServer;
import com.want.domain.server.JDBCServer;
import com.want.mapper.BatchLoggerMapper;
import com.want.mapper.JCOServerMapper;
import com.want.mapper.JDBCServerMapper;
import com.want.mapper.JobLogMapper;
import com.want.mapper.JobMapper;
import com.want.mapper.TaskMapper;
import com.want.service.center.component.JobFactory;
import com.want.util.RSAUtil;
import com.want.util.StringUtil;
import com.want.worker.dto.JobDTO;

@Service
public class ManualCommandService extends ResponseDataService<String, String, String> {

	@Autowired
	private JobMapper jobMapper;
	@Autowired
	private TaskMapper taskMapper;
	@Autowired
	private JDBCServerMapper jdbcServerMapper;
	@Autowired
	private JCOServerMapper jcoServerMapper;
	@Autowired
	private JobLogMapper jobLogMapper;
	@Autowired
	private BatchLoggerMapper batchLoggerMapper;

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
	protected String dataAccess(InputArgumentDTO argument, String jobId) throws Exception {
		Job job = jobMapper.findOne(jobId);
		if (job == null) {
			return null;
		}
		if (jobLogMapper.isJobRunning(jobId)) {
			return null;
		}
		List<Task> tasks = taskMapper.findByJobId(job.getId());
		List<String> jdbcServerIds = getJdbcServerIds(tasks);
		// handle batch log server.
		String batchLoggerServerId = null;
		if (job.getSendBatchLogger() == 1) {
			BatchLogger batchLogger = batchLoggerMapper.findOne();
			if (batchLogger != null) {
				batchLoggerServerId = batchLogger.getId();
				jdbcServerIds.add(batchLoggerServerId);
			}
		}
		List<JDBCServer> jdbcServer = jdbcServerMapper.findByIds(jdbcServerIds);
		List<JCOServer> jcoServer = jcoServerMapper.findByIds(getJcoServerIds(tasks));
		JobDTO jobTO = JobFactory.create(job, tasks, jdbcServer, jcoServer, batchLoggerServerId);
		String parameter = JobFactory.toJson(jobTO);
		return RSAUtil.encodeString(RSAUtil.PUBLIC_KEY, parameter);
	}

	private List<String> getJcoServerIds(List<Task> tasks) {
		return tasks.stream()
				.filter(task -> task.getFunctionServerId() != null && !task.getFunctionServerId().isEmpty())
				.map(task -> task.getFunctionServerId()).distinct().collect(Collectors.toList());
	}

	private List<String> getJdbcServerIds(List<Task> tasks) {
		List<String> jdbcServerIds = new ArrayList<>();
		tasks.forEach(task -> {
			if (!StringUtil.isEmpty(task.getSourceServerId())) {
				jdbcServerIds.add(task.getSourceServerId());
			}
			if (!StringUtil.isEmpty(task.getTargetServerId())) {
				jdbcServerIds.add(task.getTargetServerId());
			}
		});
		return jdbcServerIds.stream().distinct().collect(Collectors.toList());
	}

}
