package com.serverside.AngularSpringAuth.auth;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.serverside.AngularSpringAuth.security.UserRole;

//@Repository’s job is to catch persistence specific exceptions 
//and rethrow them as one of Spring’s unified unchecked exception.

@Repository("mock")	//alias given to this implementation is "mock"
public class MockApplicationUserDAOService implements ApplicationUserDAO {

	private final PasswordEncoder passwordEncoder; 
	
	@Autowired
	public MockApplicationUserDAOService(PasswordEncoder passwordEncoder) { 
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
		return getApplicationUsers()
				.stream()
				.filter(applicationUser -> username.equals(applicationUser.getUsername()))
				.findFirst();
	}
	
	private List<ApplicationUser> getApplicationUsers() { 
		List<ApplicationUser> applicationUsers = new ArrayList<>();
		
		applicationUsers.add(
					new ApplicationUser(
						"John", 
						passwordEncoder.encode("password"), 
						UserRole.STUDENT.getGrantedAuthorities(),
						true,
						true,
						true,
						true
					)
		);
				
		return applicationUsers; 
	}

}
