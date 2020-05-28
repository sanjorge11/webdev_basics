package com.serverside.AngularSpringAuth.auth;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

// Documentation explains that creating a custom class that implements UserDetails is an option for creating user classes
// https://docs.spring.io/spring-security/site/docs/3.0.x/apidocs/org/springframework/security/core/userdetails/User.html
public class ApplicationUser implements UserDetails {
	
	//this will be used for creating Users,rather than using the Builder


	/**
	 * 	Serial Version generated for this class which implemented UserDetails, which extends Serializable
	 */
	private static final long serialVersionUID = 5264877739552678893L;
				
	//all of these properties should be stored in database, but the grantedAuthorities should instead be stored as role
	//and we obtain the list of authorities for that given role in Java code
	private final String username; 
	private final String password; 
	private final Set<? extends GrantedAuthority> grantedAuthorities; 
	private final boolean isAccountNonExpired; 	//if any of these booleans are false, then user has no access 
	private final boolean isAccountNonLocked; 
	private final boolean isCredentialsNonExpired; 
	private final boolean isEnabled; 
	
	
	public ApplicationUser(String username, String password, Set<? extends GrantedAuthority> grantedAuthorities,
			boolean isAccountNonExpired, boolean isAccountNonLocked, boolean isCredentialsNonExpired,
			boolean isEnabled) {
		this.username = username;
		this.password = password;
		this.grantedAuthorities = grantedAuthorities;
		this.isAccountNonExpired = isAccountNonExpired;
		this.isAccountNonLocked = isAccountNonLocked;
		this.isCredentialsNonExpired = isCredentialsNonExpired;
		this.isEnabled = isEnabled;
	}	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return grantedAuthorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return isAccountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return isCredentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}

}
