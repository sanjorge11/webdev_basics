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
	//4.01.51
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
		
		//JWT Authentication works by the client sending its sign-in information to the 
		//server, after validation, the server creates and signs a JSON Web Token (JWT)
		//and send the token back to the client. From then on, the client has to send the
		//signed token with every request. This is an inversion from the SESSIONID model 
		//which has the database store and keep track of all the SESSIONIDs for each user. 
		//In this case, the user provides the signed token for the server to verify. 
		//This is advantageous for scalability as there is no space required for storing 
		//SESSIONIDs and it is the standard in mnay applications. 
		
		//Pros: Fast, Stateless, Can be used across many services 
		//Cons: Tokens can be stolen, it is a serious problem if the secret key 
		//is comprimised, no bookeeping of user history as you can with SESSIONIDs, 
		//since this is a stateless approach
		
		//More info on JWT: https://jwt.io/
		
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
