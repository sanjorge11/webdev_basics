package com.serverside.AngularSpringAuth.services;

import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.serverside.AngularSpringAuth.dao.StudentDAO;
import com.serverside.AngularSpringAuth.domain.Student;

@Service
public class StudentService {
	
	private final Logger _log = Logger.getLogger(StudentService.class);
	
	@Autowired
	StudentDAO studentDAO;
	
	public List<Student> getAllStudents() { 
		
		List<Student> res = studentDAO.getAllStudents();
		
		return res;
	}
	
	public Student getStudent(int id) { 
		
		Student res = studentDAO.getStudent(id);
		
		return res;
	}
	
	public Student createStudent(Student student) { 
		int uniqueId = studentDAO.getNextVal(); 
		
		studentDAO.createStudent(uniqueId, student);
		
		Student newStudent = new Student(uniqueId, student.getGpa(), 
				student.getFirstName(), student.getLastName()); 
		return newStudent;
	}

	public void updateStudent(int id, Student student) throws Exception { 
		
		int res = studentDAO.updateStudent(id, student);
		
		//Exception handling example
		if(res != 1) { 	
			_log.error("Error updating Student with ID: " + id);
			throw new Exception("Error updating Student with ID: " + id);
		}
		
	}
	
	public void deleteStudent(int id) { 
		studentDAO.deleteStudent(id);
	}
}
