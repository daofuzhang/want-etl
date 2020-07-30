/**
 * -------------------------------------------------------
 * @FileName：ThreadPoolConfig.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ThreadPoolConfig {

	@Bean(name = "taskExecutor")
	public ThreadPoolTaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		threadPoolTaskExecutor.setCorePoolSize(4);
		threadPoolTaskExecutor.setMaxPoolSize(4);
		threadPoolTaskExecutor.setKeepAliveSeconds(60);
		threadPoolTaskExecutor.setThreadNamePrefix("taskExecutor");
		threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
		threadPoolTaskExecutor.initialize();
		return threadPoolTaskExecutor;
	}
	
	@Bean(name = "workerExecutor")
	public ThreadPoolTaskExecutor workerExecutor() {
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		threadPoolTaskExecutor.setCorePoolSize(4);
		threadPoolTaskExecutor.setMaxPoolSize(4);
		threadPoolTaskExecutor.setKeepAliveSeconds(60);
		threadPoolTaskExecutor.setThreadNamePrefix("workerExecutor");
		threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
		threadPoolTaskExecutor.initialize();
		return threadPoolTaskExecutor;
	}

}
