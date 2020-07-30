/**
 * -------------------------------------------------------
 * @FileName：JCOProvider.java
 * @Description：简要描述本文件的内容
 * @Author：Dirk.Lee
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.worker.jco;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.sap.conn.jco.ext.DataProviderException;
import com.sap.conn.jco.ext.DestinationDataEventListener;
import com.sap.conn.jco.ext.DestinationDataProvider;

public class JCOProvider implements DestinationDataProvider {

	private Map<String, Properties> secureDBStorage = new HashMap<String, Properties>();
	private DestinationDataEventListener eventListener;

	@Override
	public Properties getDestinationProperties(String destinationName) {
		try {
			Properties p = secureDBStorage.get(destinationName);
			if (p != null) {
				if (p.isEmpty())
					throw new DataProviderException(DataProviderException.Reason.INVALID_CONFIGURATION,
							"destination configuration is incorrect", null);

				return p;
			}
			return null;
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void setDestinationDataEventListener(DestinationDataEventListener eventListener) {
		this.eventListener = eventListener;
		System.out.println("eventListener assigned ! ");
	}

	@Override
	public boolean supportsEvents() {
		return true;
	}

	public void changeProperties(String destName, Properties properties) {
		synchronized (secureDBStorage) {
			if (properties == null) {
				if (secureDBStorage.remove(destName) != null)
					eventListener.deleted(destName);
			} else {
				secureDBStorage.put(destName, properties);
				eventListener.updated(destName);
			}
		}
	}

}