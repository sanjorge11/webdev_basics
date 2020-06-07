package com.serverside.AngularSpringAuth.security.auth;

import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.serverside.AngularSpringAuth.security.jwt.JwtConfig;
import com.serverside.AngularSpringAuth.security.securityConfig.UserRole;

import io.jsonwebtoken.Jwts;

@RestController
public class ApplicationUserController {
	
	@Autowired
	private ApplicationUserService applicationUserService; 
	
	@Autowired
	private ApplicationUserDAO applicationUserDAO;
	
	private final JwtConfig jwtConfig; 
	
	public ApplicationUserController(JwtConfig jwtConfig) { 
		this.jwtConfig = jwtConfig;
	}

	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<String> registerUser(@RequestBody RegisterUser registerUser, 
			HttpServletRequest request, HttpServletResponse response) throws Exception { 
		if(registerUser.getUsername() == null || registerUser.getUsername().length() == 0 ||
		   registerUser.getPassword() == null || registerUser.getPassword().length() == 0 || 
		   registerUser.getRole() == null || registerUser.getRole().length() == 0) { 
			return new ResponseEntity<String>(("Incomplete form"), HttpStatus.BAD_REQUEST);
		}
		
		List<String> applicationRoles = applicationUserDAO.getApplicationResources().getRoles();
		
		if(!applicationRoles.contains(registerUser.getRole())) { 
			return new ResponseEntity<String>(("Error registering user under Role: " + registerUser.getRole()), HttpStatus.BAD_REQUEST);
		}
		
		
		boolean wasSuccess = applicationUserService.registerUser(registerUser.getUsername(), registerUser.getPassword(), registerUser.getRole());
	
		if(wasSuccess) {
			String curRole = null; 
			for(String role : applicationRoles) { 
				if(role.equals(registerUser.getRole())) {
					curRole = role; 
				}
			}

			String jwtToken = 
					
					Jwts.builder()	
						.setSubject(registerUser.getUsername())	//set the username 
						.claim("authorities", UserRole.valueOf(curRole).getGrantedAuthorities())	
						.setIssuedAt(new Date()) 	
						.setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfig.getTokenExpirationAfterDays())))
						.signWith(jwtConfig.secretKey())
						.compact();
					
				    Cookie cookie = new Cookie("Authorization", URLEncoder.encode(jwtConfig.getTokenPrefix() + jwtToken, "UTF-8" ));
				    cookie.setMaxAge((int) TimeUnit.DAYS.toSeconds(jwtConfig.getTokenExpirationAfterDays()));
				    cookie.setHttpOnly(true);
				    cookie.setPath("/");
				    
				    response.addCookie(cookie);
				    
			return new ResponseEntity<String>((registerUser.getUsername() + " was registered successfully"), HttpStatus.OK);
		}
		else return new ResponseEntity<String>((registerUser.getUsername() + " is already taken"), HttpStatus.CONFLICT);
	}

}
