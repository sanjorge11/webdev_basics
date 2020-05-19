package com.serverside.SpringSecurity.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class HomeController {
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String greet() { 
		return "Hello World";
	}

}
