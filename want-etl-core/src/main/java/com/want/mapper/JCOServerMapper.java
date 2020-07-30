/**
 * -------------------------------------------------------
 * @FileName：JcoServerMapper.java
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
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.want.constant.EncryptionConstant;
import com.want.domain.server.JCOServer;

/**
 * @author 80005151
 *
 */
@Repository
@Mapper
public interface JCOServerMapper {
	@SelectKey(statement = "SELECT CONCAT(DATE_FORMAT(NOW(), '%Y%m%d'), 'JCO', LPAD(RIGHT(IFNULL(MAX(id),0), 5) + 1, 5, '0')) FROM `jco_server` WHERE id LIKE CONCAT(DATE_FORMAT(NOW(), '%Y%m%d'), 'JCO%')", keyProperty = "id", before = true, resultType = String.class)
	@Insert("INSERT INTO `jco_server`(id, name, lang, passwd, sysnr, client, poolCapacity, user, peakLimit, ashost) "
			+ "VALUES(#{id}, #{name}, #{lang}, TO_BASE64(AES_ENCRYPT(#{passwd}, '"
			+ EncryptionConstant.AESPassword
			+ "')), #{sysnr}, #{client}, #{poolCapacity}, TO_BASE64(AES_ENCRYPT(#{user}, '"
			+ EncryptionConstant.AESPassword + "')), #{peakLimit}, #{ashost})")
	void insert(JCOServer jcoServer);

	@Select("SELECT id, name, lang" + ", CAST(AES_DECRYPT(FROM_BASE64(passwd), '"
			+ EncryptionConstant.AESPassword + "') as char) AS passwd, sysnr, client, poolCapacity"
			+ ", CAST(AES_DECRYPT(FROM_BASE64(user), '" + EncryptionConstant.AESPassword
			+ "') as char) AS user, peakLimit, ashost FROM `jco_server` WHERE id = #{id}")
	JCOServer find(String id);
	
	@Select({
		"<script>																										",
		"	select																										",
		"		id, name, lang,																							",
		"		CAST(AES_DECRYPT(FROM_BASE64(passwd), '" + EncryptionConstant.AESPassword + "') as char) AS passwd, 	",
		"		sysnr, client, poolCapacity,																			",	
		"		CAST(AES_DECRYPT(FROM_BASE64(user), '" + EncryptionConstant.AESPassword + "') as char) AS user,  		",	
		"		peakLimit, ashost																						",
		"	from `jco_server`																							",
		"	<where>																									    ",
		"    	<if test=\"ids.size() > 0\">																		",
		"			id in																							",
		"			<foreach collection='ids' item='id' open='(' separator=',' close=')'>								",
		"				#{id}																							",
		"			</foreach>																							",
		"    	</if>																									",
		"	</where>																									",
		"</script>" })
	List<JCOServer> findByIds(@Param("ids")List<String> ids);

	@Select("SELECT id, name, lang" + ", CAST(AES_DECRYPT(FROM_BASE64(passwd), '"
			+ EncryptionConstant.AESPassword + "') as char) AS passwd, sysnr, client, poolCapacity"
			+ ", CAST(AES_DECRYPT(FROM_BASE64(user), '" + EncryptionConstant.AESPassword
			+ "') as char) AS user, peakLimit, ashost FROM `jco_server`")
	List<JCOServer> findAll();

	@Delete("DELETE FROM `jco_server` WHERE id = #{id}")
	void delete(String id);

	@Update("UPDATE `jco_server` SET name = #{name}, lang = #{lang}"
			+ ", passwd = TO_BASE64(AES_ENCRYPT(#{passwd}, '" + EncryptionConstant.AESPassword + "'))"
			+ ",sysnr = #{sysnr} ,client = #{client} ,poolCapacity = #{poolCapacity} , user = TO_BASE64(AES_ENCRYPT(#{user}, '"
			+ EncryptionConstant.AESPassword + "')), peakLimit = #{peakLimit}, ashost = #{ashost} WHERE id = #{id}")
	void update(JCOServer jcoServer);
}
