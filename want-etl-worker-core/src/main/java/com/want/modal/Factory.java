/**
 * -------------------------------------------------------
 * @FileName：Factory.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.modal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.want.graylog.to.Message;

public class Factory {
	
	public final static int STATUS_RUNNING = 0;
	public final static int STATUS_DONE = 1;
	public final static int STATUS_ERROR = 2;
	public final static int STATUS_INTERRUPTED = 3;

	/**
	 * 產生Job列表。
	 * 
	 * @param list
	 * @return
	 *
	 * @author Luke.Tsai
	 */
	public static List<JobLog> createJobs(List<Message> list) {
		Function<Message, List<Object>> compositeKey = message -> Arrays.<Object>asList(message.getSource(),
				message.getGl2RemoteIp(), message.getPid(), message.getJobName());
		Map<Object, List<Message>> map = list.stream()
				.collect(Collectors.groupingBy(compositeKey, Collectors.toList()));
		List<JobLog> jobs = new ArrayList<>();
		map.forEach((k, v) -> jobs.add(convertJob(v)));
		return jobs.stream().sorted(Comparator.comparing(JobLog::getStartTime).reversed()).collect(Collectors.toList());
	}

	/**
	 * 產生Job。
	 * 
	 * @param list
	 * @return
	 *
	 * @author Luke.Tsai
	 */
	private static JobLog convertJob(List<Message> list) {
		Message msg = list.get(0);
		JobLog job = new JobLog();
		job.setVersion(msg.getJobVersion());
		job.setName(msg.getJobName());
		job.setPid(msg.getPid());
		job.setRunner(msg.getSource() + "/" + msg.getGl2RemoteIp());
		job.setTaskCount(Long.valueOf(msg.getJobTaskCount()));

		Map<Object, List<Message>> map = list.stream().collect(Collectors.groupingBy(o -> o.getTaskId()));
		List<TaskLog> tasks = new ArrayList<>();
		map.forEach((k, v) -> tasks.add(convertTask(v)));
		job.setTasks(tasks.stream().sorted(Comparator.comparing(TaskLog::getStartTime)).collect(Collectors.toList()));

		job.setStartTime(job.getTasks().get(0).getStartTime());
		job.setEndTime(job.getTasks().size() == Integer.valueOf(msg.getJobTaskCount())
				? job.getTasks().get(job.getTasks().size() - 1).getEndTime()
				: null);
		if (job.getEndTime() != null) {
			tasks.forEach((t) -> job.setStatus(
					job.getStatus() == null || job.getStatus() < t.getStatus() ? t.getStatus() : job.getStatus()));
		} else {
			job.setStatus(STATUS_RUNNING);
		}

		return job;
	}

	/**
	 * 產生Task。
	 * 
	 * @param list
	 * @return
	 *
	 * @author Luke.Tsai
	 */
	private static TaskLog convertTask(List<Message> list) {
		TaskLog task = new TaskLog();
		task.setStartTime(-1L);
		list.stream().sorted(Comparator.comparing(Message::getStatus)).forEach(new Consumer<Message>() {

			@Override
			public void accept(Message msg) {
				task.setId(msg.getTaskId());
				task.setStatus(msg.getStatus());
				switch (task.getStatus()) {
				case STATUS_RUNNING:
					task.setSetting(msg.getFullMessage());
					task.setStartTime(msg.getTimestampMs());
					break;
				case STATUS_DONE:
				case STATUS_ERROR:
					task.setResult(msg.getFullMessage());
					task.setEndTime(msg.getTimestampMs());
					break;
				}
			}
		});
		return task;
	}
}
