/**
 * -------------------------------------------------------
 * @FileName：SettingService.java
 * @Description：简要描述本文件的内容
 * @Author：Dirk.Lee
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.service.setting;

import java.util.List;

import com.want.domain.account.Account;
import com.want.domain.batchlogger.BatchLogger;
import com.want.domain.mail.Mail;
import com.want.domain.mail.MailGroup;
import com.want.domain.server.JCOServer;
import com.want.domain.server.JDBCServer;

/**
 * @author 80005151
 *
 */
public interface SettingService {
	public List<String> getDataBaseTypes();

	public List<JDBCServer> getJDBCServers();

	public List<JCOServer> getJCOServers();

	public List<JDBCServer> getOracleServers();

	public List<MailGroup> getMailGroups(int offset, int limit);

	public List<Mail> getMailByMailGroupId(String id);

	public List<Account> getAccounts(int offset, int limit);

	public long getTotalAccountCount();

	public long getTotalMailGroupCount();

	public Mail getMail(String mail);
	
	public BatchLogger getBatchLogger();

	public void saveJDBCServer(JDBCServer jdbcServer);

	public void saveJCOServer(JCOServer jcoServer);

	public void saveMailGroup(MailGroup mailGroup);

	public void saveMailGroupMembers(MailGroup mailGroup);

	public void saveAccount(Account account, String mailAddress);

	public void saveBatchLogger(BatchLogger batchLogger);

	public void deleteJDBCServer(String id);

	public void deleteJCOServer(String id);

	public void deleteMailGroup(String id);

	public void deleteAccount(String id);
}
