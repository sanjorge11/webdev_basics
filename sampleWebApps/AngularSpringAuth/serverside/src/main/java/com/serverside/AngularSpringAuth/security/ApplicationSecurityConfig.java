package com.serverside.AngularSpringAuth.security;



import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.serverside.AngularSpringAuth.auth.ApplicationUserService;
import com.serverside.AngularSpringAuth.jwt.JwtConfig;
import com.serverside.AngularSpringAuth.jwt.JwtTokenVerifier;
import com.serverside.AngularSpringAuth.jwt.JwtUsernameAndPasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity  
@EnableGlobalMethodSecurity(prePostEnabled = true)			
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

	private final PasswordEncoder passwordEncoder;
	private final ApplicationUserService applicationUserService; 
	
	private final SecretKey secretKey; 
	private final JwtConfig jwtConfig;
	
	@Autowired
	public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, ApplicationUserService applicationUserService,
			SecretKey secretKey, JwtConfig jwtConfig) {
		this.passwordEncoder = passwordEncoder;
		this.applicationUserService = applicationUserService;
		this.secretKey = secretKey;
		this.jwtConfig = jwtConfig;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
		.csrf().disable()
		
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		
		.addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig, secretKey))
		
		.addFilterAfter(new JwtTokenVerifier(secretKey, jwtConfig), JwtUsernameAndPasswordAuthenticationFilter.class)	//apply this filter after the filter above
		
		.authorizeRequests()
		.antMatchers("/", "index", "/css/*", "/js/*").permitAll()  
		.antMatchers("/students/**").hasRole(UserRole.STUDENT.name()) 
		.antMatchers(HttpMethod.GET, "/management/students/**").hasAnyRole(UserRole.ADMIN.name(), UserRole.ADMINTRAINEE.name())	
		.anyRequest()	
		.authenticated();
	
	}
	
	
	@Bean 
	public DaoAuthenticationProvider daoAuthenticationProvider() { 
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider(); 
		provider.setPasswordEncoder(passwordEncoder);	
		provider.setUserDetailsService(applicationUserService);
		return provider;
	}

	@Override		//override AuthenticationManager for the app with the passwordEncoder and UserDetailsService
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider());
	}
	
}
