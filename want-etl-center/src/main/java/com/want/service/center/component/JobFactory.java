/**
 * -------------------------------------------------------
 * @FileName：JobDTOFactory.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.service.center.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.want.domain.job.Job;
import com.want.domain.job.Task;
import com.want.domain.server.JCOServer;
import com.want.domain.server.JDBCServer;
import com.want.worker.dto.FormDTO;
import com.want.worker.dto.JCOServerDTO;
import com.want.worker.dto.JDBCServerDTO;
import com.want.worker.dto.JobDTO;
import com.want.worker.dto.RuleDTO;
import com.want.worker.dto.TaskDTO;

public class JobFactory {

	static Gson gson = new Gson();

	public static String toJson(Object src) {
		return gson.toJson(src);
	}

	public static JobDTO create(Job job, List<Task> tasks, List<JDBCServer> jdbcServers, List<JCOServer> jcoServers,
			String batchLoggerServerId) {
		Map<String, JDBCServer> jdbcServerMap = jdbcServers.stream()
				.collect(Collectors.toMap(server -> server.getId(), Function.identity(), (e1, e2) -> e1));
		Map<String, JCOServer> jcoServerMap = jcoServers.stream()
				.collect(Collectors.toMap(server -> server.getId(), Function.identity(), (e1, e2) -> e1));

		JobDTO jobTO = new JobDTO();
		jobTO.setJobName(job.getName());
		jobTO.setJobStrategy(job.getStrategy());
		jobTO.setTasks(new ArrayList<>());
		jobTO.setBatchFuncId(job.getBatchFuncId());
		jobTO.setBatchLoggerServer(batchLoggerServerId == null || !jdbcServerMap.containsKey(batchLoggerServerId) ? null
				: convertJDBCServerDTO(jdbcServerMap.get(batchLoggerServerId)));
		jobTO.setDynamics(job.getDynamics());
		for (Task task : tasks) {
			if (task == null) {
				continue;
			}
			TaskDTO taskTO = new TaskDTO();
			taskTO.setType(task.getType());
			taskTO.setStatement(task.getStatement());
			taskTO.setDatabase(task.getDatabase());
			taskTO.setFunction(task.getFunction());
			taskTO.setTable(task.getTable());
			taskTO.setCondition(task.getCondition());
			taskTO.setCoreSize(task.getCoreSize());
			if (task.getImportForm() != null && !task.getImportForm().isEmpty()) {
				taskTO.setImportForm(gson.fromJson(task.getImportForm(), new TypeToken<List<FormDTO>>() {
				}.getType()));
			}
			if (task.getRules() != null && !task.getRules().isEmpty()) {
				taskTO.setRules(gson.fromJson(task.getRules(), new TypeToken<List<RuleDTO>>() {
				}.getType()));
			}
			if (task.getSourceServerId() != null && !task.getSourceServerId().isEmpty()) {
				JDBCServer jdbcServer = jdbcServerMap.get(task.getSourceServerId());
				taskTO.setSourceServer(convertJDBCServerDTO(jdbcServer));
			}
			if (task.getFunctionServerId() != null && !task.getFunctionServerId().isEmpty()) {
				JCOServer jcoServer = jcoServerMap.get(task.getFunctionServerId());
				taskTO.setFunctionServer(convertJCOServerDTO(jcoServer));
			}
			if (task.getTargetServerId() != null && !task.getTargetServerId().isEmpty()) {
				JDBCServer jdbcServer = jdbcServerMap.get(task.getTargetServerId());
				taskTO.setTargetServer(convertJDBCServerDTO(jdbcServer));
			}
			jobTO.getTasks().add(taskTO);
		}
		return jobTO;
	}

	private static JDBCServerDTO convertJDBCServerDTO(JDBCServer jdbcServer) {
		JDBCServerDTO jdbcServerTO = new JDBCServerDTO();
		jdbcServerTO.setType(jdbcServer.getType());
		jdbcServerTO.setUrl(jdbcServer.getUrl());
		jdbcServerTO.setUsername(jdbcServer.getUserName());
		jdbcServerTO.setPassword(jdbcServer.getPassword());
		return jdbcServerTO;
	}

	private static JCOServerDTO convertJCOServerDTO(JCOServer jcoServer) {
		JCOServerDTO jcoServerTO = new JCOServerDTO();
		jcoServerTO.setDestinationName(jcoServer.getName());
		jcoServerTO.setLang(jcoServer.getLang());
		jcoServerTO.setPasswd(jcoServer.getPasswd());
		jcoServerTO.setSysnr(jcoServer.getSysnr());
		jcoServerTO.setClient(jcoServer.getClient());
		jcoServerTO.setPoolCapacity(jcoServer.getPoolCapacity());
		jcoServerTO.setUser(jcoServer.getUser());
		jcoServerTO.setPeakLimit(jcoServer.getPeakLimit());
		jcoServerTO.setAshost(jcoServer.getAshost());
		return jcoServerTO;
	}

}
