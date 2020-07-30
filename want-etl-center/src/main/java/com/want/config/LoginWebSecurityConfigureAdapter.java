package com.want.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.want.service.token.TokenService;

@Configuration
@EnableWebSecurity
public class LoginWebSecurityConfigureAdapter extends WebSecurityConfigurerAdapter {

	@Autowired
	private TokenService tokenService;

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.headers().frameOptions().disable();
		httpSecurity.cors().and().csrf().disable();
		httpSecurity.authorizeRequests().antMatchers(HttpMethod.POST, "/login/**").permitAll();
		httpSecurity.authorizeRequests().anyRequest().authenticated();
		httpSecurity
				.addFilterBefore(new LoginAuthorizationFilter(authenticationManager(), tokenService),
						UsernamePasswordAuthenticationFilter.class)
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/static/**", "/swagger-ui/**", "/swagger-resources/**",
				"/swagger-ui.html", "/api-docs", "/api-docs/**", "/v2/api-docs/**", "/**/*.css", "/**/*.js",
				"/**/*.png", "/**/*.jpg", "/**/*.jpeg", "/**/*.gif", "/**/*.svg", "/**/*.ico", "/**/*.ttf",
				"/**/*.woff", "/**/*.woff2", "/**/*.otf", "/**/*.map");

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
