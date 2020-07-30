/**
 * -------------------------------------------------------
 * @FileName：LoginService.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.controller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.transaction.compensating.manager.TransactionAwareContextSourceProxy;
import org.springframework.stereotype.Service;

import com.want.base.service.ResponseDataService;
import com.want.base.service.dto.InputArgumentDTO;
import com.want.base.service.exception.ResponseExceptionFactory;
import com.want.controller.service.dto.LoginInfoDTO;
import com.want.controller.service.dto.LoginInputDTO;
import com.want.domain.account.Account;
import com.want.mapper.AccountMapper;
import com.want.service.token.TokenService;

@Service
public class PasswordLoginService extends ResponseDataService<LoginInputDTO, LoginInputDTO, LoginInfoDTO> {

	@Value("${ldap.url}")
	private String ldapUrl;
	@Value("${ldap.userDn}")
	private String ldapUserDn;
	@Value("${ldap.password}")
	private String ldapPassword;
	@Value("${ldap.base}")
	private String ldapBase;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private AccountMapper accountMapper;

	@Override
	protected void checkParameters(LoginInputDTO parameters) throws Exception {
		if (parameters.getUserId() == null || parameters.getUserId().isEmpty()) {
			throw ResponseExceptionFactory.createParameterEmpty("userId不能為空值.");
		}
		if (parameters.getPassword() == null || parameters.getPassword().isEmpty()) {
			throw ResponseExceptionFactory.createParameterEmpty("password不能為空值.");
		}
	}

	@Override
	protected LoginInputDTO parseParameters(InputArgumentDTO argument, LoginInputDTO parameters) throws Exception {
		return parameters;
	}

	@Override
	protected LoginInfoDTO dataAccess(InputArgumentDTO argument, LoginInputDTO parameters) throws Exception {
		Account account = accountMapper.findOne(parameters.getUserId());
		if (account == null) {
			throw ResponseExceptionFactory.createAccountUnable();
		}
		if (!isPasswordCorrect(parameters.getUserId(), parameters.getPassword())) {
			throw ResponseExceptionFactory.createAccountUnable();
		}
		LoginInfoDTO info = new LoginInfoDTO();
		info.setUserId(account.getId());
		info.setUserName(account.getName());
		info.setAuthorization(tokenService.generateTokenId(account.getId()));
		return info;
	}

	private boolean isPasswordCorrect(String account, String password) throws Exception {
		String filter = "(&(objectclass=person)(cn=" + account + "))";
		LdapTemplate ldapTemplate = getLdapTemplate();
		return ldapTemplate.authenticate(ldapBase, filter, password);
	}

	private LdapTemplate getLdapTemplate() throws Exception {
		LdapContextSource contextSource = new LdapContextSource();
		contextSource.setUrl(ldapUrl);
		contextSource.setUserDn(ldapUserDn);
		contextSource.setPassword(ldapPassword);
		contextSource.afterPropertiesSet();
		TransactionAwareContextSourceProxy transactionAwareContextSourceProxy = new TransactionAwareContextSourceProxy(
				contextSource);
		LdapTemplate ldapTemplate = new LdapTemplate(transactionAwareContextSourceProxy);
		ldapTemplate.afterPropertiesSet();
		return ldapTemplate;
	}

}
