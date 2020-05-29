package com.serverside.AngularSpringAuth.security.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationUserController {
	
	@Autowired
	private ApplicationUserService applicationUserService; 
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<String> registerUser(@RequestBody RegisterUser registerUser) throws Exception { 
		boolean wasSuccess = applicationUserService.registerUser(registerUser.getUsername(), registerUser.getPassword(), registerUser.getRole());
	
		if(wasSuccess) return new ResponseEntity<String>((registerUser.getUsername() + " was registered successfully"), HttpStatus.OK);
		else return new ResponseEntity<String>((registerUser.getUsername() + " is already taken"), HttpStatus.CONFLICT);
	}

}
