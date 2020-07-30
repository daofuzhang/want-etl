/**
 * -------------------------------------------------------
 * @FileName：TokenServiceImpl.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.service.token.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.want.domain.account.Account;
import com.want.domain.token.Token;
import com.want.mapper.AccountMapper;
import com.want.mapper.TokenMapper;
import com.want.service.token.TokenService;

@Service
public class TokenServiceImpl implements TokenService {

	private final long TOKEN_EXPIRED_TIME = 30 * 60 * 1000;

	@Autowired
	private TokenMapper tokenMapper;
	@Autowired
	private AccountMapper accountMapper;

	@Override
	public String generateTokenId(String accountId) {
		Token token = new Token();
		token.setAccountId(accountId);
		token.setExpiredTime(new Timestamp(System.currentTimeMillis() + TOKEN_EXPIRED_TIME));
		token.setCreateTime(new Timestamp(System.currentTimeMillis()));
		tokenMapper.insert(token);
		return token.getId();
	}

	@Override
	public Account getAccount(String tokenId) {
		Token token = tokenMapper.findOne(tokenId);
		if (token == null) {
			return null;
		}
		if (System.currentTimeMillis() > token.getExpiredTime().getTime()) {
			return null;
		}
		tokenMapper.updateExpiredTime(tokenId, new Timestamp(System.currentTimeMillis() + TOKEN_EXPIRED_TIME));
		return accountMapper.findOne(token.getAccountId());
	}

}
