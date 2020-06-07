package com.serverside.AngularSpringAuth.security.jwt;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

//Filter #2 -- this is done once per request
public class JwtTokenVerifierFilter extends OncePerRequestFilter {
	
	private final JwtConfig jwtConfig;
	

	public JwtTokenVerifierFilter(JwtConfig jwtConfig) {
		this.jwtConfig = jwtConfig;
	}



	@Override
	protected void doFilterInternal(HttpServletRequest request, 
									HttpServletResponse response, 
									FilterChain filterChain)
													throws ServletException, IOException {		

		//Replaced logic that sends JWT inside header, to inside an HTTP-Only Cookie
		Cookie[] cookies = request.getCookies(); 
		if(cookies == null || cookies.length == 0) { 
			triggerLogout(request, response, filterChain); 
			
			//System.out.println(request.getRequestURI());

			//filterChain.doFilter(request, response);		//request will be rejected
			return; 
		}
		Cookie authCookie = null; 
		for(Cookie c : cookies) { 
			if(c.getName().equals("Authorization")) {
				authCookie = c; 
				break; 
			}
		}
		if(authCookie == null) {
			triggerLogout(request, response, filterChain); 
			
			//filterChain.doFilter(request, response);		//request will be rejected
			return; 
		}
		
		
		String token = URLDecoder.decode(authCookie.getValue(), "UTF-8").replace(jwtConfig.getTokenPrefix(), ""); 
		
		
		/*
		String authorizationHeader = request.getHeader(jwtConfig.getAuthorizationHeader());
		
		if(authorizationHeader == null || authorizationHeader.length() == 0|| authorizationHeader.startsWith(jwtConfig.getAuthorizationHeader())) { 
			filterChain.doFilter(request, response);		//request will be rejected
			return; 
		}
		
		//token from client
		String token = authorizationHeader.replace(jwtConfig.getTokenPrefix(), ""); 
		*/
		
		
		try { 
			
			//Note that .parseClaimsJws will validate the signature
			//From Documentation: 
			//There are two things going on here. The key from before is being used to validate the signature of the JWT. 
			//If it fails to verify the JWT, a SignatureException (which extends from JwtException) is thrown.
			//Will also throw exception if JWT is expired.
			Jws<Claims> claimsJws = 
					
			Jwts.parser()
				.setSigningKey(jwtConfig.secretKey())	
				.parseClaimsJws(token); 
			//documentation mentions that when we created the JWT, calling .compact(), it creates 
			//a signed JWT called a JWS, so we are parsing a JWS
			
			Claims body = claimsJws.getBody();
			
			String username = body.getSubject();
			
			//get the list of maps, authorities
			List<Map<String, String>> authorities = (List<Map<String, String>>) body.get("authorities");
		
			//map the list contents to a set of SimpleGrantedAuthority objects 
			Set<SimpleGrantedAuthority> simpleGrantedAuthorites = 
			authorities.stream()
				.map(m -> new SimpleGrantedAuthority(m.get("authority")))
				.collect(Collectors.toSet());
			
			//create authentication object to authenticate
			Authentication authentication = new UsernamePasswordAuthenticationToken(
					username, 
					null, 
					simpleGrantedAuthorites
			); 
			
			
			//you could implement more verifications here like: check expiration, check username
			
			
			//here we set the authentication to be true, we confirm that client that sent token is authenticated
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
		} catch(JwtException e) { 
			
			triggerLogout(request, response, filterChain); 
			
			//this catch block would catch if JWS signature is invalid, or expired, etc.
			throw new IllegalStateException(String.format("Token %s cannot be trusted", token)); 
		}
		
		filterChain.doFilter(request, response);	//pass request/response to next filter in chain
	}
	
	public void triggerLogout(HttpServletRequest request, 
			HttpServletResponse response, 
			FilterChain filterChain) { 
		
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); 
		
		//response.setStatus(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized Request");
		
	}

}
