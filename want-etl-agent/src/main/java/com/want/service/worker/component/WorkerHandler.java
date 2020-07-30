/**
 * -------------------------------------------------------
 * @FileName：JobWorkerHandler.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.service.worker.component;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.function.Consumer;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.want.domain.log.JobLog;
import com.want.util.RSAUtil;

public class WorkerHandler implements Runnable {

	public @interface WorkerEvent {

		public static final int JOB_START = 0;

		public static final int JOB_END = 1;

		public static final int JOB_INTERRUPT = 2;

		public static final int WORKER_OUTPUT = 3;

		public static final int WORKER_ERROR = 4;
	}

	public interface WorkerHandlerListener {

		void onWorkerEvent(JobLog jobLog, @WorkerEvent int event, String message);

	}

	final JobLog jobLog;

	final WorkerHandlerListener listener;

	public WorkerHandler(JobLog jobLog, WorkerHandlerListener listener) {
		this.jobLog = jobLog;
		this.listener = listener;
	}

	@Override
	public void run() {
		try {
			String jarPath = getClass().getResource("").toString().replaceAll("^.*file:", "").replaceAll("jar!.*",
					"jar");
			String encodeParameter = RSAUtil.encodeString(RSAUtil.PUBLIC_KEY, jobLog.getParameter());
			runCommand(Paths.get(jarPath).getParent(), "java", "-cp", "want-etl-worker.jar:resources:lib/sapjco3.jar",
					"com.want.App", encodeParameter);
		} catch (InvalidKeyException | InvalidKeySpecException | NoSuchAlgorithmException | NoSuchPaddingException
				| IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (AssertionError e) {
			e.printStackTrace();
			listener.onWorkerEvent(jobLog, WorkerEvent.JOB_INTERRUPT, null);
			return;
		}
		listener.onWorkerEvent(jobLog, WorkerEvent.JOB_END, null);

	}

	void runCommand(Path directory, String... command) throws IOException, InterruptedException, AssertionError {
		Objects.requireNonNull(directory, "directory");
		if (!Files.exists(directory)) {
			throw new RuntimeException("can't run command in non-existing directory '" + directory + "'");
		}
		ProcessBuilder pb = new ProcessBuilder().command(command).directory(directory.toFile());
		Process p = pb.start();

		jobLog.setPid(String.valueOf(getProcessID(p)));
		jobLog.setStartTime(new Timestamp(System.currentTimeMillis()));
		jobLog.setEndTime(null);
		listener.onWorkerEvent(jobLog, WorkerEvent.JOB_START, null);

		StreamGobbler outputGobbler = new StreamGobbler(p.getInputStream(), new Consumer<String>() {

			@Override
			public void accept(String message) {
				listener.onWorkerEvent(jobLog, WorkerEvent.WORKER_OUTPUT, message);
			}
		});
		outputGobbler.start();

		StreamGobbler errorGobbler = new StreamGobbler(p.getErrorStream(), new Consumer<String>() {

			@Override
			public void accept(String message) {
				listener.onWorkerEvent(jobLog, WorkerEvent.WORKER_ERROR, message);
			}
		});
		errorGobbler.start();

		int exit = p.waitFor();
		jobLog.setEndTime(new Timestamp(System.currentTimeMillis()));

		outputGobbler.join();
		errorGobbler.join();
		if (exit != 0) {
			throw new AssertionError(String.format("runCommand returned %d", exit));
		}
	}

	long getProcessID(Process p) {
		long result = -1;
		try {
			if (p.getClass().getName().equals("java.lang.UNIXProcess")) {
				Field f = p.getClass().getDeclaredField("pid");
				f.setAccessible(true);
				result = f.getLong(p);
				f.setAccessible(false);
			}
//			else if (p.getClass().getName().equals("java.lang.Win32Process")
//					|| p.getClass().getName().equals("java.lang.ProcessImpl")) {
//				Field f = p.getClass().getDeclaredField("handle");
//				f.setAccessible(true);
//				long handl = f.getLong(p);
//				Kernel32 kernel = Kernel32.INSTANCE;
//				WinNT.HANDLE hand = new WinNT.HANDLE();
//				hand.setPointer(Pointer.createConstant(handl));
//				result = kernel.GetProcessId(hand);
//				f.setAccessible(false);
//			}
		} catch (Exception ex) {
			result = -1;
		}
		return result;
	}

}
