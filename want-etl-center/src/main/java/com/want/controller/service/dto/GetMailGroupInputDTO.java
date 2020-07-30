/**
 * -------------------------------------------------------
 * @FileName：GetMailGroupInputDTO.java
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
public class GetMailGroupInputDTO {
	@ApiModelProperty(value = "頁碼", position = 1, example = "1")
	private Integer pageIndex;
	@ApiModelProperty(value = "單頁筆數", position = 2, example = "20")
	private Integer pageSize;

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
