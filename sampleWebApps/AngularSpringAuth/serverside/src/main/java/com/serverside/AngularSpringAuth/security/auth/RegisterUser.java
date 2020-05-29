package com.serverside.AngularSpringAuth.security.auth;

public class RegisterUser {

	String username;
	String password;
	String role;

	public RegisterUser(String username, String password, String role) {
		this.username = username;
		this.password = password;
		this.role = role;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
