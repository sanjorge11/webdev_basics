package com.serverside.AngularSpringAuth.security.jwt;

import javax.crypto.SecretKey;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.security.Keys;


@Component
@ConfigurationProperties(prefix = "application.jwt")
public class JwtConfig {

	private String secretKey; 
	private String tokenPrefix; 
	private Integer tokenExpirationAfterDays;
	
	public JwtConfig() { 
		
	}
	
	//getters/setters names must match with items in properties file
	public String getSecretKey() {		
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	
	public SecretKey secretKey() { 
		return Keys.hmacShaKeyFor(this.secretKey.getBytes()); 
	}

	public String getTokenPrefix() {
		return tokenPrefix;
	}

	public void setTokenPrefix(String tokenPrefix) {
		this.tokenPrefix = tokenPrefix;
	}

	public Integer getTokenExpirationAfterDays() {
		return tokenExpirationAfterDays;
	}

	public void setTokenExpirationAfterDays(Integer tokenExpirationAfterDays) {
		this.tokenExpirationAfterDays = tokenExpirationAfterDays;
	}
	
	public String getAuthorizationHeader() { 
		return HttpHeaders.AUTHORIZATION;
	}
	
}
