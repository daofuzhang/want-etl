/**
 * -------------------------------------------------------
 * @FileName：AgentServiceImpl.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.service.agent.impl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.want.domain.server.Agent;
import com.want.mapper.AgentMapper;
import com.want.service.agent.AgentService;

@Service
public class AgentServiceImpl implements AgentService {

	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	@Autowired
	private ThreadPoolTaskExecutor workerExecutor;
	@Autowired
	private AgentMapper agentMapper;

	@Value("${server.port}")
	private String port;
	@Value("${server.servlet.context-path}")
	private String contextPath;

	private Agent agent;

	@PostConstruct
	public void postConstruct() throws ClientProtocolException, IOException {
		this.agent = new Agent();
		setup();
		agentMapper.insert(agent);
		taskExecutor.execute(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						synchronized (AgentServiceImpl.this) {
							if (agent != null) {
								setup();
								agentMapper.replace(agent);
							}
						}
						Thread.sleep(1000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
		});
	}

	@PreDestroy
	public void preDestroy() {
		synchronized (AgentServiceImpl.this) {
			agent = null;
		}
	}

	private void setup() {
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			agent.setName(inetAddress.getHostName());
			agent.setHost("http://" + inetAddress.getHostAddress() + ":" + port + contextPath);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		agent.setActive(workerExecutor.getActiveCount());
		agent.setMaxActive(workerExecutor.getMaxPoolSize());
		agent.setUpdateTime(new Timestamp(System.currentTimeMillis()));
	}

	@Override
	public String getAgentId() {
		return agent.getId();
	}
}
