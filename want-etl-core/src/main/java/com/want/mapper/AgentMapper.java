/**
 * -------------------------------------------------------
 * @FileName：AgnetMapper.java
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

import com.want.domain.server.Agent;

/**
 * @author 80005151
 *
 */
@Repository
@Mapper
public interface AgentMapper {
	@SelectKey(statement = "SELECT CONCAT(DATE_FORMAT(NOW(), '%Y%m%d'), 'AGNET', LPAD(RIGHT(IFNULL(MAX(id),0), 5) + 1, 5, '0')) FROM `agent` WHERE id LIKE CONCAT(DATE_FORMAT(NOW(), '%Y%m%d'), 'AGNET%')", keyProperty = "id", before = true, resultType = String.class)
	@Insert("INSERT INTO `agent`(id, name, host, active, maxActive, updateTime) " + "VALUES (#{id}, #{name}, #{host}, #{active}, #{maxActive}, #{updateTime})")
	void insert(Agent agent);
	
	@Insert("REPLACE INTO `agent`(id, name, host, active, maxActive, updateTime) VALUES (#{id}, #{name}, #{host}, #{active}, #{maxActive}, #{updateTime})")
	void replace(Agent agent);

	@Select("SELECT * FROM `agent`")
	List<Agent> findAll();

	@Select("SELECT * FROM `agent` WHERE id = #{id}")
	Agent findOne(String id);

	@Delete("DELETE FROM `agent` WHERE id = #{id}")
	void delete(String id);

	@Update("UPDATE `agent` SET name = #{name}, host = #{host}, active = #{active}, maxActive = #{maxActive}, updateTime = #{updateTime} WHERE id = #{id}")
	void update(Agent agent);
	
	@Select("SELECT * FROM wantETL.agent WHERE `active` < maxActive AND updateTime > DATE_SUB(NOW(), INTERVAL 2 SECOND) ORDER BY `active` ASC")
	List<Agent> findIdles();
	
	@Select("SELECT * FROM wantETL.agent WHERE updateTime > DATE_SUB(NOW(), INTERVAL 2 SECOND) ORDER BY id ASC;")
	List<Agent> findAlives();


}
