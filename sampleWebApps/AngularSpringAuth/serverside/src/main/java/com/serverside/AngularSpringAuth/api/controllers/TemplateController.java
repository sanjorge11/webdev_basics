package com.serverside.AngularSpringAuth.api.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller		
@RequestMapping(value = "/")
public class TemplateController {	
	
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String getLoginPage() { 
		return "login";	
	}
	
	@RequestMapping(value = "courses", method = RequestMethod.GET)
	public String getCoursesage() { 
		return "courses";
	}

}
