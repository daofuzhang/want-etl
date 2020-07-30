/**
 * -------------------------------------------------------
 * @FileName：JDBCServerMapper.java
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
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.want.constant.EncryptionConstant;
import com.want.domain.server.JDBCServer;

/**
 * @author 80005499
 *
 */
@Repository
@Mapper
public interface JDBCServerMapper {
	@SelectKey(statement = "SELECT CONCAT(DATE_FORMAT(NOW(), '%Y%m%d'), 'JDBC', LPAD(RIGHT(IFNULL(MAX(id),0), 5) + 1, 5, '0')) FROM `jdbc_server` WHERE id LIKE CONCAT(DATE_FORMAT(NOW(), '%Y%m%d'), 'JDBC%')", keyProperty = "id", before = true, resultType = String.class)
	@Insert("INSERT INTO `jdbc_server`(id, name, type, url, username, password) "
			+ "VALUES (#{id}, #{name}, #{type}, #{url}" + ", TO_BASE64(AES_ENCRYPT(#{userName}, '"
			+ EncryptionConstant.AESPassword + "'))" + ", TO_BASE64(AES_ENCRYPT(#{password}, '"
			+ EncryptionConstant.AESPassword + "')))")
	void insert(JDBCServer jdbcServer);

	@Select("SELECT id, name, type, url" + ", CAST(AES_DECRYPT(FROM_BASE64(username), '"
			+ EncryptionConstant.AESPassword + "') as char) AS username" + ", CAST(AES_DECRYPT(FROM_BASE64(password), '"
			+ EncryptionConstant.AESPassword + "') as char) AS password FROM `jdbc_server` WHERE id = #{id}")
	JDBCServer findOne(String id);

	@Select({
			"<script>																										",
			"	select																										",
			"		id, name, type, url,																							",
			"		CAST(AES_DECRYPT(FROM_BASE64(username), '" + EncryptionConstant.AESPassword
					+ "') as char) AS username, ",
			"		CAST(AES_DECRYPT(FROM_BASE64(password), '" + EncryptionConstant.AESPassword
					+ "') as char) AS password  ",
			"	from `jdbc_server`																							",
			"	<where>																									    ",
			"    	<if test=\"ids.size() > 0\">																		",
			"			id in																							",
			"			<foreach collection='ids' item='id' open='(' separator=',' close=')'>								",
			"				#{id}																							",
			"			</foreach>																							",
			"    	</if>																									",
			"	</where>																									",
			"</script>" })
	List<JDBCServer> findByIds(@Param("ids") List<String> ids);

	@Select("SELECT id, name, type, url" + ", CAST(AES_DECRYPT(FROM_BASE64(username), '"
			+ EncryptionConstant.AESPassword + "') as char) AS username" + ", CAST(AES_DECRYPT(FROM_BASE64(password), '"
			+ EncryptionConstant.AESPassword + "') as char) AS password FROM `jdbc_server` WHERE type = 'oracle'")
	List<JDBCServer> findOracles();

	@Select("SELECT id, name, type, url" + ", CAST(AES_DECRYPT(FROM_BASE64(username), '"
			+ EncryptionConstant.AESPassword + "') as char) AS username" + ", CAST(AES_DECRYPT(FROM_BASE64(password), '"
			+ EncryptionConstant.AESPassword + "') as char) AS password FROM `jdbc_server`")
	List<JDBCServer> findAll();

	@Delete("DELETE FROM `jdbc_server` WHERE id = #{id}")
	void delete(String id);

	@Update("UPDATE `jdbc_server` SET name = #{name}, type = #{type}, url = #{url}"
			+ ", username = TO_BASE64(AES_ENCRYPT(#{userName}, '" + EncryptionConstant.AESPassword + "'))"
			+ ", password = TO_BASE64(AES_ENCRYPT(#{password}, '" + EncryptionConstant.AESPassword
			+ "')) WHERE id = #{id}")
	void update(JDBCServer jdbcServer);
}