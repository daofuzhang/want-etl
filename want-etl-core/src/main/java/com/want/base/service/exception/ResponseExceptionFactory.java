/**
 * -------------------------------------------------------
 * @FileName：ResponseExceptionFactory.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.base.service.exception;

import com.want.base.service.ReturnCode;

public class ResponseExceptionFactory {

	public static ResponseException createTokenUnable() {
		return new ResponseException(ReturnCode.RETURN_CODE_TOKEN_UNABLE, "凭证无效.");
	}

	public static ResponseException createTokenExpiration() {
		return new ResponseException(ReturnCode.RETURN_CODE_TOKEN_EXPIRATION, "凭证逾时.");
	}

	public static ResponseException createAccountUnable() {
		return new ResponseException(ReturnCode.RETURN_CODE_ACCOUNT_UNABLE, "帐号无效.");
	}

	public static ResponseException createParameterEmpty(String parameterMsg) {
		return new ResponseException(ReturnCode.RETURN_CODE_PARAMETER_EMPTY, parameterMsg);
	}

	public static ResponseException createParameterFormatError(String parameterMsg) {
		return new ResponseException(ReturnCode.RETURN_CODE_PARAMETER_FORMAT_ERROR, parameterMsg);
	}

}
