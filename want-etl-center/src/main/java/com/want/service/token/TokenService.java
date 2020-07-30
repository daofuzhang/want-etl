/**
 * -------------------------------------------------------
 * @FileName：TokenService.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.service.token;

import com.want.domain.account.Account;

public interface TokenService {

	public String generateTokenId(String accountId);

	public Account getAccount(String tokenId);

}
