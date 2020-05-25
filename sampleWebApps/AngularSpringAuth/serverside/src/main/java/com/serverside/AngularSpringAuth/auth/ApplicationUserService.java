package com.serverside.AngularSpringAuth.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserService implements UserDetailsService {
	
	//this service will be used to fetch users from database

	//we are using an interface because we can use dependency injection,
	//to switch implementations if we want to do so (switching between Postgres
	//and MongoDB will require only one line to be changed)
	private final ApplicationUserDAO applicationUserDao;
	
	@Autowired		//to let Spring instatiate this interface as a Singleton
	//specify which implementation to use with @Qualifier
	public ApplicationUserService(@Qualifier("mock") ApplicationUserDAO applicationUserDao) { 
		this.applicationUserDao = applicationUserDao;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return applicationUserDao.selectApplicationUserByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found", username)));
	}

	
}
