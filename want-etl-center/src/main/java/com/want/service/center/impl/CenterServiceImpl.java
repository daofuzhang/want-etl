/**
 * -------------------------------------------------------
 * @FileName：AgentServiceImpl.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.service.center.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.want.domain.batchlogger.BatchLogger;
import com.want.domain.job.Job;
import com.want.domain.job.Task;
import com.want.domain.log.JobLog;
import com.want.domain.log.JobLog.Status;
import com.want.domain.server.Agent;
import com.want.domain.server.JCOServer;
import com.want.domain.server.JDBCServer;
import com.want.mapper.AgentMapper;
import com.want.mapper.BatchLoggerMapper;
import com.want.mapper.JCOServerMapper;
import com.want.mapper.JDBCServerMapper;
import com.want.mapper.JobLogMapper;
import com.want.mapper.JobMapper;
import com.want.mapper.TaskMapper;
import com.want.service.center.CenterService;
import com.want.service.center.component.JobFactory;
import com.want.service.center.component.JobRequestQueue;
import com.want.service.center.component.JobRequestQueue.JobRequestEventListener;
import com.want.service.center.component.JobRequst;
import com.want.service.center.component.JobRequst.Priority;
import com.want.util.StringUtil;
import com.want.util.VelocityUtil;
import com.want.worker.dto.JobDTO;

@Service
public class CenterServiceImpl implements CenterService, JobRequestEventListener {
	@Autowired
	private AgentMapper agentMapper;
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

	private JobRequestQueue mJobRequestQueue;

	@PostConstruct
	public void postConstruct() {
		mJobRequestQueue = new JobRequestQueue(agentMapper);
		mJobRequestQueue.addJobRequestEventListener(this);
		mJobRequestQueue.start();
	}

	@PreDestroy
	public void preDestroy() {
		mJobRequestQueue.cancelAll(new JobRequestQueue.JobFilter() {

			@Override
			public boolean apply(JobRequst request) {
				JobLog jobLog = jobLogMapper.findOne(request.getLogId());
				jobLog.setStatus(Status.INTERRUPT.getText());
				jobLogMapper.update(jobLog);
				return true;
			}
		});
		mJobRequestQueue.removeJobRequestEventListener(this);
		mJobRequestQueue.stop();
	}

	@Override
	public void startJob(String jobId, Priority priority) {
		Job job = jobMapper.findOne(jobId);
		if (job == null) {
			return;
		}
		if (jobLogMapper.isJobRunning(jobId)) {
			return;
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
		VelocityUtil.convert(jobTO);
		String parameter = JobFactory.toJson(jobTO);

		JobLog jobLog = new JobLog();
		jobLog.setJobId(job.getId());
		jobLog.setAgentId("");
		jobLog.setPid("");
		jobLog.setParameter(parameter);
		jobLog.setStatus(Status.WAIT.getText());
		jobLogMapper.insert(jobLog);

		JobRequst request = JobRequst.create(jobLog.getId(), priority);
		request.setTag(jobLog.getId());
		mJobRequestQueue.add(request);
	}

	@Override
	public boolean stopJob(String jobLogId) {
		JobLog log = jobLogMapper.findOne(jobLogId);
		if (log == null) {
			return false;
		}
		if (StringUtil.isEmpty(log.getAgentId())) {
			mJobRequestQueue.cancelAll(jobLogId);
		} else {
			Agent agent = agentMapper.findOne(log.getAgentId());
			if (agent == null) {
				return false;
			}
			try {
				HttpPost post = new HttpPost(agent.getHost() + "/job/stop?jobLogId=" + jobLogId);
				HttpResponse response = HttpClients.createDefault().execute(post);
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					return Boolean.valueOf(EntityUtils.toString(response.getEntity(), "UTF-8"));
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
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

	@Override
	public void onJobRequestEvent(JobRequst request, int event) {
	}

}
