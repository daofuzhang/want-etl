/**
 * -------------------------------------------------------
 * @FileName：JobService.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.service.job;

import java.util.List;

import com.want.domain.job.Folder;
import com.want.domain.job.Group;
import com.want.domain.job.Job;
import com.want.domain.job.JobBelong;
import com.want.domain.job.JobBelongGroup;
import com.want.domain.job.Task;
import com.want.domain.mail.Mail;
import com.want.domain.schedule.Schedule;

public interface JobService {

	public void saveGroup(Group group);

	public void saveFolder(Folder folder, String groupId);

	public void saveJob(Job job, List<Task> tasks, String groupId, String folderId, List<Schedule> schedules);

	public void saveJobBelong(JobBelong jobBelong);
	
	public void saveSchedule(Schedule schedule);

	public void saveMail(Mail mail);

	public void deleteGroup(String groupId);

	public void deleteFolder(String folderId);

	public void deleteJob(String jobId);

	public void deleteJobBelong(String groupId);
	
	public Job getJob(String jobId);
	
	public List<Schedule> getSchedule(String jobId);

	public List<Task> getTasks(String jobId);

	public List<JobBelongGroup> getJobBelongGroups();
	
	public JobBelong getJobBelongByJobId(String jobId);
	
	public List<Mail> getMails();

}
