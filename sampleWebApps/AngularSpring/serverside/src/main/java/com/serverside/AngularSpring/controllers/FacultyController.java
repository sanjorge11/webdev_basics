package com.serverside.AngularSpring.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FacultyController {
	
	// Request URL: / is reserved for index.html
//	@RequestMapping(value="/", method=RequestMethod.GET)
//	public String getTest() { 
//		return "GET request for test successful"; 
//	}
	
	@RequestMapping(value="/faculty", method=RequestMethod.GET)
	public String getFaculty_all() { 
		return "GET request for Faculty successful"; 
	}
}
