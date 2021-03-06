package com.serverside.AngularSpringAuth.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.serverside.AngularSpringAuth.api.domain.Student;
import com.serverside.AngularSpringAuth.api.services.StudentService;

@RestController
@RequestMapping(value = "/students")
public class StudentContoller {
	
	@Autowired
	private StudentService studentService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<Student> getStudents_all() { 
		
		List<Student> studentList = studentService.getAllStudents();
		
		return studentList; 
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Student getStudent(@PathVariable int id) { 
		
		Student student = studentService.getStudent(id);
		
		return student; 
	}
	
}
