/**
 * -------------------------------------------------------
 * @FileName：AddFolderService.java
 * @Description：简要描述本文件的内容
 * @Author：Dirk.Lee
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.controller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.want.base.service.ResponseDataService;
import com.want.base.service.dto.InputArgumentDTO;
import com.want.base.service.exception.ResponseExceptionFactory;
import com.want.controller.service.SaveFolderService.InnerFolder;
import com.want.controller.service.dto.SaveFolderInputDTO;
import com.want.domain.job.Folder;
import com.want.service.job.JobService;
import com.want.util.StringUtil;

/**
 * @author 80005151
 *
 */
@Service
public class SaveFolderService extends ResponseDataService<SaveFolderInputDTO, InnerFolder, Boolean> {

	@Autowired
	private JobService jobService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.want.base.service.BaseService#checkParameters(java.lang.Object)
	 */
	@Override
	protected void checkParameters(SaveFolderInputDTO parameters) throws Exception {
		if (parameters == null) {
			throw ResponseExceptionFactory.createParameterEmpty("Input不能為空值.");
		}
		if (parameters.getGroupId() == null || StringUtil.isEmpty(parameters.getGroupId())) {
			throw ResponseExceptionFactory.createParameterEmpty("groupId不能為空值.");
		}
		if (parameters.getName() == null || StringUtil.isEmpty(parameters.getName())) {
			throw ResponseExceptionFactory.createParameterEmpty("name不能為空值.");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.want.base.service.BaseService#parseParameters(com.want.base.service.dto.
	 * InputArgumentDTO, java.lang.Object)
	 */
	@Override
	protected InnerFolder parseParameters(InputArgumentDTO argument, SaveFolderInputDTO parameters) throws Exception {
		Folder folder = new Folder();
		folder.setId(parameters.getFolderId() == null ? "" : parameters.getFolderId());
		folder.setName(parameters.getName());
		folder.setMailGroupIds(parameters.getMailGroupIds() == null || parameters.getMailGroupIds().isEmpty() ? ""
				: String.join(",", parameters.getMailGroupIds()));
		folder.setMemo(parameters.getMemo());

		InnerFolder innerFolder = new InnerFolder();
		innerFolder.setGroupId(parameters.getGroupId());
		innerFolder.setFolder(folder);
		return innerFolder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.want.base.service.BaseService#dataAccess(com.want.base.service.dto.
	 * InputArgumentDTO, java.lang.Object)
	 */
	@Override
	protected Boolean dataAccess(InputArgumentDTO argument, InnerFolder parameters) throws Exception {
		jobService.saveFolder(parameters.getFolder(), parameters.getGroupId());
		return true;
	}

	class InnerFolder {
		String groupId;
		Folder folder;

		public String getGroupId() {
			return groupId;
		}

		public void setGroupId(String groupId) {
			this.groupId = groupId;
		}

		public Folder getFolder() {
			return folder;
		}

		public void setFolder(Folder folder) {
			this.folder = folder;
		}

	}

}
