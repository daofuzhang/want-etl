/**
 * -------------------------------------------------------
 * @FileName：JasyptFactory.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.util;

import java.io.IOException;
import java.util.Properties;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;

public class JasyptUtil {
	
	public static StandardPBEStringEncryptor getStandardPBEStringEncryptor() {
		Properties properties = new Properties();
		try {
			properties.load(JasyptUtil.class.getClassLoader().getResourceAsStream("config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String algorithm = properties.get("algorithm").toString();
		String passwordEnvName = properties.get("passwordEnvName").toString();
		
		EnvironmentStringPBEConfig config = new EnvironmentStringPBEConfig();
		config.setAlgorithm(algorithm);
		config.setPasswordEnvName(passwordEnvName);
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setConfig(config);
		return encryptor;
	}
	
	public static StandardPBEStringEncryptor getStandardPBEStringEncryptor(String algorithm, String passwordEnvName) {
		EnvironmentStringPBEConfig config = new EnvironmentStringPBEConfig();
		config.setAlgorithm(algorithm);
		config.setPasswordEnvName(passwordEnvName);
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setConfig(config);
		return encryptor;
	}
	
	public static String decode(StandardPBEStringEncryptor encryptor, String string) {
		if (encryptor != null && string.startsWith("ENC(") && string.endsWith(")")) {
			return encryptor.decrypt(string.substring(string.indexOf("(") + 1, string.indexOf(")")));
		}
		return string;
	}

}
