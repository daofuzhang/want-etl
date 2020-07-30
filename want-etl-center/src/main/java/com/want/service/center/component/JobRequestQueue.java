/**
 * -------------------------------------------------------
 * @FileName：RequestQueue.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.service.center.component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import com.want.mapper.AgentMapper;

public class JobRequestQueue {

	public @interface JobRequestEvent {

		public static final int JOB_REQUEST_QUEUED = 0;

		public static final int AGENT_DISPATCH_STARTED = 1;

		public static final int AGENT_DISPATCH_FINISHED = 2;

		public static final int JOB_REQUEST_FINISHED = 3;

	}

	public interface JobRequestEventListener {
		void onJobRequestEvent(JobRequst request, @JobRequestEvent int event);
	}

	private final AtomicInteger mSequenceGenerator = new AtomicInteger();

	private final Set<JobRequst> mCurrentRequests = new HashSet<>();

	private final PriorityBlockingQueue<JobRequst> mRequestQueue = new PriorityBlockingQueue<>();

	private final AgentMapper mAgentMapper;
	
	private AgentDispatcher mRequestDispatcher;

	private final List<JobRequestEventListener> mEventListeners = new ArrayList<>();

	public interface JobFilter {
		boolean apply(JobRequst request);
	}
	
	public JobRequestQueue(AgentMapper agentMapper) {
		mAgentMapper = agentMapper;
    }

	public void start() {
		stop();
		mRequestDispatcher = new AgentDispatcher(mRequestQueue, mAgentMapper);
		mRequestDispatcher.start();
	}

	public void stop() {
		if (mRequestDispatcher != null) {
			mRequestDispatcher.quit();
			mRequestDispatcher = null;
		}
	}

	public int getSequenceNumber() {
		return mSequenceGenerator.incrementAndGet();
	}

	public void cancelAll(JobFilter filter) {
		synchronized (mCurrentRequests) {
			for (JobRequst request : mCurrentRequests) {
				if (filter.apply(request)) {
					request.cancel();
				}
			}
		}
	}

	public void cancelAll(final Object tag) {
		if (tag == null) {
			throw new IllegalArgumentException("Cannot cancelAll with a null tag");
		}
		cancelAll(new JobFilter() {
			@Override
			public boolean apply(JobRequst request) {
				return request.getTag() == tag;
			}
		});
	}

	public JobRequst add(JobRequst request) {
		request.setRequestQueue(this);
		synchronized (mCurrentRequests) {
			mCurrentRequests.add(request);
		}
		request.setSequence(getSequenceNumber());
		sendJobRequestEvent(request, JobRequestEvent.JOB_REQUEST_QUEUED);
		mRequestQueue.add(request);
		return request;
	}

	<T> void finish(JobRequst request) {
		synchronized (mCurrentRequests) {
			mCurrentRequests.remove(request);
		}
		sendJobRequestEvent(request, JobRequestEvent.JOB_REQUEST_FINISHED);
	}

	void sendJobRequestEvent(JobRequst request, @JobRequestEvent int event) {
		synchronized (mEventListeners) {
			for (JobRequestEventListener listener : mEventListeners) {
				listener.onJobRequestEvent(request, event);
			}
		}
	}

	public void addJobRequestEventListener(JobRequestEventListener listener) {
		synchronized (mEventListeners) {
			mEventListeners.add(listener);
		}
	}

	public void removeJobRequestEventListener(JobRequestEventListener listener) {
		synchronized (mEventListeners) {
			mEventListeners.remove(listener);
		}
	}

}
