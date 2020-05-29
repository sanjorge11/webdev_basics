package com.serverside.AngularSpringAuth.security.securityConfig;

public enum UserPermission {
	STUDENT_READ("student:read"),
	STUDENT_WRITE("student:write");
									
	private final String permission; 
	
	UserPermission(String permission) { 
		this.permission = permission;
	}
	
	public String getPermission() {
		return permission;
	}
}
