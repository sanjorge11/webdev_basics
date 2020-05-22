package com.serverside.SpringSecurity.auth;

import java.util.Optional;

public interface ApplicationUserDAO {

	//https://www.tutorialspoint.com/java8/java8_optional_class.htm
//	Optional is a container object used to contain not-null objects. 
//	Optional object is used to represent null with absent value. This class 
//	has various utility methods to facilitate code to handle values 
//	as ‘available’ or ‘not available’ instead of checking null values.
	public Optional<ApplicationUser> selectApplicationUserByUsername(String username); 

}
