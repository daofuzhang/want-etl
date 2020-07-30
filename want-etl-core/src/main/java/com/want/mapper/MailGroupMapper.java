/**
 * -------------------------------------------------------
 * @FileName：MailGroupMapper.java
 * @Description：简要描述本文件的内容
 * @Author：Jerry.Chen
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.want.domain.mail.MailGroup;

/**
 * @author 80005499
 *
 */
public interface MailGroupMapper {
	@SelectKey(statement = "SELECT CONCAT(DATE_FORMAT(NOW(), '%Y%m%d'), 'MG', LPAD(RIGHT(IFNULL(MAX(id),0), 5) + 1, 5, '0')) FROM `mail_group` WHERE id LIKE CONCAT(DATE_FORMAT(NOW(), '%Y%m%d'), 'MG%')", keyProperty = "id", before = true, resultType = String.class)
	@Insert("INSERT INTO `mail_group`(id, name, description, mailServerId, toMailIds, ccMailIds) VALUES (#{id}, #{name}, #{description}, #{mailServerId}, #{toMailIds}, #{ccMailIds})")
	void insert(MailGroup mailGroup);

	@Select("SELECT * FROM `mail_group`")
	List<MailGroup> findAll();

	@Select("SELECT * FROM `mail_group` limit #{offset}, #{limit}")
	List<MailGroup> findAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

	@Select("SELECT * FROM `mail_group` WHERE id = #{id}")
	MailGroup findOne(String id);
	
	@Select("SELECT count(1) FROM `mail_group`")
	long getCount();

	@Delete("DELETE FROM `mail_group` WHERE id = #{id}")
	void delete(String id);

	@Update("UPDATE `mail_group` SET name = #{name}, description = #{description}, mailServerId = #{mailServerId}, toMailIds = #{toMailIds}, ccMailIds = #{ccMailIds} WHERE id = #{id}")
	void update(MailGroup mailGroup);

	@Update("UPDATE `mail_group` SET toMailIds = #{toMailIds} WHERE id = #{id}")
	void updateToMailIds(MailGroup mailGroup);
}