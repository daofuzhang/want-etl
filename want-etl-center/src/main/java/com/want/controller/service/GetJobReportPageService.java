/**
 * -------------------------------------------------------
 * @FileName：GetReportByPageService.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.controller.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.want.base.service.ResponseDataService;
import com.want.base.service.dto.InputArgumentDTO;
import com.want.base.service.exception.ResponseExceptionFactory;
import com.want.controller.service.dto.JobReportDTO;
import com.want.controller.service.dto.JobReportInputDTO;
import com.want.controller.service.dto.JobReportLogDTO;
import com.want.controller.service.dto.PageDTO;
import com.want.domain.log.JobReport;
import com.want.mapper.JobReportMapper;

@Service
public class GetJobReportPageService
		extends ResponseDataService<JobReportInputDTO, JobReportInputDTO, PageDTO<JobReportDTO>> {

	final static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	private JobReportMapper jobReportMapper;

	@Override
	protected void checkParameters(JobReportInputDTO parameters) throws Exception {
		if (parameters.getPageIndx() == null) {
			throw ResponseExceptionFactory.createParameterEmpty("pageIndx不能為空值.");
		}
		if (parameters.getPageSize() == null) {
			throw ResponseExceptionFactory.createParameterEmpty("pageSize不能為空值.");
		}
		if (!validateDate(parameters.getStartTime())) {
			throw ResponseExceptionFactory.createParameterFormatError("startTime格式錯誤.");
		}
		if (!validateDate(parameters.getEndTime())) {
			throw ResponseExceptionFactory.createParameterFormatError("endTime格式錯誤.");
		}
	}

	private boolean validateDate(String time) {
		if (time == null || time.isEmpty()) {
			return true;
		}
		try {
			dateFormat.parse(time);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	@Override
	protected JobReportInputDTO parseParameters(InputArgumentDTO argument, JobReportInputDTO parameters)
			throws Exception {
		return parameters;
	}

	@Override
	protected PageDTO<JobReportDTO> dataAccess(InputArgumentDTO argument, JobReportInputDTO parameters)
			throws Exception {
		int offset = (parameters.getPageIndx() - 1) * parameters.getPageSize();
		int limit = parameters.getPageSize();
		long totalElements = jobReportMapper.countLatestJobReport(parameters.getKeyword(), parameters.getStartTime(),
				parameters.getEndTime(), parameters.getGroupId(), parameters.getFolderId(), parameters.getStatus());

		List<JobReport> reports = jobReportMapper.findLatestJobReport(parameters.getKeyword(),
				parameters.getStartTime(), parameters.getEndTime(), parameters.getGroupId(), parameters.getFolderId(),
				parameters.getStatus(), offset, limit);
		Map<String, List<JobReport>> reportMap = reports.stream().collect(Collectors.groupingBy(JobReport::getJobId));

		List<JobReport> logs = jobReportMapper.findByJobIds(new ArrayList<String>(reportMap.keySet()),
				parameters.getStartTime(), parameters.getEndTime());
		Map<String, List<JobReport>> logMap = logs.stream()
				.collect(Collectors.groupingBy(JobReport::getJobId,
						Collectors.mapping(Function.identity(),
								Collectors.collectingAndThen(Collectors.toList(),
										e -> e.stream()
												.sorted(Comparator.comparing(JobReport::getJobLogId).reversed())
												.collect(Collectors.toList())))));

		List<JobReportDTO> content = new ArrayList<JobReportDTO>();
		reportMap.forEach((k, v) -> {
			JobReport report = v.get(0);
			JobReportDTO reportDTO = new JobReportDTO();
			reportDTO.setJobId(report.getJobId());
			reportDTO.setName(report.getName());
			reportDTO.setDescription(report.getMemo());
			reportDTO.setJobLogId(report.getJobLogId());
			reportDTO.setStartTime(report.getStartTime());
			reportDTO.setEndTime(report.getEndTime());
			reportDTO.setDuration(report.getDuration());
			reportDTO.setStatus(report.getStatus());
			reportDTO.setRecentLogs(new ArrayList<>());

			logMap.get(k).forEach(r -> {
				JobReportLogDTO jobLogDTO = new JobReportLogDTO();
				jobLogDTO.setJobLogId(r.getJobLogId());
				jobLogDTO.setStartTime(r.getStartTime());
				jobLogDTO.setEndTime(r.getEndTime());
				jobLogDTO.setStatus(r.getStatus());
				reportDTO.getRecentLogs().add(jobLogDTO);
			});
			content.add(reportDTO);
		});

		PageDTO<JobReportDTO> result = new PageDTO<>();
		result.setContent(content);
		result.setTotalElements(totalElements);
		result.setTotalPage((int) (totalElements % limit > 0 ? totalElements / limit + 1 : totalElements / limit));
		return result;
	}

}
