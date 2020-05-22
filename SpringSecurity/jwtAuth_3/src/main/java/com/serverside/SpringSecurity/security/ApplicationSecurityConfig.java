package com.serverside.SpringSecurity.security;

import java.util.concurrent.TimeUnit;

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
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.serverside.SpringSecurity.auth.ApplicationUserService;

@Configuration
@EnableWebSecurity  
@EnableGlobalMethodSecurity(prePostEnabled = true)			
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final PasswordEncoder passwordEncoder;
	private final ApplicationUserService applicationUserService; 
	
	@Autowired	
	public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, 
									 ApplicationUserService applicationUserService) { 
		this.passwordEncoder = passwordEncoder;
		this.applicationUserService = applicationUserService; 
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		//Java JWT implementation 
		// https://github.com/jwtk/jjwt
		
		http
		.csrf().disable()		
		.authorizeRequests()
		.antMatchers("/", "index", "/css/*", "/js/*").permitAll()  
		.antMatchers("/students/**").hasRole(ApplicationUserRole.STUDENT.name()) 
		.antMatchers(HttpMethod.GET, "/management/students/**").hasAnyRole(ApplicationUserRole.ADMIN.name(), ApplicationUserRole.ADMINTRAINEE.name())	
		.anyRequest()	
		.authenticated()	
		.and()			
		.formLogin() 	
		.loginPage("/login").permitAll()	
			.defaultSuccessUrl("/courses", true) 	
			.usernameParameter("username")		
			.passwordParameter("password")
		
		.and()
		.rememberMe() 	
			.tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21)) 
			.key("specialKey")		
		
			.rememberMeParameter("remember-me")  
			
		.and()
		.logout()
			.logoutUrl("/logout") 	
			.clearAuthentication(true)
			.invalidateHttpSession(true)
			.deleteCookies("JSESSIONID", "remember-me") 
			.logoutSuccessUrl("/login"); 	
	
	}
	
	
	@Bean 
	public DaoAuthenticationProvider daoAuthenticationProvider() { 
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider(); 
		provider.setPasswordEncoder(passwordEncoder);	
		provider.setUserDetailsService(applicationUserService);
		return provider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider());
	}
	
}
