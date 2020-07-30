/**
 * -------------------------------------------------------
 * @FileName：RSAUtil.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;

public class RSAUtil {

	public final static String ALGORITHM = "RSA";
	public final static String PUBLIC_KEY = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJT+snkj5nx7MUrEpduzvwaq49C8Ezjltp79fU91K5Erw4R2hz4wLcsfTRagfXQyaX445V0zKv76QdIbASn85p0CAwEAAQ==";
	public final static String PRIVATE_KEY = "MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEAlP6yeSPmfHsxSsSl27O/Bqrj0LwTOOW2nv19T3UrkSvDhHaHPjAtyx9NFqB9dDJpfjjlXTMq/vpB0hsBKfzmnQIDAQABAkBlUq/cYeWkUcK7re988Ue/KQe0M7J+xvjiNlC5cF0oYyf/RUJoOczxdhtb9bpbB3FDAcLe5y0RCPge3LAHFlOhAiEA1MuiARQwC6nzZmP2uY536d14nA7uCZtUgrcaX1d8gjMCIQCzPu9CNxKfVKqcGwO0FeBK6Bj8hAW9TdTXRv9CiqRD7wIhAKLU07npnb4/JS6Tjcd9ukqtm04nqoSVZLzFqhuM0Yy7AiEAp7LS4G1sI9tvv5tarKrm7M4XSnEueX0PC9ZlzViu9MsCIEkJdGSteKVJSzkbOBEAL9hd+t0kW4OIYMPLUD1HW0CB";

	public static String encodeString(String rsaPublicKey, String string)
			throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException, IOException {
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(rsaPublicKey));
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
		RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(x509EncodedKeySpec);
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);

		byte[] bytes = string.getBytes();
		int inputLen = bytes.length;
		int offLen = 0;
		int i = 0;
		ByteArrayOutputStream bops = new ByteArrayOutputStream();
		while (inputLen - offLen > 0) {
			byte[] cache;
			if (inputLen - offLen > 53) {
				cache = cipher.doFinal(bytes, offLen, 53);
			} else {
				cache = cipher.doFinal(bytes, offLen, inputLen - offLen);
			}
			bops.write(cache);
			i++;
			offLen = 53 * i;
		}
		byte[] encryptedData = bops.toByteArray();
		bops.close();
		String encodeToString = new String(Base64.encodeBase64(encryptedData));
		return encodeToString;
	}

	public static String decodeString(String rsaPrivateKey, String encodeString)
			throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException, IOException {
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(rsaPrivateKey));
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
		RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8EncodedKeySpec);
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, privateKey);

		byte[] bytes = Base64.decodeBase64(encodeString);
		int inputLen = bytes.length;
		int offLen = 0;
		int i = 0;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		while (inputLen - offLen > 0) {
			byte[] cache;
			if (inputLen - offLen > 64) {
				cache = cipher.doFinal(bytes, offLen, 64);
			} else {
				cache = cipher.doFinal(bytes, offLen, inputLen - offLen);
			}
			byteArrayOutputStream.write(cache);
			i++;
			offLen = 64 * i;

		}
		byte[] byteArray = byteArrayOutputStream.toByteArray();
		byteArrayOutputStream.close();
		return new String(byteArray);
	}

	public static void main(String[] args)
			throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException, IOException {
		String msg = "芝麻開門";
//		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
//		keyPairGenerator.initialize(512);
//		KeyPair keyPair = keyPairGenerator.generateKeyPair();
//		PublicKey publicKey = keyPair.getPublic();
//		PrivateKey privateKey = keyPair.getPrivate();
//		String rsaPublicKey = Base64.encodeBase64String(publicKey.getEncoded());
//		String rsaPrivateKey = Base64.encodeBase64String(privateKey.getEncoded());
//		System.out.println("rsaPublicKey: " + rsaPublicKey);
//		System.out.println("rsaPrivateKey: " + rsaPrivateKey);

		String enstr = RSAUtil.encodeString(PUBLIC_KEY, msg);
		String destr = RSAUtil.decodeString(PRIVATE_KEY, enstr);

		System.out.println("明文是:" + msg);
		System.out.println("加密後:" + enstr);
		System.out.println("解密後:" + destr);
		
		System.out.println("解密後:" + RSAUtil.decodeString(PRIVATE_KEY, "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"));

	}
}
