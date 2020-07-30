/**
 * -------------------------------------------------------
 * @FileName：BatchLoggerMapper.java
 * @Description：简要描述本文件的内容
 * @Author：Dirk.Lee
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.want.domain.batchlogger.BatchLogger;

/**
 * @author 80005151
 *
 */
public interface BatchLoggerMapper {
	@Insert("INSERT INTO `batch_logger`(id) VALUES (#{id}) ON DUPLICATE KEY UPDATE id = #{id}")
	void insert(BatchLogger batchLogger);

	@Select("SELECT * FROM `batch_logger` limit 1")
	BatchLogger findOne();

	@Delete("DELETE FROM `batch_logger`")
	void deleteAll();
}
