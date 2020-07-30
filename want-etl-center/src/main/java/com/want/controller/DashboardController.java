/**
 * -------------------------------------------------------
 * @FileName：DashboardController.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.want.base.service.dto.ResultDTO;
import com.want.controller.service.GetNodesService;
import com.want.controller.service.dto.NodeDTO;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("dashboard")
public class DashboardController {

	@Autowired
	private GetNodesService getNodesService;

	@PostMapping(value = "/getNodes")
	public ResultDTO<List<NodeDTO>> getNodes() {
		return getNodesService.request(null, null);
	}
}
