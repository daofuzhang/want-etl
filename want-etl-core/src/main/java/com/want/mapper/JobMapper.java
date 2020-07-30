/**
 * -------------------------------------------------------
 * @FileName：JobMapper.java
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

import com.want.domain.job.Job;

/**
 * @author 80005151
 *
 */
@Repository
@Mapper
public interface JobMapper {
	@SelectKey(statement = "SELECT CONCAT(DATE_FORMAT(NOW(), '%Y%m%d'), 'JOB', LPAD(RIGHT(IFNULL(MAX(id),0), 5) + 1, 5, '0')) FROM `job` WHERE id LIKE CONCAT(DATE_FORMAT(NOW(), '%Y%m%d'), 'JOB%')", keyProperty = "id", before = true, resultType = String.class)
	@Insert("INSERT INTO `job`(id, name, strategy, mailGroupIds, memo, dynamics, batchFuncId, sendBatchLogger) VALUES (#{id}, #{name}, #{strategy}, #{mailGroupIds}, #{memo}, #{dynamics}, #{batchFuncId}, #{sendBatchLogger})")
	void insert(Job job);

	@Select("SELECT * FROM `job`")
	List<Job> findAll();

	@Select("SELECT * FROM `job` WHERE id = #{id}")
	Job findOne(String id);

	@Select("SELECT * FROM `job` WHERE id = #{id}")
	List<Job> findByFolderId(String id);

	@Delete("DELETE FROM `job` WHERE id = #{id}")
	void delete(String id);

	@Update("UPDATE `job` SET name = #{name}, strategy = #{strategy}, mailGroupIds = #{mailGroupIds}, memo = #{memo}, dynamics = #{dynamics}, batchFuncId = #{batchFuncId}, sendBatchLogger = #{sendBatchLogger} WHERE id = #{id}")
	void update(Job job);

	@Select("SELECT GROUP_CONCAT(id) FROM `job`")
	String findAllJobIds();
}
