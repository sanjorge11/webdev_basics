package com.serverside.SpringSecurity.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller		//note that this is not @RestController, which is used to return JSON responses 
//https://stackoverflow.com/questions/25242321/difference-between-spring-controller-and-restcontroller-annotation
@RequestMapping(value = "/")
public class TemplateController {	//this controller is for Thymeleaf server-side templating
	
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String getLoginPage() { 
		return "login";		//return login template, login.html
	}
	
	@RequestMapping(value = "courses", method = RequestMethod.GET)
	public String getCoursesage() { 
		return "courses";
	}

}
