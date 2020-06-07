package com.serverside.AngularSpringAuth.security.securityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
		
		//Note: My application will re-direct the url to /login if there was an unauthorized request,
		//but technically all of the static files are served at once. It is still relatively safe since 
		//no data request will pass without a valid JWT. It may be a better solution to only serve 
		//the /login and /register files at first, and if authenticated then, serve the rest of the files.
		
		http 
		.cors()		//enable cross-origin requests, the overriden method I implemented will be used
		.and()
		.csrf().disable()
		
		//stateless sessions, no SESSIONIDs created or managed by spring boot
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
		.antMatchers("/", "index", "/css/*", "/js/*", "/login", "/register", "/logout",
				"/runtime-es2015.js", "/polyfills-es2015.js", "/styles-es2015.js", "/vendor-es2015.js", "/main-es2015.js", "/favicon.ico").permitAll()  
		.antMatchers("/students/**").hasRole(UserRole.STUDENT.name()) 
		.antMatchers(HttpMethod.GET, "/admin/**").hasAnyRole(UserRole.ADMIN.name(), UserRole.ADMINTRAINEE.name())	
		
		.anyRequest()
		.authenticated()
		
		.and()
		.logout()
		.clearAuthentication(true)
		.invalidateHttpSession(true)
		.deleteCookies("Authorization")
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))		//set /logout as logout URL
		.logoutSuccessUrl("/");		//re-direct to base URL, Angular app will re-direct it in the front-end to /login
	
	}
	
	
	@Override  //dont let these urls go through filters, probably redundant to permit them in configuration above
    public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/", "index", "/css/*", "/js/*", "/register", 			
		"/runtime-es2015.js*", "/polyfills-es2015.js*", "/styles-es2015.js*", "/vendor-es2015.js*", "/main-es2015.js*", "/favicon.ico");
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
	
	//IMPORTANT: the JWT will be stored in the frontend inside an http-only cookie
	//which means that it will not be able to be retrieved via JavaScript and is 
	//therefore not vulnerable to XSS. An http-only cookie is, however, NOT 
	//safe from CSRF attacks, because the browser will be trusted by website 
	//becuase the cookie sits in the browser and is sent with a request automatically. 
	
	//A way to solve this issue is by storing the Bearer Token (JWT) in-memory. 
	//In other words, stored inside a variable in the client-side javascript 
	//that is running, supplied by the website. The token is persisted, not lost 
	//when refreshing the page, by having a refresh token sent along with bearer 
	//token. It will have server generate new token if lost or expired. The refresh 
	//token is stored in frontend as a cookie. 
	
	//This approach is not vulnerable to XSS because not accessible by JavaScript. 
	//The token is stored in a variable inside the JavaScript that the browser 
	//is running, you cannot access the variables and their values unless ab object 
	//was console.logged. Note that it could be retrieved by setting a breakpoint in
	//the Chrome Dev tools, but the production code should have the files minified 
	//so the attacker will not know where to place the breakpoint or which file 
	//to look at. It is also not vulnerable to CSRF because the website will 
	//not only rely on the cookies stored and the trust with the user. The token 
	//is stored in-memory and sent as a header. 

	//See this video fr details: https://www.youtube.com/watch?v=iD49_NIQ-R4
	
}
