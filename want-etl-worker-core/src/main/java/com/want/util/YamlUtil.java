package com.want.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.want.gson.CollectionAdapter;
import com.want.gson.MapDeserializerDoubleAsIntFix;
import com.want.modal.CronJob;
import com.want.modal.CronJobSetting;
import com.want.modal.Jco;
import com.want.modal.JcoDefine;
import com.want.modal.Job;
import com.want.modal.Mail;
import com.want.modal.MailGroup;
import com.want.modal.MailServerDefine;
import com.want.modal.Server;
import com.want.modal.ServerDefine;

/**
 * -------------------------------------------------------
 * 
 * @FileName：YamlUtil.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright www.want-want.com Ltd. All rights reserved.
 *            注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 *            -------------------------------------------------------
 */

public class YamlUtil {
	public final static String PATH_ROOT = System.getProperty("user.dir");
	public final static String PATH_JOBS = PATH_ROOT + "/job";
	public final static String PATH_SERVER = PATH_ROOT + "/config/server.yml";
	public final static String PATH_MAIL = PATH_ROOT + "/config/mail.yml";
	public final static String PATH_CRON_JOBS = PATH_ROOT + "/config/cronjob-setting.yml";
	public final static String PATH_JCO = PATH_ROOT + "/config/jco.yml";
	public final static String PATH_MONITOR = PATH_ROOT + "/config/monitor.yml";

	public static DumperOptions getDumperOptions() {
		DumperOptions options = new DumperOptions();
		options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
		options.setPrettyFlow(true);
		return options;
	}

	public static LoaderOptions getLoaderOptions() {
		LoaderOptions options = new LoaderOptions();
		return options;
	}

