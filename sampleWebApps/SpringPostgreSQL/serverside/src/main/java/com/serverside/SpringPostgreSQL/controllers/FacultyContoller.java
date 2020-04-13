package com.serverside.SpringPostgreSQL.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.serverside.SpringPostgreSQL.domain.Faculty;
import com.serverside.SpringPostgreSQL.services.FacultyService;

@RestController
@RequestMapping(value = "/faculty")
public class FacultyContoller {
	
	@Autowired
	private FacultyService facultyService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<Faculty> getFaculty_all() { 
		
		List<Faculty> facultyList = facultyService.getAllFaculty();
		
		return facultyList; 
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Faculty getFacultyMember(@PathVariable int id) { 
		
		Faculty faculty = facultyService.getFacultyMember(id);
		
		return faculty; 
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public void createFacultyMember(@RequestBody Faculty faculty) {
		facultyService.createFacultyMember(faculty.getFirstName(), faculty.getLastName());
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Faculty updateFacultyMember(@PathVariable int id, @RequestBody Faculty faculty) throws Exception {
		Faculty res = facultyService.updateFacultyMember(id, faculty);
		
		return res;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String deleteFacultyMember(@PathVariable int id) {
		return facultyService.deleteFacultyMember(id);
	}
}
