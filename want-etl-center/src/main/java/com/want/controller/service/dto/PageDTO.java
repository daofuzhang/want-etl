package com.want.controller.service.dto;

import java.util.Collection;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "分頁資訊")
public class PageDTO<Data> {

	@ApiModelProperty(value = "回傳資料")
	private Collection<Data> content;
	@ApiModelProperty(value = "總筆數")
	private Long totalElements;
	@ApiModelProperty(value = "總頁數")
	private Integer totalPage;

	public Collection<Data> getContent() {
		return content;
	}

	public void setContent(Collection<Data> content) {
		this.content = content;
	}

	public Long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(Long totalElements) {
		this.totalElements = totalElements;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	
}
