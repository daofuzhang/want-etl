/**
 * -------------------------------------------------------
 * @FileName：GetAccountsService.java
 * @Description：简要描述本文件的内容
 * @Author：Dirk.Lee
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.controller.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.want.base.service.ResponseDataService;
import com.want.base.service.dto.InputArgumentDTO;
import com.want.base.service.exception.ResponseExceptionFactory;
import com.want.controller.service.dto.GetUsersInputDTO;
import com.want.controller.service.dto.PageDTO;
import com.want.controller.service.dto.UserDTO;
import com.want.domain.account.Account;
import com.want.service.setting.SettingService;

/**
 * @author 80005151
 *
 */
@Service
public class GetUsersService extends ResponseDataService<GetUsersInputDTO, GetUsersInputDTO, PageDTO<UserDTO>> {
	@Autowired
	SettingService settingService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.want.base.service.BaseService#checkParameters(java.lang.Object)
	 */
	@Override
	protected void checkParameters(GetUsersInputDTO parameters) throws Exception {
		if (parameters.getPageIndex() == null) {
			throw ResponseExceptionFactory.createParameterEmpty("pageIndex不能為空值.");
		}
		if (parameters.getPageSize() == null) {
			throw ResponseExceptionFactory.createParameterEmpty("pageSize不能為空值.");
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
	protected GetUsersInputDTO parseParameters(InputArgumentDTO argument, GetUsersInputDTO parameters)
			throws Exception {
		return parameters;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.want.base.service.BaseService#dataAccess(com.want.base.service.dto.
	 * InputArgumentDTO, java.lang.Object)
	 */
	@Override
	protected PageDTO<UserDTO> dataAccess(InputArgumentDTO argument, GetUsersInputDTO parameters) throws Exception {
		int offset = (parameters.getPageIndex() - 1) * parameters.getPageSize();
		int limit = parameters.getPageSize();
		long totalCount = settingService.getTotalAccountCount();
		List<UserDTO> list = new ArrayList<UserDTO>();
		List<Account> accounts = settingService.getAccounts(offset, limit);
		if (accounts != null && !accounts.isEmpty()) {
			accounts.forEach(new Consumer<Account>() {

				@Override
				public void accept(Account t) {
					UserDTO dto = new UserDTO();
					dto.setId(t.getId());
					dto.setName(t.getName());
					dto.setRoleId(t.getRoleId());
					dto.setEnable(t.getEnable());
					dto.setMail(t.getMail());
					list.add(dto);
				}
			});
		}

		PageDTO<UserDTO> results = new PageDTO<>();
		results.setContent(list);
		results.setTotalElements(totalCount);
		results.setTotalPage((int) (totalCount % limit > 0 ? totalCount / limit + 1 : totalCount / limit));

		return results;
	}

}