	private static void mkdirs(String path) {
		File folder = new File(path.substring(0, path.lastIndexOf(File.separator)));
		if (!folder.exists()) {
			folder.mkdirs();
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////

	public static void saveJob(Job job) throws IOException {
		String path = PATH_JOBS + File.separator + job.getJobName() + ".yml";
		mkdirs(path);

		Type type = new TypeToken<Map<String, Object>>() {
		}.getType();

		// 1.convert empty string, empty array to null. 2.fix integer issue.
		Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping()
				.registerTypeHierarchyAdapter(Collection.class, new CollectionAdapter())
				.registerTypeAdapter(type, new MapDeserializerDoubleAsIntFix()).create();
		String json = gson.toJson(job);
		// System.out.println(json);
		// ignore null field.
		Representer representer = new Representer() {
			@Override
			protected NodeTuple representJavaBeanProperty(Object javaBean, Property property, Object propertyValue,
					Tag customTag) {
				if (propertyValue == null) {
					return null;
				} else {
					return super.representJavaBeanProperty(javaBean, property, propertyValue, customTag);
				}
			}
		};
		Yaml yaml = new Yaml(representer, getDumperOptions());
		yaml.dump(gson.fromJson(json, type), new FileWriter(path));
	}

	public static Job loadJob(String jobName) throws IOException {
		InputStream input = new FileInputStream(new File(PATH_JOBS + File.separator + jobName + ".yml"));
		Job job = new Yaml().loadAs(input, Job.class);
		return job;
	}

	public static List<String> getJobNames() {
		List<String> jobNames = new ArrayList<>();
		File folder = new File(PATH_JOBS);
		for (File file : getAllYmlFiles(folder)) {
			String path = file.getAbsolutePath().replace(folder.getAbsolutePath() + File.separator, "");
			jobNames.add(path.substring(0, path.lastIndexOf(".")));
		}
		return jobNames;
	}

	public static List<File> getAllYmlFiles(File folder) {
		List<File> list = new ArrayList<File>();
		if (folder.exists()) {
			for (File temp : folder.listFiles()) {
				if (temp.isFile() && temp.getName().endsWith(".yml")) {
					list.add(temp);
				} else if (temp.isDirectory()) {
					list.addAll(getAllYmlFiles(temp));

				}
			}
		}
		return list;
	}

	////////////////////////////////////////////////////////////////////////////////////////////

	public static void saveCronJobs(List<CronJob> cronJobs) throws JsonSyntaxException, IOException {
		mkdirs(PATH_CRON_JOBS);

		CronJobSetting setting = new CronJobSetting();
		setting.setCronJabs(cronJobs);

		Type type = new TypeToken<Map<String, Object>>() {
		}.getType();

		// 1.convert empty string, empty array to null. 2.fix integer issue.
		Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping()
				.registerTypeHierarchyAdapter(Collection.class, new CollectionAdapter())
				.registerTypeAdapter(type, new MapDeserializerDoubleAsIntFix()).create();
		String json = gson.toJson(setting);
		// System.out.println(json);
		// ignore null field.
		Representer representer = new Representer() {
			@Override
			protected NodeTuple representJavaBeanProperty(Object javaBean, Property property, Object propertyValue,
					Tag customTag) {
				if (propertyValue == null) {
					return null;
				} else {
					return super.representJavaBeanProperty(javaBean, property, propertyValue, customTag);
				}
			}
		};
		Yaml yaml = new Yaml(representer, getDumperOptions());
		yaml.dump(gson.fromJson(json, type), new FileWriter(PATH_CRON_JOBS));

	}

	public static List<CronJob> loadCronJobs() {
		try {
			InputStream input = new FileInputStream(new File(PATH_CRON_JOBS));
			CronJobSetting setting = new Yaml().loadAs(input, CronJobSetting.class);
			return setting.getCronJabs();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return Arrays.asList();
	}

	////////////////////////////////////////////////////////////////////////////////////////////

	public static Map<String, ServerDefine> getServerDefineMap(StandardPBEStringEncryptor encryptor) {
		InputStream input = null;
		try {
			input = new FileInputStream(new File(PATH_SERVER));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Server server = new Yaml().loadAs(input, Server.class);
		Map<String, ServerDefine> map = new HashMap<>();
		for (ServerDefine serverDefine : server.getServerDefine()) {
			serverDefine.setUsername(JasyptUtil.decode(encryptor, serverDefine.getUsername()));
			serverDefine.setPassword(JasyptUtil.decode(encryptor, serverDefine.getPassword()));
			map.put(serverDefine.getServerName(), serverDefine);
		}
		return map;
	}

	public static Map<String, ServerDefine> getMonitorServerDefineMap(StandardPBEStringEncryptor encryptor) {
		InputStream input = null;
		try {
			input = new FileInputStream(new File(PATH_MONITOR));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Server server = new Yaml().loadAs(input, Server.class);
		Map<String, ServerDefine> map = new HashMap<>();
		for (ServerDefine serverDefine : server.getServerDefine()) {
			serverDefine.setUsername(JasyptUtil.decode(encryptor, serverDefine.getUsername()));
			serverDefine.setPassword(JasyptUtil.decode(encryptor, serverDefine.getPassword()));
			map.put(serverDefine.getServerName(), serverDefine);
		}
		return map;
	}

	public static Map<String, JcoDefine> getJcoDefineMap(StandardPBEStringEncryptor encryptor) {
		InputStream input = null;
		try {
			input = new FileInputStream(new File(PATH_JCO));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Jco jco = new Yaml().loadAs(input, Jco.class);
		Map<String, JcoDefine> map = new HashMap<>();
		for (JcoDefine jcoDefine : jco.getJcoDefine()) {
			jcoDefine.setUser(JasyptUtil.decode(encryptor, jcoDefine.getUser()));
			jcoDefine.setPasswd(JasyptUtil.decode(encryptor, jcoDefine.getPasswd()));
			map.put(jcoDefine.getDestinationName(), jcoDefine);
		}

		return map;
	}

	public static Map<String, MailServerDefine> getMailServerDefineMap(StandardPBEStringEncryptor encryptor) {
		InputStream input = null;
		try {
			input = new FileInputStream(new File(PATH_MAIL));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Mail server = new Yaml().loadAs(input, Mail.class);
		Map<String, MailServerDefine> map = new HashMap<>();
		for (MailServerDefine serverDefine : server.getMailServerDefine()) {
			serverDefine.setUser(JasyptUtil.decode(encryptor, serverDefine.getUser()));
			serverDefine.setPassword(JasyptUtil.decode(encryptor, serverDefine.getPassword()));
			map.put(serverDefine.getServerName(), serverDefine);
		}
		return map;
	}

	public static Map<String, MailGroup> getMailGroupMap() {
		InputStream input = null;
		try {
			input = new FileInputStream(new File(PATH_MAIL));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Mail server = new Yaml().loadAs(input, Mail.class);
		Map<String, MailGroup> map = new HashMap<>();
		for (MailGroup mailGroup : server.getMailGroup()) {
			map.put(mailGroup.getGroupName(), mailGroup);
		}
		return map;
	}

	public static Job getJob(String path) {
		InputStream input = null;
		try {
			input = new FileInputStream(new File(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Job job = new Yaml().loadAs(input, Job.class);
		return job;
	}

}
