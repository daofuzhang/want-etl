/**
 * -------------------------------------------------------
 * @FileName：TaskMapper.java
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

import com.want.domain.job.Task;

/**
 * @author 80005151
 *
 */
@Repository
@Mapper
public interface TaskMapper {
	@SelectKey(statement = "SELECT CONCAT(DATE_FORMAT(NOW(), '%Y%m%d'), 'TASK', LPAD(RIGHT(IFNULL(MAX(id),0), 5) + 1, 5, '0')) FROM `task` WHERE id LIKE CONCAT(DATE_FORMAT(NOW(), '%Y%m%d'), 'TASK%')", keyProperty = "id", before = true, resultType = String.class)
	@Insert("INSERT INTO `task`( id, jobId, `type`, `order`, statement, sourceServerId, functionServerId, `function`, importForm, targetServerId, `database`, `table`, `condition`, coreSize, rules, memo) "
			+ "VALUES (#{id}, #{jobId}, #{type}, #{order}, #{statement}, #{sourceServerId}, #{functionServerId}, #{function}, #{importForm}, #{targetServerId}, #{database}, #{table}, #{condition}, #{coreSize}, #{rules}, #{memo})")
	void insert(Task task);

	@Select("SELECT * FROM `task`")
	List<Task> findAll();

	@Select("SELECT * FROM `task` WHERE id = #{id}")
	Task findOne(String id);

	@Select("SELECT * FROM `task` WHERE jobId = #{jobId}")
	List<Task> findByJobId(String jobId);

	@Delete("DELETE FROM `task` WHERE id = #{id}")
	void delete(String id);

	@Delete("DELETE FROM `task` WHERE jobId = #{jobId}")
	void deleteByJobId(String jobId);

	@Update("UPDATE `task` SET jobId = #{jobId}, type = #{type}, order = #{order}, statement = #{statement}, sourceServerId = #{sourceServerId}, functionServerId = #{functionServerId}, function = #{function}, importForm = #{importForm}, targetServerId = #{targetServerId}, database = #{database}, table = #{table}, condition = #{condition}, coreSize = #{coreSize}, rules = #{rules} WHERE id = #{id}")
	void update(Task task);
}
