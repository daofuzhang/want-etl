/**
 * -------------------------------------------------------
 * @FileName：JobServiceImpl.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.service.job.impl;

import java.util.List;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.want.domain.job.Folder;
import com.want.domain.job.Group;
import com.want.domain.job.Job;
import com.want.domain.job.JobBelong;
import com.want.domain.job.JobBelongGroup;
import com.want.domain.job.Task;
import com.want.domain.mail.Mail;
import com.want.domain.schedule.Schedule;
import com.want.mapper.FolderMapper;
import com.want.mapper.GroupMapper;
import com.want.mapper.JobBelongMapper;
import com.want.mapper.JobMapper;
import com.want.mapper.MailMapper;
import com.want.mapper.ScheduleMapper;
import com.want.mapper.TaskMapper;
import com.want.service.job.JobService;
import com.want.util.StringUtil;

@Service
public class JobServiceImpl implements JobService {

	@Autowired
	private GroupMapper groupMapper;

	@Autowired
	private FolderMapper folderMapper;

	@Autowired
	private JobMapper jobMapper;

	@Autowired
	private TaskMapper taskMapper;

	@Autowired
	private JobBelongMapper jobBelongMapper;

	@Autowired
	private MailMapper mailMapper;

	@Autowired
	private ScheduleMapper scheduleMapper;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveGroup(Group group) {
		if (StringUtil.isEmpty(group.getId())) {
			groupMapper.insert(group);
			JobBelong jobBelong = new JobBelong();
			jobBelong.setGroupId(group.getId());
			jobBelong.setFolderId("");
			jobBelong.setJobId("");
			jobBelongMapper.insert(jobBelong);
		} else {
			groupMapper.update(group);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveFolder(Folder folder, String groupId) {
		if (StringUtil.isEmpty(folder.getId())) {
			folderMapper.insert(folder);
			JobBelong jobBelong = new JobBelong();
			jobBelong.setGroupId(groupId);
			jobBelong.setFolderId(folder.getId());
			jobBelong.setJobId("");
			jobBelongMapper.insert(jobBelong);
		} else {
			folderMapper.update(folder);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveJob(Job job, List<Task> tasks, String groupId, String folderId, List<Schedule> schedules) {
		if (StringUtil.isEmpty(job.getId())) {
			jobMapper.insert(job);
		} else {
			jobMapper.update(job);
		}
		taskMapper.deleteByJobId(job.getId());
		if (tasks != null && !tasks.isEmpty()) {
			for (int i = 0; i < tasks.size(); i++) {
				Task t = tasks.get(i);
				t.setJobId(job.getId());
				t.setOrder(String.valueOf(i + 1));
				taskMapper.insert(t);
			}
		}

		jobBelongMapper.deleteByJobId(job.getId());

		JobBelong jobBelong = new JobBelong();
		jobBelong.setGroupId(groupId);
		jobBelong.setFolderId(folderId);
		jobBelong.setJobId(job.getId());
		jobBelongMapper.insert(jobBelong);

		scheduleMapper.deleteByJobId(job.getId());
		if (schedules != null && !schedules.isEmpty()) {
			for (Schedule schedule : schedules) {
				scheduleMapper.insert(schedule);
			}
		}
	}

	@Override
	public void saveJobBelong(JobBelong jobBelong) {
		jobBelongMapper.insert(jobBelong);
	}

	@Override
	public void saveSchedule(Schedule schedule) {
		scheduleMapper.insert(schedule);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveMail(Mail mail) {
		if (StringUtil.isEmpty(mail.getId())) {
			mailMapper.insert(mail);
		} else {
			mailMapper.update(mail);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteGroup(String groupId) {
		List<JobBelong> jobBelongs = jobBelongMapper.findByGroupId(groupId);
		jobBelongs.forEach(new Consumer<JobBelong>() {

			@Override
			public void accept(JobBelong t) {
				if (!StringUtil.isEmpty(t.getGroupId())) {
					groupMapper.delete(t.getGroupId());
				}

				if (!StringUtil.isEmpty(t.getFolderId())) {
					folderMapper.delete(t.getFolderId());
				}

				if (!StringUtil.isEmpty(t.getJobId())) {
					jobMapper.delete(t.getJobId());
					taskMapper.deleteByJobId(t.getJobId());
				}

			}
		});

		jobBelongMapper.deleteByGroupId(groupId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteFolder(String folderId) {
		List<JobBelong> jobBelongs = jobBelongMapper.findByFolderId(folderId);

		jobBelongs.forEach(new Consumer<JobBelong>() {

			@Override
			public void accept(JobBelong t) {
				if (!StringUtil.isEmpty(t.getFolderId())) {
					folderMapper.delete(t.getFolderId());
				}

				if (!StringUtil.isEmpty(t.getJobId())) {
					jobMapper.delete(t.getJobId());
					taskMapper.deleteByJobId(t.getJobId());
				}
			}
		});

		jobBelongMapper.deleteByFolderId(folderId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteJob(String jobId) {
		List<JobBelong> jobBelongs = jobBelongMapper.findByJobId(jobId);

		jobBelongs.forEach(new Consumer<JobBelong>() {

			@Override
			public void accept(JobBelong t) {
				if (!StringUtil.isEmpty(t.getJobId())) {
					jobMapper.delete(t.getJobId());
					taskMapper.deleteByJobId(t.getJobId());
				}
			}
		});

		jobBelongMapper.deleteByJobId(jobId);
	}

	@Override
	public void deleteJobBelong(String groupId) {
		jobBelongMapper.deleteByGroupId(groupId);
	}

	@Override
	public Job getJob(String jobId) {
		return jobMapper.findOne(jobId);
	}

	@Override
	public List<Task> getTasks(String jobId) {
		return taskMapper.findByJobId(jobId);
	}

	@Override
	public List<JobBelongGroup> getJobBelongGroups() {
		return jobBelongMapper.getJobBelongGroups();
	}

	@Override
	public JobBelong getJobBelongByJobId(String jobId) {
		List<JobBelong> results = jobBelongMapper.findByJobId(jobId);
		return results == null || results.isEmpty() ? null : results.get(0);
	}

	@Override
	public List<Schedule> getSchedule(String jobId) {
		return scheduleMapper.findByJobId(jobId);
	}

	@Override
	public List<Mail> getMails() {
		return mailMapper.findAll();
	}

}
