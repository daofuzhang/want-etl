/**
 * -------------------------------------------------------
 * @FileName：WorkerServiceImpl.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.service.worker.impl;

import java.io.IOException;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.want.domain.log.JobLog;
import com.want.domain.log.JobLog.Status;
import com.want.domain.log.JobWorkerMessage;
import com.want.mapper.JobLogMapper;
import com.want.mapper.JobWorkerMessageMapper;
import com.want.service.agent.AgentService;
import com.want.service.worker.WorkerService;
import com.want.service.worker.component.WorkerHandler;
import com.want.service.worker.component.WorkerHandler.WorkerEvent;
import com.want.service.worker.component.WorkerHandler.WorkerHandlerListener;

@Service
public class WorkerServiceImpl implements WorkerService, WorkerHandlerListener {

	@Autowired
	private ThreadPoolTaskExecutor workerExecutor;
	@Autowired
	private AgentService agentService;
	@Autowired
	private JobLogMapper jobLogMapper;
	@Autowired
	private JobWorkerMessageMapper jobWorkerMessageMapper;

	@Override
	public boolean startJob(String jobLogId) {
		if(workerExecutor.getActiveCount() >= workerExecutor.getMaxPoolSize()) {
			return false;
		}
		JobLog jobLog = jobLogMapper.findOne(jobLogId);
		if (jobLog == null) {
			return false;
		}
		jobLog.setAgentId(agentService.getAgentId());
		workerExecutor.execute(new WorkerHandler(jobLog, this));
		return true;
	}

	@Override
	public boolean stopJob(String jobLogId) {
		JobLog jobLog = jobLogMapper.findOne(jobLogId);
		if (jobLog == null || jobLog.getPid().isEmpty()) {
			return false;
		}
		ProcessBuilder pb = new ProcessBuilder().command("kill", "-9", jobLog.getPid())
				.directory(Paths.get(System.getProperty("user.dir")).toFile());
		try {
			pb.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public void onWorkerEvent(JobLog jobLog, int event, String message) {
		switch (event) {
		case WorkerEvent.JOB_START:
			jobLog.setStatus(Status.RUNNING.getText());
			jobLogMapper.update(jobLog);
			break;
		case WorkerEvent.JOB_END:
			if(jobWorkerMessageMapper.isSuccess(jobLog.getId())) {
				jobLog.setStatus(Status.DONE.getText());
			} else {
				jobLog.setStatus(Status.ERROR.getText());
			}
			jobLogMapper.update(jobLog);
			break;
		case WorkerEvent.JOB_INTERRUPT:
			jobLog.setStatus(Status.INTERRUPT.getText());
			jobLogMapper.update(jobLog);
			break;
		case WorkerEvent.WORKER_OUTPUT:
			jobWorkerMessageMapper.insert(JobWorkerMessage.create(jobLog.getId(), JobWorkerMessage.Type.NORMAL, message));
			break;
		case WorkerEvent.WORKER_ERROR:
			jobWorkerMessageMapper.insert(JobWorkerMessage.create(jobLog.getId(), JobWorkerMessage.Type.ERROR, message)); 
			break;
		default:
			break;
		}
	}

}
