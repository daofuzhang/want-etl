/**
 * -------------------------------------------------------
 * @FileName：Request.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.service.center.component;

public class JobRequst implements Comparable<JobRequst> {

	private final Object mLock = new Object();

	private Integer mSequence;

	private JobRequestQueue mRequestQueue;

	private boolean mCanceled = false;

	private Object mTag;

	private final Priority mPriority;

	private final String mLogId;

	private JobRequst(String logId, Priority priority) {
		mPriority = priority;
		mLogId = logId;
	}

	public static JobRequst create(String jobLogId, Priority priority) {
		return new JobRequst(jobLogId, priority);
	}

	public String getLogId() {
		return mLogId;
	}

	public JobRequst setTag(Object tag) {
		mTag = tag;
		return this;
	}

	public Object getTag() {
		return mTag;
	}

	void finish(final String tag) {
		if (mRequestQueue != null) {
			mRequestQueue.finish(this);
		}
	}

	void sendEvent(@JobRequestQueue.JobRequestEvent int event) {
		if (mRequestQueue != null) {
			mRequestQueue.sendJobRequestEvent(this, event);
		}
	}

	public JobRequst setRequestQueue(JobRequestQueue requestQueue) {
		mRequestQueue = requestQueue;
		return this;
	}

	public final JobRequst setSequence(int sequence) {
		mSequence = sequence;
		return this;
	}

	public final int getSequence() {
		if (mSequence == null) {
			throw new IllegalStateException("getSequence called before setSequence");
		}
		return mSequence;
	}

	public void cancel() {
		synchronized (mLock) {
			mCanceled = true;
		}
	}

	public boolean isCanceled() {
		synchronized (mLock) {
			return mCanceled;
		}
	}

	public enum Priority {
		LOW(0), NORMAL(1), HIGH(2), IMMEDIATE(3);

		final int code;

		Priority(int code) {
			this.code = code;
		}

		public int getCode() {
			return code;
		}

		public static Priority fromCode(int code) {
			for (Priority p : Priority.values()) {
				if (p.code == code) {
					return p;
				}
			}
			return null;
		}
	}

	public Priority getPriority() {
		return mPriority;
	}

	@Override
	public int compareTo(JobRequst other) {
		Priority left = this.getPriority();
		Priority right = other.getPriority();
		return left == right ? this.mSequence - other.mSequence : right.ordinal() - left.ordinal();
	}

	@Override
	public String toString() {
		return (isCanceled() ? "[X] " : "[ ] ") + " " + getPriority() + " " + mSequence;
	}

}
