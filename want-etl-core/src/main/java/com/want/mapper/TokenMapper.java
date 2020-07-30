/**
 * -------------------------------------------------------
 * @FileName：TokenMapper.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.mapper;

import java.sql.Timestamp;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.want.domain.token.Token;

@Mapper
public interface TokenMapper {

	@SelectKey(statement = "SELECT uuid()", keyProperty = "id", before = true, resultType = String.class)
	@Insert("INSERT INTO `token`(id, accountId, expiredTime, createTime)VALUES(#{id}, #{accountId}, #{expiredTime}, #{createTime})")
	void insert(Token token);

	@Select("SELECT * FROM `token` WHERE id = #{id}")
	Token findOne(@Param("id") String id);

	@Update("UPDATE `token` SET expiredTime = #{expiredTime} WHERE id = #{id}")
	void updateExpiredTime(@Param("id") String id, @Param("expiredTime") Timestamp expiredTime);

}
