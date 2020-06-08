package com.serverside.AngularSpringAuth.security.jwt;

import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;

//Filter #1	-- we extend UsernamePasswordAuthenticationFilter, not done once per request, only when authenticating user in /login
public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager; 
	
	private final JwtConfig jwtConfig; 

	public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager,
													  JwtConfig jwtConfig) {
		this.authenticationManager = authenticationManager;
		this.jwtConfig = jwtConfig; 
	}


	@Override	//authenticate username/password
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		try {
			
			UsernameAndPassword usernameAndPassword = new ObjectMapper()
					.readValue(request.getInputStream(), UsernameAndPassword.class);
		
			Authentication authentication = new UsernamePasswordAuthenticationToken(
					usernameAndPassword.getUsername(), 
					usernameAndPassword.getPassword()
			);
			
			return authenticationManager.authenticate(authentication);
			
		} catch (IOException e) {
			throw new RuntimeException(e); 
		}
	}


	@Override		//return a generate JWT if authenticated
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
				
		String jwtToken = 
				
		Jwts.builder()	
			.setSubject(authResult.getName())	//set the username 
			.claim("authorities", authResult.getAuthorities())	
			.setIssuedAt(new Date()) 	
			.setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfig.getTokenExpirationAfterDays())))
			.signWith(jwtConfig.secretKey())
			.compact();
		
		//Replaced logic that sends JWT inside header, to inside an HTTP-Only Cookie
		//response.addHeader("Access-Control-Expose-Headers", "Authorization");
		//response.addHeader(jwtConfig.getAuthorizationHeader(), jwtConfig.getTokenPrefix() + jwtToken);
		
		//the cookie is sent to frontend, browser automatically stores it and sends it with each subsequent request
	    Cookie cookie = new Cookie("Authorization", URLEncoder.encode(jwtConfig.getTokenPrefix() + jwtToken, "UTF-8" ));
	    cookie.setMaxAge((int) TimeUnit.DAYS.toSeconds(jwtConfig.getTokenExpirationAfterDays()));
	    cookie.setHttpOnly(true);
	    cookie.setPath("/");
	    
	    response.addCookie(cookie);

		//chain.doFilter(request, response); 	//creates error
	}
	
}
