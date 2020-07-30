/**
 * -------------------------------------------------------
 * @FileName：ReportController.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.want.base.service.dto.ResultDTO;
import com.want.controller.service.GetJobReportLogService;
import com.want.controller.service.GetJobReportPageService;
import com.want.controller.service.dto.JobReportDTO;
import com.want.controller.service.dto.JobReportInputDTO;
import com.want.controller.service.dto.JobReportLogDTO;
import com.want.controller.service.dto.PageDTO;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("report")
public class ReportController {

	@Autowired
	private GetJobReportPageService getJobReportPageService;

	@PostMapping(value = "/getJobReportPage")
	public ResultDTO<PageDTO<JobReportDTO>> getJobReportPage(@RequestBody JobReportInputDTO input) {
		return getJobReportPageService.request(null, input);
	}

	@Autowired
	private GetJobReportLogService getJobReportLogService;

	@PostMapping(value = "/getJobReportLog")
	public ResultDTO<JobReportLogDTO> getJobReportLog(String jobLogId) {
		return getJobReportLogService.request(null, jobLogId);
	}
}