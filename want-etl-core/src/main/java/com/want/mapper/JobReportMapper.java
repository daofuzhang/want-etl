/**
 * -------------------------------------------------------
 * @FileName：ReportMapper.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.want.domain.log.JobReport;

@Repository
@Mapper
public interface JobReportMapper {

	@Select({
			"<script>																															",
			"	SELECT																															",
			"		COUNT(1)																													",
			"	FROM wantETL.job_log T1																											",
			"	LEFT JOIN wantETL.job T2 ON T1.jobId = T2.id																					",
			"	LEFT JOIN wantETL.job_belong T3 ON T1.jobId = T3.jobId																			",
			"	<where>																									    					",
			"    	EXISTS(SELECT 1 FROM wantETL.job_log GROUP BY jobId HAVING T1.jobId = jobId AND T1.id = MAX(id) LIMIT 1) 							",
			"    	<if test=\"startTime != null\"> AND T1.createTime <![CDATA[ >= ]]> #{startTime} </if>										",
			"    	<if test=\"endTime != null\"> AND T1.createTime <![CDATA[ <= ]]> #{endTime} </if>											",
			"    	<if test=\"status != null\"> AND T1.status = #{status} </if>																",
			"    	<if test=\"keyword != null\"> 																								",
			"			<bind name='bindKeyword' value=\"'%' + keyword + '%'\" />																",
			"    		AND (T2.id LIKE #{bindKeyword} OR T2.`name` LIKE #{bindKeyword} OR T2.`memo` LIKE #{bindKeyword})								",
			"    	</if>																														",
			"    	<if test=\"groupId != null\"> AND T3.groupId = #{groupId} </if>																",
			"    	<if test=\"folderId != null\"> AND T3.folderId = #{folderId} </if>															",
			"	</where>																														",
			"</script>																															" })
	long countLatestJobReport(@Param("keyword") String keyword, @Param("startTime") String startTime,
			@Param("endTime") String endTime, @Param("groupId") String groupId, @Param("folderId") String folderId,
			@Param("status") String status);

	@Select({
			"<script>																															",
			"	SELECT																															",
			"		T1.jobId, T1.id AS jobLogId, T2.name, T2.memo, 																						",
			"		IF(T1.startTime IS NULL OR T1.endTime IS NULL, '-', DATE_FORMAT(T1.startTime, '%Y-%m-%d %H:%i:%s')) AS startTime,			",
			"		IF(T1.endTime IS NULL OR T1.endTime IS NULL, '-', DATE_FORMAT(T1.endTime, '%Y-%m-%d %H:%i:%s')) AS endTime,					",
			"    	IF(T1.startTime IS NULL OR T1.endTime IS NULL, '-', SEC_TO_TIME(TIMESTAMPDIFF(SECOND,  T1.startTime, T1.endTime))) duration,",
			"    	T1.`status`																													",
			"	FROM wantETL.job_log T1																											",
			"	LEFT JOIN wantETL.job T2 ON T1.jobId = T2.id																					",
			"	LEFT JOIN wantETL.job_belong T3 ON T1.jobId = T3.jobId																			",
			"	<where>																									    					",
			"    	EXISTS(SELECT 1 FROM wantETL.job_log GROUP BY jobId HAVING T1.jobId = jobId AND T1.id = MAX(id) LIMIT 1) 							",
			"    	<if test=\"startTime != null\"> AND T1.createTime <![CDATA[ >= ]]> #{startTime} </if>										",
			"    	<if test=\"endTime != null\"> AND T1.createTime <![CDATA[ <= ]]> #{endTime} </if>											",
			"    	<if test=\"status != null\"> AND T1.status = #{status} </if>																",
			"    	<if test=\"keyword != null\"> 																								",
			"			<bind name='bindKeyword' value=\"'%' + keyword + '%'\" />																",
			"    		AND (T2.id LIKE #{bindKeyword} OR T2.`name` LIKE #{bindKeyword} OR T2.`memo` LIKE #{bindKeyword})								",
			"    	</if>																														",
			"    	<if test=\"groupId != null\"> AND T3.groupId = #{groupId} </if>																",
			"    	<if test=\"folderId != null\"> AND T3.folderId = #{folderId} </if>															",
			"    	<if test=\"folderId != null\"> AND T3.folderId = #{folderId} </if>															",
			"	</where>																														",
			"	ORDER BY T1.jobId LIMIT #{offset}, #{limit} 																					",
			"</script>																															" })
	List<JobReport> findLatestJobReport(@Param("keyword") String keyword, @Param("startTime") String startTime,
			@Param("endTime") String endTime, @Param("groupId") String groupId, @Param("folderId") String folderId,
			@Param("status") String status, @Param("offset") long offset, @Param("limit") long limit);

	@Select({
			"<script>																															",
			"	SELECT																															",
			"		T1.jobId, T1.id AS jobLogId, 																								",
			"		IF(T1.startTime IS NULL OR T1.endTime IS NULL, '-', DATE_FORMAT(T1.startTime, '%Y-%m-%d %H:%i:%s')) AS startTime,			",
			"		IF(T1.endTime IS NULL OR T1.endTime IS NULL, '-', DATE_FORMAT(T1.endTime, '%Y-%m-%d %H:%i:%s')) AS endTime,					",
			"    	IF(T1.startTime IS NULL OR T1.endTime IS NULL, '-', SEC_TO_TIME(TIMESTAMPDIFF(SECOND,  T1.startTime, T1.endTime))) duration,",
			"    	T1.`status`, T1.updateTime																									",
			"	FROM wantETL.job_log T1																											",
			"	LEFT JOIN wantETL.job T2 ON T1.jobId = T2.id																					",
			"	LEFT JOIN wantETL.job_belong T3 ON T1.jobId = T3.jobId																			",
			"	<where>																									    					",
			"		T1.jobId IN	<if test='jobIds.isEmpty()'> ('') </if>																			",
			"   	<foreach item='item' index='index' collection='jobIds' open='(' separator=',' close=')'> #{item} </foreach>					",
			"    	<if test=\"startTime != null\"> AND T1.createTime <![CDATA[ >= ]]> #{startTime} </if>										",
			"    	<if test=\"endTime != null\"> AND T1.createTime <![CDATA[ <= ]]> #{endTime} </if>											",
			"	</where>																														",
			"	ORDER BY T1.jobId																												",
			"</script>																															" })
	List<JobReport> findByJobIds(@Param("jobIds") List<String> jobIds, @Param("startTime") String startTime,
			@Param("endTime") String endTime);

}
