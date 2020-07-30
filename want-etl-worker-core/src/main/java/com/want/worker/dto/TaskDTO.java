/**
 * -------------------------------------------------------
 * @FileName：Task.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.worker.dto;

import java.util.List;

public class TaskDTO {
	
	private String type;
	private String statement;
	private JDBCServerDTO sourceServer;
	private JCOServerDTO functionServer;
	private String function;
	private List<FormDTO> importForm;
	private JDBCServerDTO targetServer;
	private String database;
	private String table;
	private String condition;
	private String coreSize;
	private List<RuleDTO> rules;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	public JDBCServerDTO getSourceServer() {
		return sourceServer;
	}

	public void setSourceServer(JDBCServerDTO sourceServer) {
		this.sourceServer = sourceServer;
	}

	public JCOServerDTO getFunctionServer() {
		return functionServer;
	}

	public void setFunctionServer(JCOServerDTO functionServer) {
		this.functionServer = functionServer;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public List<FormDTO> getImportForm() {
		return importForm;
	}

	public void setImportForm(List<FormDTO> importForm) {
		this.importForm = importForm;
	}

	public JDBCServerDTO getTargetServer() {
		return targetServer;
	}

	public void setTargetServer(JDBCServerDTO targetServer) {
		this.targetServer = targetServer;
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

	public List<RuleDTO> getRules() {
		return rules;
	}

	public void setRules(List<RuleDTO> rules) {
		this.rules = rules;
	}

}
