/**
 * -------------------------------------------------------
 * @FileName：AccountMapper.java
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
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.want.domain.account.Account;

/**
 * @author 80005151
 *
 */
@Repository
@Mapper
public interface AccountMapper {
	@Insert("INSERT INTO `account`(id, name, password, roleId, enable, mailId) VALUES (#{id}, #{name}, #{password}, #{roleId}, #{enable}, #{mailId}) ON DUPLICATE KEY UPDATE name = #{name}, password = #{password}, roleId = #{roleId}, enable = #{enable}, mailId = #{mailId}")
	void insert(Account account);

	@Select("SELECT * FROM `account` WHERE id = #{id}")
	Account findOne(String id);

	@Select("SELECT * FROM `account`")
	List<Account> findAll();

	@Select("SELECT count(1) FROM `account`")
	long getCount();

	@Select("SELECT  t1.*, t2.mail FROM `account` t1 LEFT JOIN mail t2 ON t1.mailId = t2.id limit #{offset}, #{limit}")
	List<Account> findAllWithEmail(@Param("offset") int offset, @Param("limit") int limit);

	@Delete("DELETE FROM `account` WHERE id = #{id}")
	void delete(String id);

	@Update("UPDATE `account` SET name = #{name}, password = #{password}, roleId = #{roleId}, enable = #{enable}, mailId = #{mailId} WHERE id = #{id}")
	void update(Account account);
}
