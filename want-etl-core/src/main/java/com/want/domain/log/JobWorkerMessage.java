/**
 * -------------------------------------------------------
 * @FileName：JobWorkerMessage.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.domain.log;

import java.sql.Timestamp;

public class JobWorkerMessage {

	public enum Type {

		NORMAL(0, "normal"), ERROR(1, "error");

		final int code;

		final String text;

		Type(int code, String text) {
			this.code = code;
			this.text = text;
		}

		public int getCode() {
			return code;
		}

		public String getText() {
			return text;
		}

		public static Type fromCode(int code) {
			for (Type s : Type.values()) {
				if (s.code == code) {
					return s;
				}
			}
			return null;
		}

		public static Type fromText(Type text) {
			for (Type s : Type.values()) {
				if (s.text.equals(text)) {
					return s;
				}
			}
			return null;
		}

	}

	private String jobLogId;
	private int type;
	private String message;
	private Timestamp createTime;

	public String getJobLogId() {
		return jobLogId;
	}

	public void setJobLogId(String jobLogId) {
		this.jobLogId = jobLogId;
	}

	public int getType() {
		return type;
	}

	public void setType(int code) {
		this.type = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public static JobWorkerMessage create(String id, Type type, String message) {
		JobWorkerMessage dao = new JobWorkerMessage();
		dao.setJobLogId(id);
		dao.setType(type.code);
		dao.setMessage(message);
		return dao;
	}

}
