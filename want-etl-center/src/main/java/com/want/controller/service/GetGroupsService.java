/**
 * -------------------------------------------------------
 * @FileName：JobBelongService.java
 * @Description：简要描述本文件的内容
 * @Author：Dirk.Lee
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.controller.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.want.base.service.ResponseDataService;
import com.want.base.service.dto.InputArgumentDTO;
import com.want.controller.service.dto.FolderDTO;
import com.want.controller.service.dto.GroupDTO;
import com.want.controller.service.dto.JobDTO;
import com.want.domain.job.JobBelongGroup;
import com.want.service.job.JobService;
import com.want.util.StringUtil;

/**
 * @author 80005151
 *
 */
@Service
public class GetGroupsService extends ResponseDataService<String, String, List<GroupDTO>> {

	@Autowired
	private JobService jobService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.want.base.service.BaseService#checkParameters(java.lang.Object)
	 */
	@Override
	protected void checkParameters(String parameters) throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.want.base.service.BaseService#parseParameters(com.want.base.service.dto.
	 * InputArgumentDTO, java.lang.Object)
	 */
	@Override
	protected String parseParameters(InputArgumentDTO argument, String parameters) throws Exception {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.want.base.service.BaseService#dataAccess(com.want.base.service.dto.
	 * InputArgumentDTO, java.lang.Object)
	 */
	@Override
	protected List<GroupDTO> dataAccess(InputArgumentDTO argument, String parameters) throws Exception {
		List<GroupDTO> resultList = new ArrayList<GroupDTO>();
		List<JobBelongGroup> jobBelongGroups = jobService.getJobBelongGroups();
		Map<String, List<JobBelongGroup>> groupMap = jobBelongGroups.stream()
				.collect(Collectors.groupingBy(JobBelongGroup::getGroupId));

		groupMap.values().forEach(new Consumer<List<JobBelongGroup>>() {

			@Override
			public void accept(List<JobBelongGroup> t) {
				GroupDTO groupDTO = new GroupDTO();
				resultList.add(groupDTO);
				groupDTO.setId(t.get(0).getGroupId());
				groupDTO.setName(t.get(0).getGroupName());
				groupDTO.setMemo(t.get(0).getGroupMemo());
				groupDTO.setMailGroupIds(
						t.get(0).getGroupMailGroupIds() == null || StringUtil.isEmpty(t.get(0).getGroupMailGroupIds())
								? new ArrayList<>()
								: Arrays.asList(t.get(0).getGroupMailGroupIds().split(",")));
				groupDTO.setFolders(new ArrayList<>());
				Map<String, List<JobBelongGroup>> folderMap = t.stream()
						.sorted(Comparator.comparing(JobBelongGroup::getFolderId))
						.collect(Collectors.groupingBy(JobBelongGroup::getFolderId));
				folderMap.values().forEach(new Consumer<List<JobBelongGroup>>() {

					@Override
					public void accept(List<JobBelongGroup> t) {
						FolderDTO folderDTO = new FolderDTO();
						if (!StringUtil.isEmpty(t.get(0).getFolderId())) {

							groupDTO.getFolders().add(folderDTO);
							folderDTO.setId(t.get(0).getFolderId());
							folderDTO.setName(t.get(0).getFolderName());
							folderDTO.setMemo(t.get(0).getFolderMemo());
							folderDTO.setMailGroupIds(t.get(0).getFolderMailGroupIds() == null
									|| StringUtil.isEmpty(t.get(0).getFolderMailGroupIds()) ? new ArrayList<>()
											: Arrays.asList(t.get(0).getFolderMailGroupIds().split(",")));
							folderDTO.setJobs(new ArrayList<>());
							Map<String, List<JobBelongGroup>> jobMap = t.stream()
									.sorted(Comparator.comparing(JobBelongGroup::getJobId))
									.collect(Collectors.groupingBy(JobBelongGroup::getJobId));
							jobMap.values().forEach(new Consumer<List<JobBelongGroup>>() {

								@Override
								public void accept(List<JobBelongGroup> t) {
									for (JobBelongGroup j : t) {
										if (!StringUtil.isEmpty(j.getJobId())) {
											JobDTO jobDTO = new JobDTO();
											folderDTO.getJobs().add(jobDTO);
											jobDTO.setJobId(j.getJobId());
											jobDTO.setName(j.getJobName());
											jobDTO.setMemo(t.get(0).getJobMemo());
											jobDTO.setMailGroupIds(t.get(0).getJobMailGroupIds() == null
													|| StringUtil.isEmpty(t.get(0).getJobMailGroupIds())
															? new ArrayList<>()
															: Arrays.asList(t.get(0).getJobMailGroupIds().split(",")));
										}
									}
								}
							});

							folderDTO.getJobs().sort(new Comparator<JobDTO>() {

								@Override
								public int compare(JobDTO o1, JobDTO o2) {
									return o1.getJobId().compareTo(o2.getJobId());
								}
							});
						}
					}
				});
				groupDTO.getFolders().sort(new Comparator<FolderDTO>() {

					@Override
					public int compare(FolderDTO o1, FolderDTO o2) {
						return o1.getId().compareTo(o2.getId());
					}
				});
			}
		});

		resultList.sort(new Comparator<GroupDTO>() {

			@Override
			public int compare(GroupDTO o1, GroupDTO o2) {
				return o1.getId().compareTo(o2.getId());
			}
		});

		return resultList;
	}

}
