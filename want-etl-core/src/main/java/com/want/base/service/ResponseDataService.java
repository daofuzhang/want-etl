/**
 * -------------------------------------------------------
 * @FileName：BaseService.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.base.service;

import com.want.base.service.dto.InputArgumentDTO;
import com.want.base.service.dto.ResultDTO;
import com.want.base.service.exception.ResponseException;

public abstract class ResponseDataService<Parameters, DataAccessParameters, DataAccessObject>
		extends BaseService<Parameters, DataAccessParameters, DataAccessObject, ResultDTO<DataAccessObject>> {

	@Override
	protected ResultDTO<DataAccessObject> generateResultData(InputArgumentDTO argument,
			DataAccessObject dataAccessObject) throws Exception {
		ResultDTO<DataAccessObject> responseDataEntity = new ResultDTO<>();
		responseDataEntity.setReturnCode(ReturnCode.RETURN_CODE_SUCCESS);
		responseDataEntity.setData(dataAccessObject);
		return responseDataEntity;
	}

	@Override
	protected ResultDTO<DataAccessObject> handleResponseException(ResponseException e) {
		ResultDTO<DataAccessObject> responseDataEntity = new ResultDTO<>();
		responseDataEntity.setReturnCode(e.getReturnCode());
		responseDataEntity.setReturnMessage(e.getReturnMessage());
		return responseDataEntity;
	}

	@Override
	protected ResultDTO<DataAccessObject> handleCheckParametersException(Exception e) {
		ResultDTO<DataAccessObject> responseDataEntity = new ResultDTO<>();
		responseDataEntity.setReturnCode(ReturnCode.RETURN_CODE_CHECK_PARAMETERS_ERROR);
		responseDataEntity.setReturnMessage(e.getMessage());
		return responseDataEntity;
	}

	@Override
	protected ResultDTO<DataAccessObject> handleParseParametersException(Exception e) {
		ResultDTO<DataAccessObject> responseDataEntity = new ResultDTO<>();
		responseDataEntity.setReturnCode(ReturnCode.RETURN_CODE_PARSE_PARAMETERS_ERROR);
		responseDataEntity.setReturnMessage(e.getMessage());
		return responseDataEntity;
	}

	@Override
	protected ResultDTO<DataAccessObject> handleDataAccessException(Exception e) {
		ResultDTO<DataAccessObject> responseDataEntity = new ResultDTO<>();
		responseDataEntity.setReturnCode(ReturnCode.RETURN_CODE_DATA_ACCESS_ERROR);
		responseDataEntity.setReturnMessage(e.getMessage());
		return responseDataEntity;
	}

	@Override
	protected ResultDTO<DataAccessObject> handleGenerateResultDataException(Exception e) {
		ResultDTO<DataAccessObject> responseDataEntity = new ResultDTO<>();
		responseDataEntity.setReturnCode(ReturnCode.RETURN_CODE_GENERATE_RESULT_DATA_ERROR);
		responseDataEntity.setReturnMessage(e.getMessage());
		return responseDataEntity;
	}

}
