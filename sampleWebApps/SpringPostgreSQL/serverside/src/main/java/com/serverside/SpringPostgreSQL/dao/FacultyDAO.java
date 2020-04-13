package com.serverside.SpringPostgreSQL.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.serverside.SpringPostgreSQL.domain.Faculty;

@Repository
public class FacultyDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	//DataSource object contains the database connection information, 
	//such as: jdbcUrl, driverClassName, userName, password
	//we @Autowired this setter so that the DataSource info is 
	//assigned to the jdbcTemplate on application startup bootstrapping
	@Autowired
    public void setDataSource(final DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
	
	public class FacultyRowMapper implements RowMapper<Faculty> {
		@Override
		public Faculty mapRow(ResultSet rs, int rowNum) throws SQLException {
		      Faculty faculty = new Faculty();
		      
		      faculty.setId(rs.getInt("id"));
		      faculty.setFirstName(rs.getString("first_name").trim());
		      faculty.setLastName(rs.getString("last_name").trim());
		      
		      return faculty;
		}
	}
	
	public List<Faculty> getAllFaculty() { 
		
		String sql = "SELECT * FROM public.faculty"; 
		
		List<Faculty> res = jdbcTemplate.query(sql, new FacultyRowMapper()); 
		
		return res;
	}
	
	public Faculty getFacultyMember(int id) { 
		
		String sql = "SELECT * FROM public.faculty WHERE id = ?"; 
		Object[] args = { id };
		
		Faculty res = jdbcTemplate.queryForObject(sql, new FacultyRowMapper(), args);
		
		return res; 
	}
	
	public void createFacultyMember(String firstName, String lastName) { 
		
		//this SQL was used when creating table to have auto-incremented id primary key
		// 		ALTER TABLE public.faculty ADD COLUMN id BIGSERIAL PRIMARY KEY;
		//Note: primary key id should be unique and unpredictable as opposed to auto-incremented but this is for demo purposes
		
		String sql = "INSERT INTO public.faculty (first_name, last_name) VALUES (?, ?)"; 
		Object[] args = {firstName, lastName};
		
		jdbcTemplate.update(sql, args);
	}

	public int updateFacultyMember(int id, Faculty faculty) { 
		
		String sql = 
				"UPDATE public.faculty " +
				"SET first_name = ?, last_name = ? " + 
				"WHERE id = ?"; 
		Object[] args = { faculty.getFirstName(), faculty.getLastName(), id };
		
		int res = jdbcTemplate.update(sql, args);
		
		return res; 
	}
	
	public int deleteFacultyMember(int id) { 
		
		String sql = 
				"DELETE FROM public.faculty WHERE id = ?"; 
		Object[] args = { id };
		
		int res = jdbcTemplate.update(sql, args);
		
		return res; 
	}
}
