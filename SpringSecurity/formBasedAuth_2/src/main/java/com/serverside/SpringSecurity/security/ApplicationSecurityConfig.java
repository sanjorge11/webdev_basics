package com.serverside.SpringSecurity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity  
@EnableGlobalMethodSecurity(prePostEnabled = true)			
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
//2.25.00	
	private final PasswordEncoder passwordEncoder; 
	
	@Autowired	
	public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) { 
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()		
		.authorizeRequests()
		.antMatchers("/", "index", "/css/*", "/js/*").permitAll()  
		.antMatchers("/students/**").hasRole(ApplicationUserRole.STUDENT.name()) 
		.antMatchers(HttpMethod.GET, "/management/students/**").hasAnyRole(ApplicationUserRole.ADMIN.name(), ApplicationUserRole.ADMINTRAINEE.name())	
		.anyRequest()	
		.authenticated()	
		.and()			
		.formLogin(); 	//specified as form-based authentication		
		//form-based auth has been the standard for web apps, it works by having the user login with a form,
		//the server validates the credentials and sends an OK status along with a cookie that contains 
		//the SESSIONID, any subsequent request will have the SESSIONID attached for the server to validate
		//and so then user will not need to enter username and password for every request, each subsequent 
		//request validates the SESSIONID, so each request will have a response 200 along with whatever user asked for 
		//the default lifespan for a SESSIONID in 30 min
		
		//by default, the SESSIONID is stored in an in-memory database in Spring-Boot, so a new SESSIONID will be created 
		//whenever the server restarts -- it is possible, however, to use external databases to store sessions, which is best practice
	}


	@Override	
	@Bean  
	protected UserDetailsService userDetailsService() {
		UserDetails sampleUser = User.builder()
				.username("John")
				.password(passwordEncoder.encode("password")) 
				.authorities(ApplicationUserRole.STUDENT.getGrantedAuthorities())  
				.build();
		
		
		UserDetails sampleAdmin = User.builder()
				.username("Jane")
				.password(passwordEncoder.encode("password2")) 
				.authorities(ApplicationUserRole.ADMIN.getGrantedAuthorities())
				.build();
		
		UserDetails sampleAdminTrainee = User.builder()
				.username("Jordan")
				.password(passwordEncoder.encode("password2")) 
				.authorities(ApplicationUserRole.ADMINTRAINEE.getGrantedAuthorities())
				.build();
				
		return new InMemoryUserDetailsManager(
				sampleUser,
				sampleAdmin,
				sampleAdminTrainee
		);
	}
	
	
}
