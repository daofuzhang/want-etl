/**
 * -------------------------------------------------------
 * @FileName：SendMailUtils.java
 * @Description：简要描述本文件的内容
 * @Author：Dirk.Lee
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.worker.util;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;

public class MailUtil {

	public static void send(String host, String user, String password, String from, String[] to, String[] cc,
			String subject, String text, String[] fileName, byte[][] bytes) throws UnsupportedEncodingException {
		Properties properties = new Properties();
		properties.put("mail.host", host);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.transport.protocol", "smtp");

		Authenticator authenticator = new SimpleAuthenticator(user, password);
		Session session = Session.getInstance(properties, authenticator);
		MimeMessage mimeMsg = new MimeMessage(session);
		try {
			mimeMsg.setSubject(subject);
			mimeMsg.setFrom(new InternetAddress(from));

			if (to != null && to.length > 0) {
				for (String mail : to) {
					mimeMsg.addRecipient(RecipientType.TO, new InternetAddress(encodeAddressString(mail)));
				}
			}

			if (cc != null && cc.length > 0) {
				for (String mail : cc) {
					mimeMsg.addRecipient(RecipientType.CC,
							new InternetAddress(MimeUtility.encodeText(mail.substring(0, mail.indexOf("<") - 1))
									+ mail.substring(mail.indexOf("<"), mail.length())));
				}
			}

			Multipart multipart = new MimeMultipart();
			if (text != null) {
				MimeBodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart.setContent(text, "text/html;charset=UTF-8");
				multipart.addBodyPart(messageBodyPart);
			}
			if (fileName != null && bytes != null) {
				for (int i = 0; i < bytes.length; i++) {
					if (bytes[i] != null) {
						DataSource source = new ByteArrayDataSource(bytes[i], "application/excel");
						BodyPart dataBodyPart = new MimeBodyPart();
						dataBodyPart.setDataHandler(new DataHandler(source));
						dataBodyPart.setFileName(MimeUtility.encodeText(fileName[i]));
						multipart.addBodyPart(dataBodyPart);
					}
				}
			}
			mimeMsg.setContent(multipart);

			Transport.send(mimeMsg);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 編碼郵件地址。
	 * 
	 * @param mail
	 * @return
	 *
	 * @author Luke.Tsai
	 * @throws UnsupportedEncodingException
	 */
	private static String encodeAddressString(String address) throws UnsupportedEncodingException {
		if (address.contains("<") && address.contains(">")) {
			String name = MimeUtility.encodeText(address.substring(0, address.indexOf("<") - 1).trim());
			String email = address.substring(address.indexOf("<"), address.length());
			address = name + " " + email;
		}
		return address;
	}
}
