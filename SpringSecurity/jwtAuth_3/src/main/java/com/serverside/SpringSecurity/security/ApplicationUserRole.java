package com.serverside.SpringSecurity.security;

import java.util.Set;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.google.common.collect.Sets;

import static com.serverside.SpringSecurity.security.ApplicationUserPermission.*;

public enum ApplicationUserRole {
	STUDENT(Sets.newHashSet()), 
	ADMIN(Sets.newHashSet(COURSE_READ, COURSE_WRITE, STUDENT_READ, STUDENT_WRITE)),
	ADMINTRAINEE(Sets.newHashSet(COURSE_READ, STUDENT_READ)); 
	
	
	private final Set<ApplicationUserPermission> permissions;
	
	ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
		this.permissions = permissions;
	}
	
	public Set<ApplicationUserPermission> getPermissions() { 
		return this.permissions; 
	}
	
	public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
		
		Set<SimpleGrantedAuthority> grantedAuthorities = Sets.newHashSet(); 
		
		Set<ApplicationUserPermission> permissions = this.getPermissions(); 
		for(ApplicationUserPermission p : permissions) { 
			grantedAuthorities.add(new SimpleGrantedAuthority(p.getPermission()));	
		}
		

		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + this.name())); 
		
		return grantedAuthorities; 
	}
}
