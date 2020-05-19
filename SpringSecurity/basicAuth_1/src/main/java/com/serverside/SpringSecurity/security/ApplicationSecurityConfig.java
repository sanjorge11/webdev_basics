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
@EnableWebSecurity  //this lets Spring know the class will configure everything about security for our web application
@EnableGlobalMethodSecurity(prePostEnabled = true)			//this enables pre/post annotations for method-level security, https://www.baeldung.com/spring-security-method-security
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

	private final PasswordEncoder passwordEncoder; 
	
	@Autowired	//instantiate current object by @Autowired with an encoder
	public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) { 
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		////disabled cross-site request forgery, this is disabled if API will be used for non-browser usage, should be enabled for browser usage --
		.csrf().disable()		 //server sends client a csrf token and client must send a token that server validates in post/put/delete requests
		//if we disable csrf, we need to send the csrf token for each post/put/delete request done -- sending a get request or logging in will let Spring send a generated 
		//csrf token in a cookie to the browser, the name of the cookie is called XSRF -- we can write logic in the frontend to get that token and send it back to the server
		//in a header called X-XSRF-TOKEN
		//for info on how csrf token is generated and how to add .withHttpOnlyFalse() config, see: https://youtu.be/her_7pa0vrg?t=7694
		
		.authorizeRequests()
		.antMatchers("/", "index", "/css/*", "/js/*").permitAll()  //white-listed patterns, permit all that match
		.antMatchers("/students/**").hasRole(ApplicationUserRole.STUDENT.name()) //this endpoint can only be accessed by users with STUDENT role
		
		//these antMatchers were replaced with method-level authorization with @PreAuthorize
//		.antMatchers(HttpMethod.DELETE, "/management/students/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission()) //fine-grained security with authorities
//		.antMatchers(HttpMethod.POST, "/management/students/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
//		.antMatchers(HttpMethod.PUT, "/management/students/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())

		//note that the order of these antMatchers DO MATTER, if you were to place the matcher below at the top
		//.antMatchers("/management/students/**").hasAnyRole(ApplicationUserRole.ADMIN.name(), ApplicationUserRole.ADMINTRAINEE.name())
		//then the ADMINTRAINEE would match this antMatcher and get all actions granted
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
				//.roles(ApplicationUserRole.STUDENT.name()) //will be resolved as ROLE_STUDENT in spring boot "STUDENT" is the name of the enum 
				.authorities(ApplicationUserRole.STUDENT.getGrantedAuthorities()) //alternative to above, more custom 
				.build();
		
		
		//user can read and write
		UserDetails sampleAdmin = User.builder()
				.username("Jane")
				.password(passwordEncoder.encode("password2")) 
				//.roles(ApplicationUserRole.ADMIN.name()) 
				.authorities(ApplicationUserRole.ADMIN.getGrantedAuthorities())
				.build();
		
		//if you look inside .roles(), you see that it takes the string given, 
		//appends "ROLE_" prefix and adds it to an arraylist of type 
		//object that is an implementation of GrantedAuthority interface 
		//we can use this idea to create a getter method in ApplicationUserRoles
		//to get a list of GrantedAuthorities include both Role and Permissions 
		//to have the fine-grained authorities attached to the user
		
//		public UserBuilder roles(String... roles) {
//			List<GrantedAuthority> authorities = new ArrayList<>(
//					roles.length);
//			for (String role : roles) {
//				Assert.isTrue(!role.startsWith("ROLE_"), () -> role
//						+ " cannot start with ROLE_ (it is automatically added)");
//				authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
//			}
//			return authorities(authorities);
//		}
		
		//user can only read
		UserDetails sampleAdminTrainee = User.builder()
				.username("Jordan")
				.password(passwordEncoder.encode("password2")) 
				//.roles(ApplicationUserRole.ADMINTRAINEE.name()) 
				.authorities(ApplicationUserRole.ADMINTRAINEE.getGrantedAuthorities())
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
