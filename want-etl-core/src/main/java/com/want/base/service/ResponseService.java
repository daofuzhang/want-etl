package com.want.base.service;

import com.want.base.service.dto.InputArgumentDTO;
import com.want.base.service.dto.ResultDTO;

public abstract class ResponseService<Parameters, DataAccessParameters>
		extends ResponseDataService<Parameters, DataAccessParameters, Void> {

	@Override
	protected ResultDTO<Void> generateResultData(InputArgumentDTO argument, Void data) {
		ResultDTO<Void> responseDataEntity = new ResultDTO<>();
		responseDataEntity.setReturnCode(ReturnCode.RETURN_CODE_SUCCESS);
		return responseDataEntity;
	}

}
