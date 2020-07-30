package com.want.worker.jco;

import java.util.Properties;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.ext.Environment;
import com.want.worker.dto.JCOServerDTO;

public class RfcManager {
	private static final String destinationName = "JOB_WORKER";
	private static JCoDestination destination;
	private static JCOProvider provider;

	static {
		try {
			provider = new JCOProvider();
			Environment.registerDestinationDataProvider(provider);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
	}

	// public static JCoDestination getDestination() throws JCoException {
	// return destination;
	// }

	public static JCoDestination getDestination(JCOServerDTO jcoServer) throws JCoException {
		if (destination == null) {
			provider.changeProperties(destinationName, loadProperties(jcoServer));
			destination = JCoDestinationManager.getDestination(destinationName);
		}
		return destination;
	}

	// public static Properties loadProperties() {
	// Properties prop = new Properties();
	//
	// StandardPBEStringEncryptor encryptor =
	// JasyptUtil.getStandardPBEStringEncryptor();
	// Map<String, JcoDefine> map = YamlUtil.getJcoDefineMap(encryptor);
	//
	// JcoDefine jcoDefine = map.get("TEST.JCO");
	// prop.setProperty("lang", jcoDefine.getLang());
	// prop.setProperty("passwd", jcoDefine.getPasswd());
	// prop.setProperty("sysnr", jcoDefine.getSysnr());
	// prop.setProperty("client", jcoDefine.getClient());
	// prop.setProperty("pool_capacity", jcoDefine.getPool_capacity());
	// prop.setProperty("user", jcoDefine.getUser());
	// prop.setProperty("peak_limit", jcoDefine.getPeak_limit());
	// prop.setProperty("ashost", jcoDefine.getAshost());
	// prop.setProperty("jco.client", "");
	// prop.setProperty("jco.destination", "");
	//
	// //
	// prop.load(RfcManager.class.getClassLoader().getResourceAsStream("jco.properties"));
	//
	// return prop;
	// }

	public static Properties loadProperties(JCOServerDTO jcoServer) {
		Properties prop = new Properties();
		prop.setProperty("lang", jcoServer.getLang());
		prop.setProperty("passwd", jcoServer.getPasswd());
		prop.setProperty("sysnr", jcoServer.getSysnr());
		prop.setProperty("client", jcoServer.getClient());
		prop.setProperty("pool_capacity", jcoServer.getPoolCapacity());
		prop.setProperty("user", jcoServer.getUser());
		prop.setProperty("peak_limit", jcoServer.getPeakLimit());
		prop.setProperty("ashost", jcoServer.getAshost());
		prop.setProperty("jco.client", "");
		prop.setProperty("jco.destination", "");

		return prop;
	}

	public static JCoFunction getFunction(String functionName) {
		JCoFunction function = null;
		try {
			function = destination.getRepository().getFunctionTemplate(functionName).getFunction();
		} catch (JCoException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return function;
	}

	public static void execute(JCoFunction function) {
		try {
			function.execute(destination);
		} catch (JCoException e) {
			e.printStackTrace();
		}
	}

}
