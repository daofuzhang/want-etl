/**
 * -------------------------------------------------------
 * @FileName：VelocityUtil.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.util;

import java.io.StringWriter;
import java.util.Calendar;
import java.util.List;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.tools.generic.DateTool;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.want.worker.dto.DynamicDTO;
import com.want.worker.dto.FormDTO;
import com.want.worker.dto.JobDTO;
import com.want.worker.dto.RuleDTO;
import com.want.worker.dto.TaskDTO;

public class VelocityUtil {

	static Gson gson = new Gson();

	public static void convert(JobDTO job) {
		if (job.getDynamics() == null || job.getDynamics().isEmpty()) {
			return;
		}
		Velocity.init();
		VelocityContext context = VelocityUtil.createVelocityContext(job.getDynamics());
		for (TaskDTO task : job.getTasks()) {
			task.setStatement(VelocityUtil.velocityEvaluate(context, task.getStatement()));
			task.setCondition(VelocityUtil.velocityEvaluate(context, task.getCondition()));
			String importFormString = VelocityUtil.velocityEvaluate(context, gson.toJson(task.getImportForm()));
			task.setImportForm(gson.fromJson(importFormString, new TypeToken<List<FormDTO>>() {
			}.getType()));
			String rulesString = VelocityUtil.velocityEvaluate(context, gson.toJson(task.getRules()));
			task.setRules(gson.fromJson(rulesString, new TypeToken<List<RuleDTO>>() {
			}.getType()));
		}
	}

	public static VelocityContext createVelocityContext(String json) {
		VelocityContext context = new VelocityContext();
		try {
			List<DynamicDTO> dynamics = new Gson().fromJson(json, new TypeToken<List<DynamicDTO>>() {
			}.getType());
			DateTool dateTool = new DateTool();
			long now = System.currentTimeMillis();
			for (DynamicDTO dynamic : dynamics) {
				if (dynamic.getName() == null || dynamic.getFormat() == null)
					continue;
				Calendar calendar = dateTool.toCalendar(now);
				if (dynamic.getYear() != null)
					calendar.add(Calendar.YEAR, dynamic.getYear());
				if (dynamic.getMonth() != null)
					calendar.add(Calendar.MONTH, dynamic.getMonth());
				if (dynamic.getDate() != null)
					calendar.add(Calendar.DATE, dynamic.getDate());
				context.put(dynamic.getName(), dateTool.format(dynamic.getFormat(), calendar.getTime()));
			}
		} catch (Exception e) {
		}
		return context;
	}

	public static String velocityEvaluate(VelocityContext context, String template) {
		StringWriter writer = new StringWriter();
		Velocity.evaluate(context, writer, "template", template);
		return writer.toString();
	}

}
