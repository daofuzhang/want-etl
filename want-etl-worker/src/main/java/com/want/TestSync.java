/**
 * -------------------------------------------------------
 * @FileName：TestSync.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want;

import java.util.Arrays;

import com.want.worker.JobWorker;
import com.want.worker.dto.FormDTO;
import com.want.worker.dto.JCOServerDTO;
import com.want.worker.dto.JDBCServerDTO;
import com.want.worker.dto.JobStrategy;
import com.want.worker.dto.JobDTO;
import com.want.worker.dto.RuleDTO;
import com.want.worker.dto.RuleType;
import com.want.worker.dto.TaskDTO;
import com.want.worker.dto.TaskType;
import com.want.worker.sync.model.ConnectionType;

public class TestSync {

	public static void main(String[] args) {
		JDBCServerDTO server = new JDBCServerDTO();
		server.setType(ConnectionType.MYSQL.getText());
		server.setUrl("jdbc:mysql://10.231.48.196:3306/MCHANNEL?characterEncoding=utf8&useUnicode=true&useSSL=false");
		server.setUsername("sync");
		server.setPassword("P@ssw0rd");

		TaskDTO delete = new TaskDTO();
		delete.setType(TaskType.DELETE.getText());
		delete.setTargetServer(server);
		delete.setDatabase("AISOURCE");
		delete.setTable("ZWWD_O02_REALTIME_TEST");
		delete.setCondition("WHERE ZSDIC054 = '001'");

		TaskDTO truncate = new TaskDTO();
		truncate.setType(TaskType.TRUNCATE.getText());
		truncate.setTargetServer(server);
		truncate.setDatabase("AISOURCE");
		truncate.setTable("ZWWD_O02_REALTIME_TEST");

		TaskDTO sync = new TaskDTO();
		sync.setType(TaskType.SYNC.getText());
		sync.setSourceServer(server);
		sync.setStatement("SELECT * FROM AISOURCE.ZWWD_O02_REALTIME");
		sync.setTargetServer(server);
		sync.setDatabase("AISOURCE");
		sync.setTable("ZWWD_O02_REALTIME_TEST");
		sync.setCoreSize("4");

		TaskDTO call = new TaskDTO();
		call.setType(TaskType.CALL.getText());
		call.setTargetServer(server);
		call.setStatement("call AISOURCE.test()");

		RuleDTO rule = new RuleDTO();
		rule.setField("CHECK_DATE");
		rule.setType(RuleType.EQUALS.getText());
		rule.setValue("20200112");
		TaskDTO check = new TaskDTO();
		check.setType(TaskType.CHECK.getText());
		check.setTargetServer(server);
		check.setStatement("SELECT * FROM AISOURCE.ZWWD_DATA_STATUS WHERE `TABLE_NAME` = 'ZWWD_O02_REALTIME'");
		check.setRules(Arrays.asList(rule));

		JCOServerDTO functionServer = new JCOServerDTO();
		functionServer.setDestinationName("TEST.JCO");
		functionServer.setLang("ZH");
		functionServer.setPasswd("123123");
		functionServer.setSysnr("01");
		functionServer.setClient("400");
		functionServer.setPoolCapacity("3");
		functionServer.setUser("HQTR0701009");
		functionServer.setPeakLimit("10");
		functionServer.setAshost("10.0.110.170");
		FormDTO BEGDA = new FormDTO();
		BEGDA.setKey("BEGDA");
		BEGDA.setVal("20200201");
		FormDTO ENDDA = new FormDTO();
		ENDDA.setKey("ENDDA");
		ENDDA.setVal("20200301");
		FormDTO IV_WADAT = new FormDTO();
		IV_WADAT.setKey("IV_WADAT");
		IV_WADAT.setValue(Arrays.asList(BEGDA, ENDDA));
		TaskDTO jco = new TaskDTO();
		jco.setFunctionServer(functionServer);
		jco.setFunction("ZRFCSD035");
		jco.setImportForm(Arrays.asList(IV_WADAT));
		jco.setType(TaskType.JCO.getText());
		jco.setTargetServer(server);
		jco.setDatabase("AISOURCE");
		jco.setTable("ZRFCSD035");
		jco.setCoreSize("4");

		JobDTO job = new JobDTO();
		job.setJobName("測試同步");
		job.setJobStrategy(JobStrategy.INTERRUPTED.getText());
		job.setTasks(Arrays.asList(delete, truncate, sync, call, check, jco));

//		System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(job));
		JobWorker.run(job);
	}

}
