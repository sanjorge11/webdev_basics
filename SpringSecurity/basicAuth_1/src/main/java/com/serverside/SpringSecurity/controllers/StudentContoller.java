package com.serverside.SpringSecurity.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.serverside.SpringSecurity.domain.Student;
import com.serverside.SpringSecurity.services.StudentService;

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
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Student createStudent(@RequestBody Student student) {
		return studentService.createStudent(student);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public void updateStudent(@PathVariable int id, @RequestBody Student student) throws Exception {
		studentService.updateStudent(id, student);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void deleteStudent(@PathVariable int id) {
		studentService.deleteStudent(id);
	}
}
