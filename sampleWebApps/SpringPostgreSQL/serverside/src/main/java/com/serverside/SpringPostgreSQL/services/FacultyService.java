package com.serverside.SpringPostgreSQL.services;

import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.serverside.SpringPostgreSQL.dao.FacultyDAO;
import com.serverside.SpringPostgreSQL.domain.Faculty;

@Service
public class FacultyService {
	
	private final Logger _log = Logger.getLogger(FacultyService.class);
	
	@Autowired
	FacultyDAO facultyDAO;
	
	public List<Faculty> getAllFaculty() { 
		
		List<Faculty> res = facultyDAO.getAllFaculty();
		
		return res;
	}
	
	public Faculty getFacultyMember(int id) { 
		
		Faculty res = facultyDAO.getFacultyMember(id);
		
		return res;
	}
	
	public void createFacultyMember(String firstName, String lastName) { 
		facultyDAO.createFacultyMember(firstName, lastName);
	}

	public Faculty updateFacultyMember(int id, Faculty faculty) throws Exception { 
		
		int res = facultyDAO.updateFacultyMember(id, faculty);
		
		//Exception handling example
		if(res != 1) { 	
			_log.error("Error updating Faculty with ID: " + id);
			throw new Exception("Error updating Faculty with ID: " + id);
		}
		
		faculty.setId(id);
		
		return faculty; 
	}
	
	public String deleteFacultyMember(int id) { 
		
		int res = facultyDAO.deleteFacultyMember(id);
		
		if(res != 1) {
			return "Error deleting Faculty with ID: " + id;
		}
		
		return "Successfully deleted Faculty with ID: " + id;
	}
}
