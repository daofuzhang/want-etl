/**
 * -------------------------------------------------------
 * @FileName：ExpressionDTO.java
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
public class ExpressionDTO {
	@ApiModelProperty(value = "排程編號")
	private String expressionId;
	@ApiModelProperty(value = "排程格式")
	private String expression;

	public String getExpressionId() {
		return expressionId;
	}

	public void setExpressionId(String expressionId) {
		this.expressionId = expressionId;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

}
