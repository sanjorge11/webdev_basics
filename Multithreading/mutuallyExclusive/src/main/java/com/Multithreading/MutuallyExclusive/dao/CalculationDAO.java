package com.Multithreading.MutuallyExclusive.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class CalculationDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
    public void setDataSource(final DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

	
	public void addTwo(int num) { 
		
		num = num + 2;
	
		String sql = "INSERT INTO public.addTwo (value) VALUES (?)"; 
		Object[] args = { num };
		
		jdbcTemplate.update(sql, args);
	}
	
	public void multiplyTwo(int num) { 
		
		num = num * 2;
	
		String sql = "INSERT INTO public.multiplyTwo (value) VALUES (?)"; 
		Object[] args = { num };
		
		jdbcTemplate.update(sql, args);
	}

}
