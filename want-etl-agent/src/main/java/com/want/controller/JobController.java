/**
 * -------------------------------------------------------
 * @FileName：WorkerController.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.want.service.worker.WorkerService;

@RestController
@RequestMapping("job")
public class JobController {

	@Autowired
	private WorkerService workerService;

	@PostMapping(value = "/start")
	public boolean start(String jobLogId) {
		return workerService.startJob(jobLogId);
	}

	@PostMapping(value = "/stop")
	public boolean stop(String jobLogId) {
		return workerService.stopJob(jobLogId);
	}
}
