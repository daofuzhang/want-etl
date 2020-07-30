/**
 * -------------------------------------------------------
 * @FileName：Task.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.modal;

import java.util.List;

import com.google.gson.annotations.JsonAdapter;
import com.want.gson.EmptyStringTypeAdapter;

public class Task {

	public static final String TYPE_SYNC = "sync";
	public static final String TYPE_TRUNCATE = "truncate";
	public static final String TYPE_DELETE = "delete";
	public static final String TYPE_CALL = "call";
	public static final String TYPE_CHECK = "check";
	public static final String TYPE_JCO = "jcosync";

	private String type;
	private String source;
	@JsonAdapter(EmptyStringTypeAdapter.class)
	private String statement;
	@JsonAdapter(EmptyStringTypeAdapter.class)
	private String function;
	private List<Form> importForm;
	private String target;
	@JsonAdapter(EmptyStringTypeAdapter.class)
	private String database;
	@JsonAdapter(EmptyStringTypeAdapter.class)
	private String table;
	@JsonAdapter(EmptyStringTypeAdapter.class)
	private String condition;
	private Integer coreSize;
	private List<Rule> rules;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public List<Form> getImportForm() {
		return importForm;
	}

	public void setImportForm(List<Form> importForm) {
		this.importForm = importForm;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
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

	public Integer getCoreSize() {
		return coreSize;
	}

	public void setCoreSize(Integer coreSize) {
		this.coreSize = coreSize;
	}

	public List<Rule> getRules() {
		return rules;
	}

	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}

}
