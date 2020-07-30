/**
 * -------------------------------------------------------
 * @FileName：TaskVO.java
 * @Description：简要描述本文件的内容
 * @Author：Dirk.Lee
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.controller.service.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author 80005151
 *
 */
public class TaskDTO {
	@ApiModelProperty(value = "編號")
	private String id;
	@ApiModelProperty(value = "")
	private String jobId;
	@ApiModelProperty(value = "")
	private String type;
	@ApiModelProperty(value = "")
	private String order;
	@ApiModelProperty(value = "")
	private String statement;
	@ApiModelProperty(value = "")
	private String sourceServerId;
	@ApiModelProperty(value = "")
	private String functionServerId;
	@ApiModelProperty(value = "")
	private String function;
	@ApiModelProperty(value = "")
	private String importForm;
	@ApiModelProperty(value = "")
	private String targetServerId;
	@ApiModelProperty(value = "")
	private String database;
	@ApiModelProperty(value = "")
	private String table;
	@ApiModelProperty(value = "")
	private String condition;
	@ApiModelProperty(value = "")
	private String coreSize;
	@ApiModelProperty(value = "")
	private String rules;
	@ApiModelProperty(value = "說明")
	private String memo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	public String getSourceServerId() {
		return sourceServerId;
	}

	public void setSourceServerId(String sourceServerId) {
		this.sourceServerId = sourceServerId;
	}

	public String getFunctionServerId() {
		return functionServerId;
	}

	public void setFunctionServerId(String functionServerId) {
		this.functionServerId = functionServerId;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getImportForm() {
		return importForm;
	}

	public void setImportForm(String importForm) {
		this.importForm = importForm;
	}

	public String getTargetServerId() {
		return targetServerId;
	}

	public void setTargetServerId(String targetServerId) {
		this.targetServerId = targetServerId;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getCoreSize() {
		return coreSize;
	}

	public void setCoreSize(String coreSize) {
		this.coreSize = coreSize;
	}

	public String getRules() {
		return rules;
	}

	public void setRules(String rules) {
		this.rules = rules;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
