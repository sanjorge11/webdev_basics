package com.serverside.SpringSecurity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity  //this lets Spring know the class will configure everything about security for our web application
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
//1.35.50
	private final PasswordEncoder passwordEncoder; 
	
	@Autowired	//instantiate current object by @Autowired with an encoder
	public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) { 
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		.authorizeRequests()
		.antMatchers("/", "index", "/css/*", "/js/*").permitAll()  //white-listed patterns, permit all that match
		.antMatchers("/students/**").hasRole(ApplicationUserRole.STUDENT.name()) //this endpoint can only be accessed by users with STUDENT role
		.antMatchers(HttpMethod.DELETE, "/management/students/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.name()) //fine-grained security with authorities
		.antMatchers(HttpMethod.POST, "/management/students/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.name())
		.antMatchers(HttpMethod.PUT, "/management/students/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.name())
		.antMatchers(HttpMethod.GET, "/management/students/**").hasAnyRole(ApplicationUserRole.ADMIN.name(), ApplicationUserRole.ADMINTRAINEE.name())	//course-grained permissions with roles
		.anyRequest()	//every request
		.authenticated()	//must be authenticated
		.and()			//and use
		.httpBasic();		//httpBasicAuth
	}


	@Override	//this is used for retrieving users from database
	@Bean  //add this @Bean to instantiate this service on startup; will build sample user on startup
	protected UserDetailsService userDetailsService() {
		UserDetails sampleUser = User.builder()
				.username("John")
				.password(passwordEncoder.encode("password")) 
				.roles(ApplicationUserRole.STUDENT.name()) //will be resolved as ROLE_STUDENT in spring boot
				.build();									//"STUDENT" is the name of the enum 
		
		UserDetails sampleAdmin = User.builder()
				.username("Jane")
				.password(passwordEncoder.encode("password2")) 
				.roles(ApplicationUserRole.ADMIN.name()) 
				.build();
		
		UserDetails sampleAdminTrainee = User.builder()
				.username("Jordan")
				.password(passwordEncoder.encode("password2")) 
				.roles(ApplicationUserRole.ADMINTRAINEE.name()) 
				.build();
				
		return new InMemoryUserDetailsManager(
				sampleUser,
				sampleAdmin,
				sampleAdminTrainee
		);
	}
	
	//not adding PasswordEncoder will cause this Exception
	//java.lang.IllegalArgumentException: There is no PasswordEncoder mapped for the id "null"
	//passwords must be encoded when handled in Spring Boot (and any other server-side)

	
	
}
