package com.serverside.SpringSecurity.jwt;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.common.base.Strings;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

//this class will be a filter to be invoked once for every single request 
//coming from the client, here we will verify the JWT provided by client before allowing them access
public class JwtTokenVerifier extends OncePerRequestFilter {
	
	private final SecretKey secretKey;
	private final JwtConfig jwtConfig;
	

	public JwtTokenVerifier(SecretKey secretKey, JwtConfig jwtConfig) {
		this.secretKey = secretKey;
		this.jwtConfig = jwtConfig;
	}



	@Override
	protected void doFilterInternal(HttpServletRequest request, 
									HttpServletResponse response, 
									FilterChain filterChain)
													throws ServletException, IOException {		
		//String authorizationHeader = request.getHeader("Authorization"); 
		String authorizationHeader = request.getHeader(jwtConfig.getAuthorizationHeader());
		
		if(Strings.isNullOrEmpty(authorizationHeader) || authorizationHeader.startsWith(jwtConfig.getAuthorizationHeader())) { 
			filterChain.doFilter(request, response);		//request will be rejected
			return; 
		}
		
		//token from client
		String token = authorizationHeader.replace(jwtConfig.getTokenPrefix(), ""); 
		
		try { 
			
			//String key = "securesecuresecuresecuresecuresecure";

			//parse the token 
			//Note that .parseClaimsJws will validate the signature
			//From Documentation: 
			//There are two things going on here. The key from before is being used to validate the signature of the JWT. 
			//If it fails to verify the JWT, a SignatureException (which extends from JwtException) is thrown.
			Jws<Claims> claimsJws = 
					
			Jwts.parser()
				//.setSigningKey(Keys.hmacShaKeyFor(key.getBytes()))
				.setSigningKey(secretKey)	
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
			
			
			//here we set the authentication to be true, we confirm that client that sent token is authenticated

			// After setting the Authentication in the context, we specify
			// that the current user is authenticated. So it passes the
			// Spring Security Configurations successfully.
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
		} catch(JwtException e) { 
			throw new IllegalStateException(String.format("Token %s cannot be trusted", token)); 
		}
		
		filterChain.doFilter(request, response);	//pass request/response to next filter in chain
	}

}
