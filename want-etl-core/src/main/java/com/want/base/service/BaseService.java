package com.want.base.service;

import com.want.base.service.dto.InputArgumentDTO;
import com.want.base.service.exception.ResponseException;

public abstract class BaseService<Parameters, DataAccessParameters, DataAccessObject, Result> {

	/**
	 * <p>
	 * Handle request in a specific order of process.
	 * </p>
	 *
	 * <p>
	 * Start from check required parameters, parse parameters, data access, generate
	 * result data to logging.
	 * </p>
	 * <p>
	 * Each process has their own exception handling process.
	 * </p>
	 */
	public Result request(InputArgumentDTO argument, Parameters parameters) {
		// TODO: should combine with generateCheckKeyList step for more flexible
		// implementation of required parameter checking logic.
		try {
			checkParameters(parameters);
		} catch (Exception e) {
			if (e instanceof ResponseException) {
				return handleResponseException((ResponseException) e);
			}
			e.printStackTrace();
			return handleCheckParametersException(e);
		}

		// TODO: considering combined with parameter checking step or leave it abstract
		// for implementation.
		DataAccessParameters dataAccessParameters;
		try {
			dataAccessParameters = parseParameters(argument, parameters);
		} catch (Exception e) {
			if (e instanceof ResponseException) {
				return handleResponseException((ResponseException) e);
			}
			e.printStackTrace();
			return handleParseParametersException(e);
		}

		// TODO: should combine with result data generating step.
		DataAccessObject dataAccessObject;
		try {
			dataAccessObject = dataAccess(argument, dataAccessParameters);
		} catch (Exception e) {
			if (e instanceof ResponseException) {
				return handleResponseException((ResponseException) e);
			}
			e.printStackTrace();
			return handleDataAccessException(e);
		}

		// TODO: meaningless as in reality this step is always done in data access step.
		Result result;
		try {
			result = generateResultData(argument, dataAccessObject);
		} catch (Exception e) {
			if (e instanceof ResponseException) {
				return handleResponseException((ResponseException) e);
			}
			e.printStackTrace();
			return handleGenerateResultDataException(e);
		}

		return result;
	}

	protected abstract void checkParameters(Parameters parameters) throws Exception;

	protected abstract DataAccessParameters parseParameters(InputArgumentDTO argument, Parameters parameters) throws Exception;

	protected abstract DataAccessObject dataAccess(InputArgumentDTO argument, DataAccessParameters parameters) throws Exception;

	protected abstract Result generateResultData(InputArgumentDTO argument, DataAccessObject dataAccessObject) throws Exception;

	protected abstract Result handleResponseException(ResponseException exception);

	protected abstract Result handleCheckParametersException(Exception e);

	protected abstract Result handleParseParametersException(Exception e);

	protected abstract Result handleDataAccessException(Exception e);

	protected abstract Result handleGenerateResultDataException(Exception e);

}
