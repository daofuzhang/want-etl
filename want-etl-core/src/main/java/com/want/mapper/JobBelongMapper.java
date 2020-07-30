/**
 * -------------------------------------------------------
 * @FileName：JobBelongMapper.java
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
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.want.domain.job.JobBelong;
import com.want.domain.job.JobBelongGroup;

/**
 * @author 80005151
 *
 */
@Repository
@Mapper
public interface JobBelongMapper {
	@Insert("INSERT INTO `job_belong`(jobId, groupId, folderId) VALUES (#{jobId}, #{groupId}, #{folderId})")
	void insert(JobBelong jobBelong);

	@Select("SELECT * FROM `job_belong`")
	List<JobBelong> findAll();

	@Select("SELECT * FROM `job_belong` WHERE groupId = #{groupId}")
	List<JobBelong> findByGroupId(String groupId);

	@Select("SELECT * FROM `job_belong` WHERE folderId = #{folderId}")
	List<JobBelong> findByFolderId(String folderId);

	@Select("SELECT * FROM `job_belong` WHERE jobId = #{jobId}")
	List<JobBelong> findByJobId(String jobId);

	@Delete("DELETE FROM `job_belong` WHERE groupId = #{groupId}")
	void deleteByGroupId(String groupId);

	@Delete("DELETE FROM `job_belong` WHERE folderId = #{folderId}")
	void deleteByFolderId(String folderId);

	@Delete("DELETE FROM `job_belong` WHERE jobId = #{jobId}")
	void deleteByJobId(String jobId);
	
	@Delete("DELETE FROM `job_belong` WHERE jobId = #{jobId} and groupId = #{groupId} and folderId = #{folderId}  ")
	void delete(JobBelong jobBelong);

	@Update("UPDATE `job_belong` SET jobId = #{jobId}, groupId = #{groupId}, folderId = #{folderId} WHERE id = #{id}")
	void update(JobBelong jobBelong);

	@Select("SELECT GROUP_CONCAT(id) FROM `job_belong`")
	String findAllFolderIds();

	@Select("SELECT \n" + "    t1.*,\n" + "    t2.name groupName,\n" + "    t2.memo groupMemo,\n"
			+ "    t2.mailGroupIds groupMailGroupIds,\n" + "    t3.name folderName,\n" + "    t3.memo folderMemo,\n"
			+ "    t3.mailGroupIds folderMailGroupIds,\n" + "    t4.name jobName,\n" + "    t4.memo jobMemo,\n"
			+ "    t4.mailGroupIds jobMailGroupIds,\n" + "    t4.strategy,\n" + "    t5.id expressionId,\n"
			+ "    t5.expression\n" + "FROM\n" + "    wantETL.job_belong t1\n" + "        LEFT JOIN\n"
			+ "    wantETL.`group` t2 ON t1.groupId = t2.id\n" + "        LEFT JOIN\n"
			+ "    wantETL.`folder` t3 ON t1.folderId = t3.id\n" + "        LEFT JOIN\n"
			+ "    wantETL.`job` t4 ON t1.jobId = t4.id\n" + "        LEFT JOIN\n"
			+ "    wantETL.`schedule` t5 ON t1.jobId = t5.jobId;")
	List<JobBelongGroup> getJobBelongGroups();
}
