package com.serverside.AngularSpringAuth.security.jwt;

public class UsernameAndPassword {
	
	private String username; 
	private String password; 
	
	public UsernameAndPassword() { 
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
