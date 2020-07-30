/**
 * -------------------------------------------------------
 * @FileName：JCOUtil.java
 * @Description：简要描述本文件的内容
 * @Author：Dirk.Lee
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.worker.sync.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoField;
import com.sap.conn.jco.JCoFieldIterator;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoMetaData;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;
import com.want.worker.dto.FormDTO;
import com.want.worker.dto.JCOServerDTO;
import com.want.worker.jco.RfcManager;

/**
 * @author 80005151
 *
 */
public class JcoUtil {
	public static List<String> getColumnLabels(JCOServerDTO jcoServer, String tableName, List<FormDTO> forms) {
		List<String> columnLabels = null;
		try {
			RfcManager.getDestination(jcoServer);
		} catch (JCoException e) {
			e.printStackTrace();
		}
		JCoFunction function = RfcManager.getFunction(tableName);
		for (FormDTO form : forms) {
			if (form != null && form.getVal() != null) {
				function.getImportParameterList().setValue(form.getKey(), form.getVal());
			} else {
				JCoFieldIterator iter = function.getImportParameterList().getFieldIterator();
				while (iter.hasNextField()) {
					JCoField jCoField = iter.nextField();
					if (jCoField.getName().equals(form.getKey()) && jCoField.isStructure()) {
						for (FormDTO sub : form.getValue()) {
							JCoStructure structure = jCoField.getStructure();
							structure.setValue(sub.getKey().toString(), sub.getVal().toString());
						}
					}
				}
			}
		}

		RfcManager.execute(function);

		JCoParameterList params = function.getExportParameterList();
		if (params != null) {
			for (JCoField jCoField : params) {
				System.out.println(jCoField.getName() + ":" + jCoField.getString());
			}
		}

		JCoTable tables = function.getTableParameterList().getTable("OT_OUTPUT");

		JCoMetaData jcomd = tables.getMetaData();
		String fields[] = new String[jcomd.getFieldCount()];
		for (int i = 0; i < jcomd.getFieldCount(); i++) {
			fields[i] = jcomd.getName(i);
		}

		if (fields != null) {
			columnLabels = Arrays.asList(fields);
		}

		return columnLabels;
	}

	public static List<Map<String, String>> getSyncData(JCOServerDTO jcoServer, String tableName, List<FormDTO> forms) {
		try {
			RfcManager.getDestination(jcoServer);
		} catch (JCoException e) {
			e.printStackTrace();
		}
		JCoFunction function = RfcManager.getFunction(tableName);
		for (FormDTO form : forms) {
			if (form != null && form.getVal() != null) {
				function.getImportParameterList().setValue(form.getKey(), form.getVal());
			} else {
				JCoFieldIterator iter = function.getImportParameterList().getFieldIterator();
				while (iter.hasNextField()) {
					JCoField jCoField = iter.nextField();
					if (jCoField.getName().equals(form.getKey()) && jCoField.isStructure()) {
						for (FormDTO sub : form.getValue()) {
							JCoStructure structure = jCoField.getStructure();
							structure.setValue(sub.getKey().toString(), sub.getVal().toString());
						}
						// Iterator<Entry<String, String>> fieldIter = map.entrySet().iterator();
						// JCoStructure structure = jCoField.getStructure();
						// while (fieldIter.hasNext()) {
						// Entry<String, String> entry = fsieldIter.next();
						// structure.setValue(entry.getKey().toString(), entry.getValue().toString());
						// }
					}
				}
			}
		}

		RfcManager.execute(function);

		JCoParameterList params = function.getExportParameterList();
		if (params != null) {
			for (JCoField jCoField : params) {
				System.out.println(jCoField.getName() + ":" + jCoField.getString());
			}
		}

		JCoTable tables = function.getTableParameterList().getTable("OT_OUTPUT");

		JCoMetaData jcomd = tables.getMetaData();
		String fields[] = new String[jcomd.getFieldCount()];
		for (int i = 0; i < jcomd.getFieldCount(); i++) {
			fields[i] = jcomd.getName(i);
		}

		List<Map<String, String>> rows = new ArrayList<>();
		for (int i = 0; i < tables.getNumRows(); i++) {
			tables.setRow(i);
			Map<String, String> row = new LinkedHashMap<>(jcomd.getFieldCount());
			for (int j = 0; j < fields.length; j++) {
				row.put(fields[j], tables.getString(fields[j]));
			}
			rows.add(row);
		}

		return rows;
	}
}
