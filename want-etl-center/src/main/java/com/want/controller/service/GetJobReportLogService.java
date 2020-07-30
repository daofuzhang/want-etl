/**
 * -------------------------------------------------------
 * @FileName：GetJobReportLogService.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.controller.service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.want.base.service.ResponseDataService;
import com.want.base.service.dto.InputArgumentDTO;
import com.want.base.service.exception.ResponseExceptionFactory;
import com.want.controller.service.dto.JobReportLogDTO;
import com.want.controller.service.dto.JobReportLogMessageDTO;
import com.want.domain.log.JobLog;
import com.want.domain.log.JobWorkerMessage;
import com.want.mapper.JobLogMapper;
import com.want.mapper.JobWorkerMessageMapper;
import com.want.worker.dto.JCOServerDTO;
import com.want.worker.dto.JDBCServerDTO;
import com.want.worker.dto.JobDTO;
import com.want.worker.dto.MailGroupDTO;
import com.want.worker.dto.TaskDTO;

@Service
public class GetJobReportLogService extends ResponseDataService<String, String, JobReportLogDTO> {

	final static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	final Gson gson = new GsonBuilder().setPrettyPrinting().create();

	@Autowired
	private JobLogMapper jobLogMapper;
	@Autowired
	private JobWorkerMessageMapper jobWorkerMessageMapper;

	@Override
	protected void checkParameters(String jobLogId) throws Exception {
		if (jobLogId == null) {
			throw ResponseExceptionFactory.createParameterEmpty("jobLogId不能為空值.");
		}
	}

	@Override
	protected String parseParameters(InputArgumentDTO argument, String jobLogId) throws Exception {
		return jobLogId;
	}

	@Override
	protected JobReportLogDTO dataAccess(InputArgumentDTO argument, String jobLogId) throws Exception {
		JobLog jobLog = jobLogMapper.findOne(jobLogId);
		if (jobLog == null) {
//			throw ResponseExceptionFactory.
		}
		JobReportLogDTO dto = new JobReportLogDTO();
		dto.setJobLogId(jobLogId);
		dto.setAgentId(jobLog.getAgentId());
		dto.setStartTime(convertTime(jobLog.getStartTime()));
		dto.setEndTime(convertTime(jobLog.getEndTime()));
		dto.setParameter(filterSensitive(jobLog.getParameter()));
		dto.setStatus(jobLog.getStatus());
		dto.setMessage(new ArrayList<>());

		List<JobWorkerMessage> jobWorkerMessage = jobWorkerMessageMapper.findByJobLogId(jobLogId);
		if (jobWorkerMessage != null && !jobWorkerMessage.isEmpty()) {
			for (JobWorkerMessage message : jobWorkerMessage) {
				JobReportLogMessageDTO e = new JobReportLogMessageDTO();
				e.setContent(message.getMessage());
				e.setType(message.getType());
				e.setTime(convertTime(message.getCreateTime()));
				dto.getMessage().add(e);
			}
		}
		return dto;
	}

	private String convertTime(Timestamp timestamp) {
		if (timestamp == null) {
			return "-";
		}
		return dateFormat.format(new Date(timestamp.getTime()));
	}

	private JobDTO filterSensitive(String parameter) {
		JobDTO job = gson.fromJson(parameter, JobDTO.class);
		MailGroupDTO mailGroup = job.getMailGroup();
		if (mailGroup != null) {
			mailGroup.setServerPassword(null);
		}
		for (TaskDTO task : job.getTasks()) {
			JCOServerDTO functionServer = task.getFunctionServer();
			if (functionServer != null) {
				functionServer.setPasswd(null);
			}
			JDBCServerDTO sourceServer = task.getSourceServer();
			if (sourceServer != null) {
				sourceServer.setPassword(null);
			}
			JDBCServerDTO targetServer = task.getTargetServer();
			if (targetServer != null) {
				targetServer.setPassword(null);
			}
		}
		return job;
	}

}
