package com.serverside.SpringSecurity.security;

import java.util.Set;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.google.common.collect.Sets;

import static com.serverside.SpringSecurity.security.ApplicationUserPermission.*;

public enum ApplicationUserRole {
	//define permissions for each role, of type ApplicationUserPermission
	STUDENT(Sets.newHashSet()), 
	ADMIN(Sets.newHashSet(COURSE_READ, COURSE_WRITE, STUDENT_READ, STUDENT_WRITE)),
	ADMINTRAINEE(Sets.newHashSet(COURSE_READ, STUDENT_READ)); 
	
	
	private final Set<ApplicationUserPermission> permissions;
	
	//the permission enums given as hardcoded parameters above 
	//are assigned to the role in the constructor 
	ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
		this.permissions = permissions;
	}
	
	//this getter is used for creating the SimpleGrantedAuthority getter below
	public Set<ApplicationUserPermission> getPermissions() { 
		return this.permissions; 
	}
	
	//look for exaplanation of this getter method in ApplicationSecurityConfig
	//SimpleGrantedAuthority is a basic implementation of the GrantedAuthority interface
	public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
		
		Set<SimpleGrantedAuthority> grantedAuthorities = Sets.newHashSet(); 
		
		Set<ApplicationUserPermission> permissions = this.getPermissions(); 
		for(ApplicationUserPermission p : permissions) { 
			//SimpleGrantedAuthority takes a string 
			grantedAuthorities.add(new SimpleGrantedAuthority(p.getPermission()));	//add fine-grained authorities
		}
		
		//added ROLE_ prefix for spring boot to understand that it is a role, look at 
		//description in ApplicationSecurityConfig
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + this.name())); //add coarse-grained role 
		
		return grantedAuthorities; 
		
		//alternative way to do this 
//		Set<SimpleGrantedAuthority> grantedAuthorities = this.getPermissions().stream()
//				.map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
//				.collect(Collectors.toSet()); 
//		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
//		return grantedAuthorities;
	}
}
