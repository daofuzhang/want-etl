/**
 * -------------------------------------------------------
 * @FileName：GroupMapper.java
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

import com.want.domain.job.Group;

/**
 * @author 80005151
 *
 */
@Repository
@Mapper
public interface GroupMapper {
	@SelectKey(statement = "SELECT CONCAT(DATE_FORMAT(NOW(), '%Y%m%d'), 'GROUP', LPAD(RIGHT(IFNULL(MAX(id),0), 5) + 1, 5, '0')) FROM `group` WHERE id LIKE CONCAT(DATE_FORMAT(NOW(), '%Y%m%d'), 'GROUP%')", keyProperty = "id", before = true, resultType = String.class)
	@Insert("INSERT INTO `group`(id, name, mailGroupIds, memo) VALUES (#{id}, #{name}, #{mailGroupIds}, #{memo})")
	void insert(Group group);

	@Select("SELECT * FROM `group`")
	List<Group> findAll();

	@Select("SELECT * FROM `group` WHERE id = #{id}")
	Group findOne(String id);

	@Delete("DELETE FROM `group` WHERE id = #{id}")
	void delete(String id);

	@Update("UPDATE `group` SET name = #{name}, mailGroupIds = #{mailGroupIds}, memo = #{memo} WHERE id = #{id}")
	void update(Group group);

	@Select("SELECT GROUP_CONCAT(id) FROM `group`")
	String findAllGroupIds();
	
	
}
