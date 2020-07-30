/**
 * -------------------------------------------------------
 * @FileName：FolderMapper.java
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

import com.want.domain.job.Folder;

/**
 * @author 80005151
 *
 */
@Repository
@Mapper
public interface FolderMapper {
	@SelectKey(statement = "SELECT CONCAT(DATE_FORMAT(NOW(), '%Y%m%d'), 'FOLDER', LPAD(RIGHT(IFNULL(MAX(id),0), 5) + 1, 5, '0')) FROM `folder` WHERE id LIKE CONCAT(DATE_FORMAT(NOW(), '%Y%m%d'), 'FOLDER%')", keyProperty = "id", before = true, resultType = String.class)
	@Insert("INSERT INTO `folder`(id, name, mailGroupIds, memo) VALUES (#{id}, #{name}, #{mailGroupIds}, #{memo})")
	void insert(Folder folder);

	@Select("SELECT * FROM `folder`")
	List<Folder> findAll();

	@Select("SELECT * FROM `folder` WHERE id = #{id}")
	Folder findOne(String id);
	
	@Select("SELECT * FROM `folder` WHERE id = #{id}")
	List<Folder> findByGroupId(String id);

	@Delete("DELETE FROM `folder` WHERE id = #{id}")
	void delete(String id);

	@Update("UPDATE `folder` SET name = #{name}, mailGroupIds = #{mailGroupIds}, memo = #{memo} WHERE id = #{id}")
	void update(Folder folder);

	@Select("SELECT GROUP_CONCAT(id) FROM `folder`")
	String findAllFolderIds();
}
