package com.serverside.AngularSpringAuth.security.appConfig;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static com.serverside.AngularSpringAuth.security.appConfig.UserPermission.*;

public enum UserRole {
	STUDENT(new HashSet<UserPermission>()), 
	ADMIN(new HashSet<UserPermission>(Arrays.asList(COURSE_READ, COURSE_WRITE, STUDENT_READ, STUDENT_WRITE))),
	ADMINTRAINEE(new HashSet<UserPermission>(Arrays.asList(COURSE_READ, STUDENT_READ)));
	
	
	private final Set<UserPermission> permissions;
	
	UserRole(Set<UserPermission> permissions) {
		this.permissions = permissions;
	}
	
	public Set<UserPermission> getPermissions() { 
		return this.permissions; 
	}
	
	public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
		
		Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>(); 
		
		Set<UserPermission> permissions = this.getPermissions(); 
		for(UserPermission p : permissions) { 
			grantedAuthorities.add(new SimpleGrantedAuthority(p.getPermission()));	
		}
		

		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + this.name())); 
		
		return grantedAuthorities; 
	}
}
