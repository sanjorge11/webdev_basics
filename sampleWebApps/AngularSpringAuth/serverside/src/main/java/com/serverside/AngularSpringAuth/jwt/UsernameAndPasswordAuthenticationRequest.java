package com.serverside.AngularSpringAuth.jwt;

public class UsernameAndPasswordAuthenticationRequest {
	//class to get username and password from client 
	
	private String username; 
	private String password; 
	
	public UsernameAndPasswordAuthenticationRequest() { 
		
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
