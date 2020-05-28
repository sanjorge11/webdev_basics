package com.serverside.AngularSpringAuth.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.serverside.AngularSpringAuth.domain.Student;
import com.serverside.AngularSpringAuth.services.StudentService;

@RestController
@RequestMapping(value = "/management/students")
public class StudentManagementController {
	
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
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('student:write')") 
	public Student createStudent(@RequestBody Student student) {
		return studentService.createStudent(student);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@PreAuthorize("hasAuthority('student:write')") 
	public void updateStudent(@PathVariable int id, @RequestBody Student student) throws Exception {
		studentService.updateStudent(id, student);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasAuthority('student:write')") 
	public void deleteStudent(@PathVariable int id) {
		studentService.deleteStudent(id);
	}
}
