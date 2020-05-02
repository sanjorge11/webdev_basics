package com.serverside.ToDoListApp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.serverside.ToDoListApp.domain.Item;

@Repository
public class ItemDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	//DataSource object contains the database connection information, 
	//such as: jdbcUrl, driverClassName, userName, password
	//we @Autowired this setter so that the DataSource info is 
	//assigned to the jdbcTemplate on application startup bootstrapping
	@Autowired
    public void setDataSource(final DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
	
	public class ItemRowMapper implements RowMapper<Item> {
		@Override
		public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
		      Item item = new Item();
		      
		      item.setId(rs.getInt("id"));
		      item.setCompleted(rs.getBoolean("completed"));
		      item.setDescription(rs.getString("description"));
		      
		      return item;
		}
	}
	
	public List<Item> getAllItems() { 
		
		String sql = "SELECT * FROM public.item"; 
		
		List<Item> res = jdbcTemplate.query(sql, new ItemRowMapper()); 
		
		return res;
	}
	
	public Item getItem(int id) { 
		
		String sql = "SELECT * FROM public.item WHERE id = ?"; 
		Object[] args = { id };
		
		Item res = jdbcTemplate.queryForObject(sql, new ItemRowMapper(), args);
		
		return res; 
	}
	
	public void createItem(int uniqueId, boolean completed, String description) { 
	
		String sql = "INSERT INTO public.item (id, completed, description) VALUES (?, ?, ?)"; 
		Object[] args = {uniqueId, completed, description};
		
		jdbcTemplate.update(sql, args);
	}

	public int updateItemCompletion(int id, boolean completed) { 
		
		String sql = 
				"UPDATE public.item " +
				"SET completed = ? " + 
				"WHERE id = ?"; 
		Object[] args = { completed, id };
		
		int res = jdbcTemplate.update(sql, args);
		
		return res; 
	}
	
	public int deleteItem(int id) { 
		
		String sql = 
				"DELETE FROM public.item WHERE id = ?"; 
		Object[] args = { id };
		
		int res = jdbcTemplate.update(sql, args);
		
		return res; 
	}
	
	public int getNextVal() { 
		String sql = "SELECT nextval('item_seq')"; 
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
}
