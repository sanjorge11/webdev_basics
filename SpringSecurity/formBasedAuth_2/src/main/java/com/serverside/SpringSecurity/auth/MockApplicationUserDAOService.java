package com.serverside.SpringSecurity.auth;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;
import com.serverside.SpringSecurity.security.ApplicationUserRole;

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
		List<ApplicationUser> applicationUsers = Lists.newArrayList(
					new ApplicationUser(
						"John", 
						passwordEncoder.encode("password"), 
						ApplicationUserRole.STUDENT.getGrantedAuthorities(),
						true,
						true,
						true,
						true
					), 
					
					new ApplicationUser(
							"Jane", 
							passwordEncoder.encode("password2"), 
							ApplicationUserRole.ADMIN.getGrantedAuthorities(),
							true,
							true,
							true,
							true
						),
					
					new ApplicationUser(
							"Jordan", 
							passwordEncoder.encode("password2"), 
							ApplicationUserRole.ADMINTRAINEE.getGrantedAuthorities(),
							true,
							true,
							true,
							true
						)
				);	
		return applicationUsers; 
	}

}
