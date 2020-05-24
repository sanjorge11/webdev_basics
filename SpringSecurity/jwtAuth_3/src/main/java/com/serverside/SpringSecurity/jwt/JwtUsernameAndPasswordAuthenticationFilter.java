package com.serverside.SpringSecurity.jwt;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

//this extends a Filter to implement our own overridden methods, 
//Filters are between the request and the API -- they can deny a request or let them through
public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	//this class will verify the credentials, Spring-Boot can verify credentials
	//for us, but we ar going to override it with out own implementation
	
	
	//this is used by Spring-Boot to authenticate
	private final AuthenticationManager authenticationManager; 
	
	private final JwtConfig jwtConfig; 
	private final SecretKey secretKey; 


	public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager,
													  JwtConfig jwtConfig, 
													  SecretKey secretKey) {
		this.authenticationManager = authenticationManager;
		this.jwtConfig = jwtConfig; 
		this.secretKey = secretKey;
	}


	@Override		//this portion obtains user credentials and validates them  
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		try {
			//user ObjectMapper to read contents of request object and create a UsernameAndPasswordAuthenticationRequest
			UsernameAndPasswordAuthenticationRequest authenticationRequest = new ObjectMapper()
					.readValue(request.getInputStream(), UsernameAndPasswordAuthenticationRequest.class);
		
			//Authentication is an interface, we are using the UsernamePasswordAuthenticationToken implementation
			Authentication authentication = new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), 
					authenticationRequest.getPassword()
			);
			
			//authenticationManager will check if username exists, if it exists then it will 
			//check if password is correct -- if it all checks out, then the request will be authenticated
			return authenticationManager.authenticate(authentication);
			
		} catch (IOException e) {
			throw new RuntimeException(e); 
		}
	}

	//this method is invoked after attemptAuthentication() is successful, 
	//if attemptAuthentication() fails, then this will never be executed 
	//here, we create a JWT and send it to the client
	@Override	
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		//String key = "securesecuresecuresecuresecuresecure";  //the key should be long and difficult to crack
		
		String jwtToken = 
				
		Jwts.builder()	
			.setSubject(authResult.getName())	//set the username info
			.claim("authorities", authResult.getAuthorities())	//set JWT body
			.setIssuedAt(new Date()) 	//set Issued At date
			.setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2)))	//expires in 2 weeks
			//.signWith(Keys.hmacShaKeyFor(key.getBytes()))
			.signWith(secretKey)
			.compact();
		
		//you can place a breakpoint under here and copy the JWT to https://jwt.io/ to see the contents 
			
		//response.addHeader("Authorization", ("Bearer " + jwtToken));  //pass the JWT token in response header
		response.addHeader(jwtConfig.getAuthorizationHeader(), jwtConfig.getTokenPrefix() + jwtToken);
	}
	
}
