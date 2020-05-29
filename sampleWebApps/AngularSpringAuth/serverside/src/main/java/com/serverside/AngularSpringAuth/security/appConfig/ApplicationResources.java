package com.serverside.AngularSpringAuth.security.appConfig;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "application.resources")
public class ApplicationResources {
	
	private List<String> roles; 
	
	public ApplicationResources() { 
		
	}
	
	public List<String> getRoles() {		
		return roles;
	}
	
	public void setRoles(String roles) {		
		String[] rolesArr = roles.split(","); 
		this.roles = Arrays.asList(rolesArr); 
	}

}
