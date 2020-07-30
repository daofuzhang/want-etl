/**
 * -------------------------------------------------------
 * @FileName：RequestDispatcher.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.service.center.component;

import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.want.domain.server.Agent;
import com.want.mapper.AgentMapper;

public class AgentDispatcher extends Thread {

	private final BlockingQueue<JobRequst> mQueue;

	private final AgentMapper mAgentMapper;

	private volatile boolean mQuit = false;

	public AgentDispatcher(BlockingQueue<JobRequst> queue, AgentMapper agentMapper) {
		mQueue = queue;
		mAgentMapper = agentMapper;
	}

	public void quit() {
		mQuit = true;
		interrupt();
	}

	public void run() {
		while (true) {
			try {
				processRequest();
			} catch (InterruptedException e) {
				if (mQuit) {
					Thread.currentThread().interrupt();
					return;
				}
			}
		}
	}

	private void processRequest() throws InterruptedException {
		JobRequst request = mQueue.take();
		processRequest(request);
	}

	void processRequest(JobRequst request) {
		if (request.isCanceled()) {
			request.finish("cancelled");
			return;
		}
		request.sendEvent(JobRequestQueue.JobRequestEvent.AGENT_DISPATCH_STARTED);

		boolean success = false;
		while (!success) {
			if (request.isCanceled()) {
				request.finish("cancelled");
				break;
			}

			success = requestIdleAgent(request);

			if (!success) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
				}
			}
		}
		request.sendEvent(JobRequestQueue.JobRequestEvent.AGENT_DISPATCH_FINISHED);
		request.finish("success");
	}

	boolean requestIdleAgent(JobRequst request) {
		List<Agent> agents = null;
		try {
			agents = mAgentMapper.findIdles();
		} catch (Exception e) {
		}
		if (agents == null || agents.isEmpty()) {
			return false;
		}
		for (Agent agent : agents) {
			try {
				HttpPost post = new HttpPost(agent.getHost() + "/job/start?jobLogId=" + request.getLogId());
				HttpResponse response = HttpClients.createDefault().execute(post);
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					return Boolean.valueOf(EntityUtils.toString(response.getEntity(), "UTF-8"));
				}
			} catch (Exception e) {
//				e.printStackTrace();
			}
		}
		return false;
	}

}
