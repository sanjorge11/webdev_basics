package com.serverside.AngularSpringAuth.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.serverside.AngularSpringAuth.domain.Student;

@Repository
public class StudentDAO extends BaseDAO {
	
	public class StudentRowMapper implements RowMapper<Student> {
		@Override
		public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
		      Student student = new Student();
		      
		      student.setId(rs.getInt("id"));
		      student.setGpa(rs.getDouble("gpa"));
		      student.setFirstName(rs.getString("firstname").trim());
		      student.setLastName(rs.getString("lastname").trim());
		      
		      return student;
		}
	}
	
	public List<Student> getAllStudents() { 
		
		String sql = "SELECT * FROM public.student"; 
		
		List<Student> res = jdbcTemplate.query(sql, new StudentRowMapper()); 
		
		return res;
	}
	
	public Student getStudent(int id) { 
		
		String sql = "SELECT * FROM public.student WHERE id = ?"; 
		Object[] args = { id };
		
		Student res = jdbcTemplate.queryForObject(sql, new StudentRowMapper(), args);
		
		return res; 
	}
	
	public void createStudent(int uniqueId, Student student) { 
	
		String sql = "INSERT INTO public.student (id, gpa, firstName, lastName) VALUES (?, ?, ?, ?)"; 
		Object[] args = {uniqueId, student.getGpa(), student.getFirstName(), student.getLastName()};
		
		jdbcTemplate.update(sql, args);
	}

	public int updateStudent(int uniqueId, Student student) { 
		
		String sql = 
				"UPDATE public.student " +
				"SET gpa = ? , firstName = ? , lastName = ? " + 
				"WHERE id = ?"; 
		Object[] args = {student.getGpa(), student.getFirstName(), student.getLastName(), uniqueId};
		
		int res = jdbcTemplate.update(sql, args);
		
		return res; 
	}
	
	public int deleteStudent(int id) { 
		
		String sql = 
				"DELETE FROM public.student WHERE id = ?"; 
		Object[] args = { id };
		
		int res = jdbcTemplate.update(sql, args);
		
		return res; 
	}
	
	public int getNextVal() { 
		String sql = "SELECT nextval('student_seq')"; 
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
}
