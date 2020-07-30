/**
 * -------------------------------------------------------
 * @FileName：JobWorkerMessageMapper.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.want.domain.log.JobWorkerMessage;

@Repository
@Mapper
public interface JobWorkerMessageMapper {

	@Insert("INSERT INTO `job_worker_message`(jobLogId, type, message, createTime) "
			+ "VALUES (#{jobLogId}, #{type}, #{message}, NOW())")
	void insert(JobWorkerMessage jobWorkerMessage);
	
	@Select("SELECT * FROM `job_worker_message` WHERE jobLogId = #{jobLogId}")
	List<JobWorkerMessage> findByJobLogId(String jobLogId);
	
	@Select("SELECT !EXISTS(SELECT 1 FROM wantETL.job_worker_message WHERE jobLogId = #{jobLogId} AND `type` = '1')")
	boolean isSuccess(String jobLogId);

}
