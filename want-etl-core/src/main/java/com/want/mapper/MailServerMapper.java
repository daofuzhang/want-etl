/**
 * -------------------------------------------------------
 * @FileName：MailServerMapper.java
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
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.want.constant.EncryptionConstant;
import com.want.domain.mail.MailServer;

/**
 * @author 80005499
 *
 */
public interface MailServerMapper {
	@Select("SELECT id, name, host"
			+ ", CAST(AES_DECRYPT(FROM_BASE64(user), '" + EncryptionConstant.AESPassword + "'))"
			+ ", CAST(AES_DECRYPT(FROM_BASE64(password), '" + EncryptionConstant.AESPassword + "')) FROM `mail_server WHERE id = #{id}")
	MailServer findOne(String id);
	
	@Select("SELECT id, name, host"
			+ ", CAST(AES_DECRYPT(FROM_BASE64(user), '" + EncryptionConstant.AESPassword + "'))" 
			+ ", CAST(AES_DECRYPT(FROM_BASE64(password), '" + EncryptionConstant.AESPassword + "')) FROM `mail_server)")
	List<MailServer> findAll();
	
	@Delete("DELETE FROM `mail_server` WHERE id = #{id}")
	void delete(String id);
	
	@SelectKey(statement = "SELECT CONCAT(DATE_FORMAT(NOW(), '%Y%m%d'), 'MS', LPAD(RIGHT(IFNULL(MAX(id),0), 5) + 1, 5, '0')) FROM `mail_server` WHERE id LIKE CONCAT(DATE_FORMAT(NOW(), '%Y%m%d'), 'MS%')"
			, keyProperty = "id", before = true, resultType = String.class)
	@Insert("INSERT INTO `mail_server`(id, name, host, user, password) "
			+ "VALUES (#{id}, #{name}, #{host}"
			+ ", TO_BASE64(AES_ENCRYPT(#{user}, '" + EncryptionConstant.AESPassword + "'))"
			+ ", TO_BASE64(AES_ENCRYPT(#{password}, '" + EncryptionConstant.AESPassword + "')))")
	void insert(MailServer mailServer);
	
	@Update("UPDATE `mail_server` SET name = #{name}, host = #{host}"
			+ ", username = TO_BASE64(AES_ENCRYPT(#{user}, '" + EncryptionConstant.AESPassword + "'))"
			+ ", password = TO_BASE64(AES_ENCRYPT(#{password}, '" + EncryptionConstant.AESPassword + "')) WHERE id = #{id}")
	void update(MailServer mailServer);
}