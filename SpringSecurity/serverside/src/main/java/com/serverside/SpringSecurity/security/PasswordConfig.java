package com.serverside.SpringSecurity.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordConfig {

	//instantiate an encoder
	@Bean
	public PasswordEncoder passwordEncoder() {
		//chose BCrypt as our encoder, with strength of 10
		return new BCryptPasswordEncoder(10);
	}
	
}
