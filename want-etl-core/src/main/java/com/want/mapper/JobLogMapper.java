/**
 * -------------------------------------------------------
 * @FileName：LogMapper.java
 * @Description：简要描述本文件的内容
 * @Author：Dirk.Lee
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.want.domain.log.JobLog;

/**
 * @author 80005151
 *
 */
@Repository
@Mapper
public interface JobLogMapper {
	
	@SelectKey(statement = "SELECT CONCAT(DATE_FORMAT(NOW(), '%Y%m%d'), 'LOG', uuid_short())", keyProperty = "id", before = true, resultType = String.class)
	@Insert("INSERT INTO `job_log`(id, jobId, agentId, pid, parameter, status, startTime, endTime, createTime, updateTime) "
			+ "VALUES (#{id}, #{jobId}, #{agentId}, #{pid}, #{parameter}, #{status}, #{startTime}, #{endTime}, NOW(), NOW())")
	void insert(JobLog log);

	@Select("SELECT * FROM `job_log`")
	List<JobLog> findAll();

	@Select("SELECT * FROM `job_log` WHERE id = #{id}")
	JobLog findOne(String id);

	@Delete("DELETE FROM `job_log` WHERE id = #{id}")
	void delete(String id);

	@Update("UPDATE `job_log` SET jobId = #{jobId}, agentId = #{agentId}, pid = #{pid}, parameter = #{parameter}, status = #{status}, startTime = #{startTime}, endTime = #{endTime}, updateTime = NOW() WHERE id = #{id}")
	void update(JobLog log);

	@Select("SELECT GROUP_CONCAT(id) FROM `job_log`")
	String findAllJobLogIds();

	@Select("SELECT EXISTS( SELECT 1 FROM wantETL.job_log T1 INNER JOIN wantETL.agent T2 ON T1.agentId = T2.id "
			+ "AND (T1.`status` = 'running' AND T2.updateTime > DATE_SUB(NOW(), INTERVAL 2 SECOND) OR T1.`status` = 'wait') WHERE T1.jobId = #{jobId})")
	boolean isJobRunning(String jobId);

}
