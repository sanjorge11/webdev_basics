package com.serverside.AngularSpringAuth.api.controllers;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.serverside.AngularSpringAuth.security.auth.ApplicationUserService;

@RestController
@RequestMapping(value = "/base")
public class BaseController {
	
	@Autowired
	private ApplicationUserService applicationUserService;

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String greeting(HttpServletRequest request) { 
		
		String username = request.getUserPrincipal().getName(); 
				
		return "Welcome " + username + "!"; 
	}
	
	@RequestMapping(value = "/currentUserRole", method = RequestMethod.GET)
	public String getCurrentUserRole(HttpServletRequest request) { 
		
		String username = request.getUserPrincipal().getName(); 
		
		UserDetails userDetails = applicationUserService.loadUserByUsername(username);
		
		Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
		
		String role = ""; 
		
		for(GrantedAuthority r : authorities) { 
			if(r.getAuthority().contains("ROLE_")) { 
				role = r.getAuthority();
			}
		}
		
				
		return role; 
	}
	
}
