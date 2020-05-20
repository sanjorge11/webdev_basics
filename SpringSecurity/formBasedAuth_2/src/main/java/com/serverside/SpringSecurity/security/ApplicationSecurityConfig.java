package com.serverside.SpringSecurity.security;

import java.util.concurrent.TimeUnit;

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
//2.58.04	
	private final PasswordEncoder passwordEncoder; 
	
	@Autowired	
	public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) { 
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		//form-based auth has been the standard for web apps, it works by having the user login with a form,
				//the server validates the credentials and sends an OK status along with a cookie that contains 
				//the SESSIONID, any subsequent request will have the SESSIONID attached for the server to validate
				//and so then user will not need to enter username and password for every request, each subsequent 
				//request validates the SESSIONID, so each request will have a response 200 along with whatever user asked for 
				//the default lifespan for a SESSIONID in 30 min
				
				//by default, the SESSIONID is stored in an in-memory database in Spring-Boot, so a new SESSIONID will be created 
				//whenever the server restarts -- it is possible, however, to use external databases to store sessions, which is best practice
		
		http
		.csrf().disable()		
		.authorizeRequests()
		.antMatchers("/", "index", "/css/*", "/js/*").permitAll()  
		.antMatchers("/students/**").hasRole(ApplicationUserRole.STUDENT.name()) 
		.antMatchers(HttpMethod.GET, "/management/students/**").hasAnyRole(ApplicationUserRole.ADMIN.name(), ApplicationUserRole.ADMINTRAINEE.name())	
		.anyRequest()	
		.authenticated()	
		.and()			
		.formLogin() 	//specified as form-based authentication		
		.loginPage("/login").permitAll()	//permit access to the login page 
		.defaultSuccessUrl("/courses", true) 	//force re-direct to /courses on login success
		.and()
		.rememberMe() 	//this will override the 30 min SESSIONID expiration to 2 weeks (if no parameter given)
		//According to, https://www.baeldung.com/spring-security-remember-me 
		//Spring-Boot will expect an input with a name of 'remember-me', it can be used to toggle the 
		//remember me option, also note that you can configure it to expect a different name if needed, 
		//check the link to see details regarding the cookie's contents
		
			.tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21)) //you can configure it to have token last 21 days
			.key("specialKey")		//you can set the key used for the md5 hash generated for Remember Me cookie
		
		//you will see a cookie with the name, remember-me in Application tab of Chrome tools if the checkbox 
		//with name remember-me is toggled to true, Spring will enable the Remember Me option
		
		//we can customize the logout to force the deletion of cookies stored in Chrome, and also clear authentication
		//and invalidate HTTP session
		.and()
		.logout()
			.logoutUrl("/logout") 	//specify logout url, leave it as /logout 
			.clearAuthentication(true)
			.invalidateHttpSession(true)
			.deleteCookies("JSESSIONID", "remember-me") //list he cookie names you wish to delete 
			.logoutSuccessUrl("/login"); 	//redirect to login page after logout
		
		//note about logout request 
		//From documentation: 
		//	The URL that triggers log out to occur (default is "/logout"). If CSRF protection is enabled (default), 
		//	then the request must also be a POST. This means that by default POST "/logout" is required to trigger a 
		//	log out. If CSRF protection is disabled, then any HTTP method is allowed. It is considered best practice 
		//	to use an HTTP POST on any action that changes state (i.e. log out) to protect against CSRF attacks. If 
		//	you really want to use an HTTP GET, you can use 
		//	logoutRequestMatcher(new AntPathRequestMatcher(logoutUrl, "GET"));
		//So essentially, this line is added under the hood when csrf is disabled (using GET as /logout method)
		// 		.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
		//for more info, see: https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/web/configurers/LogoutConfigurer.html#logoutUrl-java.lang.String-
		//					  https://youtu.be/her_7pa0vrg?t=10217
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
