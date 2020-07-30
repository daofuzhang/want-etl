/**
 * -------------------------------------------------------
 * @FileName：ScheduleMapper.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
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
import org.springframework.stereotype.Repository;

import com.want.domain.schedule.Schedule;

@Repository
@Mapper
public interface ScheduleMapper {

	@SelectKey(statement = "SELECT CONCAT(DATE_FORMAT(NOW(), '%Y%m%d'), 'SC', LPAD(RIGHT(IFNULL(MAX(id),0), 5) + 1, 5, '0')) FROM `schedule` WHERE id LIKE CONCAT(DATE_FORMAT(NOW(), '%Y%m%d'), 'SC%')", keyProperty = "id", before = true, resultType = String.class)
	@Insert("INSERT INTO `schedule`(id, expression, enable, priority, jobId) "
			+ "VALUES(#{id}, #{expression}, #{enable}, #{priority}, #{jobId})")
	void insert(Schedule schedule);

	@Select("SELECT * FROM `schedule` WHERE id = #{id}")
	Schedule findOne(String id);

	@Select("SELECT * FROM `schedule` WHERE jobId = #{jobId}")
	List<Schedule> findByJobId(String jobId);

	@Select("SELECT * FROM `schedule`")
	List<Schedule> findAll();

	@Delete("DELETE FROM `schedule` WHERE jobId = #{jobId}")
	void deleteByJobId(String jobId);

}
