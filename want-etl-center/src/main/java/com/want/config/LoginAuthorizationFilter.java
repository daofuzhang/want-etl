package com.want.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import com.want.base.service.dto.AccountInfoDTO;
import com.want.domain.account.Account;
import com.want.service.token.TokenService;

public class LoginAuthorizationFilter extends BasicAuthenticationFilter {

	public final static String HEADER_AUTH_KEY = "authorization";
	public final static String ROLE_PREFIX = "ROLE_";

	final TokenService tokenService;

	public LoginAuthorizationFilter(AuthenticationManager authenticationManager, TokenService tokenService) {
		super(authenticationManager);
		this.tokenService = tokenService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String authorization = request.getHeader(HEADER_AUTH_KEY);
		SecurityContextHolder.getContext().setAuthentication(getAuthentication(authorization));
		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(String authorization) {
		if (StringUtils.isEmpty(authorization)) {
			return null;
		}
		Account account = tokenService.getAccount(authorization);
		if (account == null || !account.getEnable().equals("1")) {
			return null;
		}
		AccountInfoDTO accountInfo = new AccountInfoDTO();
		accountInfo.setAccountId(account.getId());
		accountInfo.setAccountName(account.getName());
		accountInfo.setRoleId(account.getRoleId());
		return new UsernamePasswordAuthenticationToken(accountInfo, null,
				new HashSet<>(Arrays.asList(new SimpleGrantedAuthority(ROLE_PREFIX + accountInfo.getRoleId()))));

	}
}