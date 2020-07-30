/**
 * -------------------------------------------------------
 * @FileName：ScheduleServiceImpl.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.service.schedule.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import com.want.domain.schedule.Schedule;
import com.want.mapper.ScheduleMapper;
import com.want.service.center.CenterService;
import com.want.service.center.component.JobRequst.Priority;
import com.want.service.schedule.ScheduleService;

@Service
public class ScheduleServiceImpl implements ScheduleService {

	final Map<String, ScheduledFuture<?>> scheduledFutureMap = new HashMap<>();

	@Autowired
	private ThreadPoolTaskScheduler taskScheduler;
	@Autowired
	private ScheduleMapper scheduleMapper;
	@Autowired
	private CenterService agentService;

	@PostConstruct
	public void postConstruct() {
		List<Schedule> scheduleList = scheduleMapper.findAll();
		if (scheduleList == null || scheduleList.isEmpty()) {
			return;
		}
		for (Schedule schedule : scheduleList) {
			if (schedule.getEnable() == 0) {
				continue;
			}
			scheduleJob(schedule);
		}
	}

	@Override
	public void restAll() {
		clearAllScheduleJob();
		postConstruct();
	}

	@Override
	public void restOne(String id) {
		Schedule schedule = scheduleMapper.findOne(id);
		if (schedule == null) {
			return;
		}
		scheduledFutureMap.get(schedule.getId()).cancel(true);
		scheduleJob(schedule);
	}

	private void scheduleJob(Schedule schedule) {
		ScheduledFuture<?> future = taskScheduler.schedule(new Runnable() {

			@Override
			public void run() {
				agentService.startJob(schedule.getJobId(), Priority.fromCode(schedule.getPriority()));
			}

		}, new CronTrigger(schedule.getExpression()));
		scheduledFutureMap.put(schedule.getId(), future);
	}

	private void clearAllScheduleJob() {
		taskScheduler.getScheduledThreadPoolExecutor().getQueue().clear();
	}

}
