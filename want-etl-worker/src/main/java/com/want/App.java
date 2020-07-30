/**
 * -------------------------------------------------------
 * @FileName：App.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.google.gson.Gson;
import com.want.util.RSAUtil;
import com.want.util.VelocityUtil;
import com.want.worker.JobWorker;
import com.want.worker.dto.JobDTO;

public class App {

	public static void main(String[] args) {
		Gson gson = new Gson();
		for (String arg : args) {
			if (arg == null || arg.isEmpty()) {
				continue;
			}
			String json = null;
			try {
				json = RSAUtil.decodeString(RSAUtil.PRIVATE_KEY, arg);
			} catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException
					| IllegalBlockSizeException | BadPaddingException | IOException e) {
				e.printStackTrace();
				continue;
			}
			JobDTO job = gson.fromJson(json, JobDTO.class);
			VelocityUtil.convert(job);
			JobWorker.run(job);
		}
	}

}
