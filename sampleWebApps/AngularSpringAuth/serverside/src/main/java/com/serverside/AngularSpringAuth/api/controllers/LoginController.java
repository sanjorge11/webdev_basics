package com.serverside.AngularSpringAuth.api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.serverside.AngularSpringAuth.api.domain.Student;

@RestController
@RequestMapping(value = "/login")
public class LoginController {
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String hello() { 
		return "hello";
	}

}
