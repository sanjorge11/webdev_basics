package com.serverside.SpringSecurity.security;

public enum ApplicationUserPermission {
	STUDENT_READ("student:read"),
	STUDENT_WRITE("student:write"), 
	COURSE_READ("course:read"), 
	COURSE_WRITE("course:write");	//these parameters are assigned in the 
									//constructor as the permission description
	private final String permission; 
	
	ApplicationUserPermission(String permission) { 
		this.permission = permission;
	}
	
	public String getPermission() {
		return permission;
	}
}
