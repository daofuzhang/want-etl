/**
 * -------------------------------------------------------
 * @FileName：MailMapper.java
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

import com.want.domain.mail.Mail;

/**
 * @author 80005499
 *
 */
public interface MailMapper {
	@SelectKey(statement = "SELECT CONCAT(DATE_FORMAT(NOW(), '%Y%m%d'), 'MAIL', LPAD(RIGHT(IFNULL(MAX(id),0), 5) + 1, 5, '0')) FROM `mail` WHERE id LIKE CONCAT(DATE_FORMAT(NOW(), '%Y%m%d'), 'MAIL%')", keyProperty = "id", before = true, resultType = String.class)
	@Insert("INSERT INTO `mail`(id, name, mail) VALUES (#{id}, #{name}, #{mail})")
	void insert(Mail mail);

	@Select("SELECT * FROM `mail` WHERE id = #{id}")
	Mail findOne(String id);

	@Select("SELECT * FROM `mail` WHERE name = #{name}")
	Mail findByName(String name);

	@Select("SELECT * FROM `mail`")
	List<Mail> findAll();

	@Select("<script> 							"
			+ "SELECT * FROM `mail` where id in 																		"
			+ "	<foreach collection='ids' index='index' item='id' open='(' separator=',' close=')'> #{id} </foreach> "
			+ "</script>	")
	List<Mail> findByIds(@Param("ids") List<String> ids);

	@Delete("DELETE FROM `mail` WHERE id = #{id}")
	void delete(String id);

	@Update("UPDATE `mail` SET name = #{name}, mail = #{mail} WHERE id = #{id}")
	void update(Mail mail);
}