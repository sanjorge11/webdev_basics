package com.serverside.AngularSpringAuth.security.appConfig;

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

import com.serverside.AngularSpringAuth.security.auth.ApplicationUserService;
import com.serverside.AngularSpringAuth.security.jwt.JwtConfig;
import com.serverside.AngularSpringAuth.security.jwt.JwtTokenVerifierFilter;
import com.serverside.AngularSpringAuth.security.jwt.JwtUsernameAndPasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity  
@EnableGlobalMethodSecurity(prePostEnabled = true)			
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

	private final PasswordEncoder passwordEncoder;		//this is from the Bean defined in PasswordConfig class
	private final ApplicationUserService applicationUserService; 	
	private final JwtConfig jwtConfig;
	
//	Autowiring by constructor is similar to byType but it applies to constructor arguments. It will look for 
//	the class type of constructor arguments, and then do an autowire byType on all constructor arguments. If 
//	exactly one bean of the constructor argument type is not present in the container, a fatal error will be raised.
//  The properties for the constructor are injected as an argument to the constructor when ApplicationSecurityConfig
//	is created. 	
	
	@Autowired
	public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, ApplicationUserService applicationUserService, JwtConfig jwtConfig) {
		this.passwordEncoder = passwordEncoder;
		this.applicationUserService = applicationUserService;
		this.jwtConfig = jwtConfig;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
		.csrf().disable()
		
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)	
		.and()	
		
		//Make a filter chain: 
		//Note that the first Filter happens when authenticating user in /login 
		//The second Filter happens when verifying a request, it is NOT done after first filter 
		//because the chain.doFilter() does not pass the request/reponse on to the next Filter in the chain.
		//Therefore, every request that's not authenticating from /login will go to second Filter, once that 
		//is done, it will pass on the request/response to the next Filter (which would be some other 
		//Filter that Spring Security provides and has not been overriden by us)
		.addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig))
		.addFilterAfter(new JwtTokenVerifierFilter(jwtConfig), JwtUsernameAndPasswordAuthenticationFilter.class)	
		
		.authorizeRequests()
		.antMatchers("/", "index", "/css/*", "/js/*").permitAll()  
		.antMatchers("/students/**").hasRole(UserRole.STUDENT.name()) 
		.antMatchers(HttpMethod.GET, "/management/students/**").hasAnyRole(UserRole.ADMIN.name(), UserRole.ADMINTRAINEE.name())	
		
		.anyRequest()
		.authenticated()

		.and()			
		.formLogin() 	
		.loginPage("/login").permitAll()	
		.defaultSuccessUrl("/courses", true) 	

		.and()
		.logout()
			.logoutUrl("/logout") 	 
			.logoutSuccessUrl("/login"); 	
	
	}
	
	
	@Bean 		//set up this provider for AuthenticationManager to use
	public DaoAuthenticationProvider daoAuthenticationProvider() { 
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider(); 
		provider.setPasswordEncoder(passwordEncoder);	
		provider.setUserDetailsService(applicationUserService);
		return provider;
	}

	@Override		//override AuthenticationManager for the app to use Provider defined above
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider());
	}
	
}
