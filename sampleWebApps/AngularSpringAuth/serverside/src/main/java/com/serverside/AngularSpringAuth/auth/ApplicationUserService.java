package com.serverside.AngularSpringAuth.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserService implements UserDetailsService {
	
	//this service will be used to fetch users from database
	
	//Note that for this demo, we store users in our application database. It is best practice 
	//to store users in a separate external database. OAuth is, for example, a third-party service 
	//that authenticates uers and stores them in their own database. 
	//https://auth0.com/docs/best-practices/user-data-storage-best-practices
	
	private final ApplicationUserDAO applicationUserDao;
	
	@Autowired	
	public ApplicationUserService(ApplicationUserDAO applicationUserDao) { 
		this.applicationUserDao = applicationUserDao;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails user = applicationUserDao.selectApplicationUserByUsername(username); 
		
		if(user == null) { 
			throw new UsernameNotFoundException(String.format("Username %s not found", username)); 
		}
				
		return user;
	}
	
	public boolean registerUser(String username, String password, String role) throws Exception {
		
		UserDetails user = applicationUserDao.selectApplicationUserByUsername(username); 
		
		if(user == null) { 
			applicationUserDao.registerUser(username, password, role); 
			return true; 
		} else { 
			return false; 
		}
	}
	
}
