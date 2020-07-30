/**
 * -------------------------------------------------------
 * @FileName：SwaggerInterceptor.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.config;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.want.domain.job.Job;
import com.want.domain.log.JobLog;
import com.want.mapper.JobLogMapper;
import com.want.mapper.JobMapper;

@Component
public class SwaggerFilter implements Filter {

	@Autowired
	private JobMapper jobMapper;

	@Autowired
	private JobLogMapper jobLogMapper;
	
	private Gson gson = new Gson();

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		JsonArray jobIds = new JsonArray();
		List<Job> jobs = jobMapper.findAll();
		if (jobs != null && !jobs.isEmpty()) {
			for (Job job : jobs) {
				jobIds.add(job.getId());
			}
		}

		JsonArray jobLogIds = new JsonArray();
		List<JobLog> jobLogs = jobLogMapper.findAll();
		if (jobLogs != null && !jobLogs.isEmpty()) {
			for (JobLog jobLog : jobLogs) {
				jobLogIds.add(jobLog.getId());
			}
		}

		SwaggerWrapper wrapper = new SwaggerWrapper((HttpServletResponse) response);
		chain.doFilter(request, wrapper);
		String json = wrapper.getBodyString();
		
		JsonElement swagger = JsonParser.parseString(json);
		JsonElement paths = swagger.getAsJsonObject().get("paths");
		paths.getAsJsonObject().keySet().forEach((key) -> {
			JsonElement path = paths.getAsJsonObject().get(key);
			JsonElement post = path.getAsJsonObject().get("post");
			JsonElement parameters = post.getAsJsonObject().get("parameters");
			if (parameters == null) {
				return;
			}
			parameters.getAsJsonArray().forEach((ele) -> {
				if (ele == null) {
					return;
				}
				JsonObject parameter = ele.getAsJsonObject();
				String name = parameter.get("name").getAsString();
				if (name.equals("jobId")) {
					parameter.addProperty("in", "query");
					parameter.addProperty("type", "string");
					parameter.add("enum", jobIds);
					parameter.remove("schema");
				}
				if (name.equals("jobLogId")) {
					parameter.addProperty("in", "query");
					parameter.addProperty("type", "string");
					parameter.add("enum", jobLogIds);
					parameter.remove("schema");
				}
			});
		});
		wrapper.getResponse().getOutputStream().write(gson.toJson(swagger).getBytes());
	}

	class SwaggerWrapper extends HttpServletResponseWrapper {

		private final SwaggerWrapperServletOutputStream wrapperServletOutputStream = new SwaggerWrapperServletOutputStream();

		public SwaggerWrapper(HttpServletResponse response) {
			super(response);
		}

		@Override
		public ServletOutputStream getOutputStream() throws IOException {
			return wrapperServletOutputStream;
		}

		@Override
		public PrintWriter getWriter() throws IOException {
			return new PrintWriter(wrapperServletOutputStream);
		}

		/**
		 * 获取 数组body
		 *
		 * @author ming
		 * @date 2019-01-10 16:23:42
		 */
		public byte[] getBodyBytes() {
			return wrapperServletOutputStream.out.toByteArray();
		}

		/**
		 * 获取 字符串body utf-8 编码
		 *
		 * @author ming
		 * @date 2019-01-10 16:23:54
		 */
		public String getBodyString() {
			try {
				return wrapperServletOutputStream.out.toString("UTF-8");
			} catch (UnsupportedEncodingException e) {
				return "[UNSUPPORTED ENCODING]";
			}
		}

		/**
		 * 将 body内容 重新赋值到 response 中 由于stream 只读一次 需要重写到response中
		 *
		 * @author ming
		 * @date 2019-01-10 16:24:25
		 */
		public void copyToResponse() {
			try {
				getResponse().getOutputStream().write(getBodyBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		private class SwaggerWrapperServletOutputStream extends ServletOutputStream {
			private ByteArrayOutputStream out = new ByteArrayOutputStream();

			@Override
			public boolean isReady() {
				return true;
			}

			@Override
			public void setWriteListener(WriteListener writeListener) {

			}

			@Override
			public void write(int b) throws IOException {
				out.write(b);
			}

			@Override
			public void write(byte[] b) throws IOException {
				out.write(b);
			}

			@Override
			public void write(byte[] b, int off, int len) throws IOException {
				out.write(b, off, len);
			}
		}
	}

}
