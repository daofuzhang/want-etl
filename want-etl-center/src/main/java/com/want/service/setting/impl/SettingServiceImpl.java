/**
 * -------------------------------------------------------
 * @FileName：SettingServiceImpl.java
 * @Description：简要描述本文件的内容
 * @Author：Dirk.Lee
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.service.setting.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.want.domain.account.Account;
import com.want.domain.batchlogger.BatchLogger;
import com.want.domain.mail.Mail;
import com.want.domain.mail.MailGroup;
import com.want.domain.server.JCOServer;
import com.want.domain.server.JDBCServer;
import com.want.domain.setting.DataBaseType;
import com.want.mapper.AccountMapper;
import com.want.mapper.BatchLoggerMapper;
import com.want.mapper.JCOServerMapper;
import com.want.mapper.JDBCServerMapper;
import com.want.mapper.MailGroupMapper;
import com.want.mapper.MailMapper;
import com.want.service.setting.SettingService;
import com.want.util.StringUtil;

/**
 * @author 80005151
 *
 */
@Service
public class SettingServiceImpl implements SettingService {
	@Autowired
	private JDBCServerMapper jdbcServerMapper;
	@Autowired
	private JCOServerMapper jcoServerMapper;
	@Autowired
	private MailGroupMapper mailGroupMapper;
	@Autowired
	private MailMapper mailMapper;
	@Autowired
	private AccountMapper accountMapper;
	@Autowired
	private BatchLoggerMapper batchLoggerMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.want.service.job.SettingService#getDataBaseTypes()
	 */
	@Override
	public List<String> getDataBaseTypes() {
		return Arrays.asList(DataBaseType.MYSQL.getName(), DataBaseType.ORACLE.getName(), DataBaseType.HANA.getName(),
				DataBaseType.JCO.getName());
	}

	@Override
	public List<JDBCServer> getJDBCServers() {
		return jdbcServerMapper.findAll();
	}

	@Override
	public List<JCOServer> getJCOServers() {
		return jcoServerMapper.findAll();
	}

	@Override
	public List<JDBCServer> getOracleServers() {
		return jdbcServerMapper.findOracles();
	}

	@Override
	public List<MailGroup> getMailGroups(int offset, int limit) {
		return mailGroupMapper.findAllByLimit(offset, limit);
	}

	@Override
	public List<Mail> getMailByMailGroupId(String id) {
		List<Mail> list = null;
		MailGroup mailGroup = mailGroupMapper.findOne(id);
		if (mailGroup != null) {
			String[] ids = mailGroup.getToMailIds().split(",");
			list = mailMapper.findByIds(Arrays.asList(ids));
		}

		return list;
	}

	@Override
	public List<Account> getAccounts(int offset, int limit) {
		return accountMapper.findAllWithEmail(offset, limit);
	}

	@Override
	public long getTotalAccountCount() {
		return accountMapper.getCount();
	}

	@Override
	public long getTotalMailGroupCount() {
		return mailGroupMapper.getCount();
	}

	@Override
	public Mail getMail(String mail) {
		return mailMapper.findByName(mail);
	}
	
	@Override
	public BatchLogger getBatchLogger() {
		return batchLoggerMapper.findOne();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveJDBCServer(JDBCServer jdbcServer) {
		if (StringUtil.isEmpty(jdbcServer.getId())) {
			jdbcServerMapper.insert(jdbcServer);
		} else {
			if (StringUtil.isEmpty(jdbcServer.getPassword())) {
				JDBCServer j = jdbcServerMapper.findOne(jdbcServer.getId());
				jdbcServer.setPassword(j == null || j.getPassword().isEmpty() ? "" : j.getPassword());
			}
			jdbcServerMapper.update(jdbcServer);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveJCOServer(JCOServer jcoServer) {
		if (StringUtil.isEmpty(jcoServer.getId())) {
			jcoServerMapper.insert(jcoServer);
		} else {
			if (StringUtil.isEmpty(jcoServer.getPasswd())) {
				JCOServer j = jcoServerMapper.find(jcoServer.getId());
				jcoServer.setPasswd(j == null || j.getPasswd().isEmpty() ? "" : j.getPasswd());
			}
			jcoServerMapper.update(jcoServer);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveMailGroup(MailGroup mailGroup) {
		if (StringUtil.isEmpty(mailGroup.getId())) {
			mailGroupMapper.insert(mailGroup);
		} else {
			mailGroupMapper.update(mailGroup);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveMailGroupMembers(MailGroup mailGroup) {
		mailGroupMapper.updateToMailIds(mailGroup);
	}

	@Override
	public void saveAccount(Account account, String mailAddress) {
		Mail upsertMail = new Mail();
		if (account.getId() != null && !account.getId().isEmpty()) {
			String existedMailId = "";
			Account temp = accountMapper.findOne(account.getId());
			if (temp != null) {
				existedMailId = temp.getMailId();
			}

			if (existedMailId != null && !existedMailId.isEmpty()) {
				if (mailMapper.findOne(existedMailId) != null) {
					upsertMail.setId(existedMailId);
				}
			}
		}

		upsertMail.setName(account.getName());
		upsertMail.setMail(mailAddress);

		if (upsertMail.getId() == null || upsertMail.getId().isEmpty()) {
			mailMapper.insert(upsertMail);
		} else {
			mailMapper.update(upsertMail);
		}

		account.setMailId(upsertMail.getId());
		accountMapper.insert(account);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveBatchLogger(BatchLogger batchLogger) {
		// TODO Auto-generated method stub
		batchLoggerMapper.deleteAll();
		batchLoggerMapper.insert(batchLogger);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteJDBCServer(String id) {
		jdbcServerMapper.delete(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteJCOServer(String id) {
		jcoServerMapper.delete(id);
	}

	@Override
	public void deleteMailGroup(String id) {
		mailGroupMapper.delete(id);
	}

	@Override
	public void deleteAccount(String id) {
		Account account = accountMapper.findOne(id);
		mailMapper.delete(account.getMailId());
		accountMapper.delete(id);
	}



}
