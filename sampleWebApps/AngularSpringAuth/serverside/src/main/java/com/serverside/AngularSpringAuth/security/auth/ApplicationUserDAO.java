package com.serverside.AngularSpringAuth.security.auth;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.serverside.AngularSpringAuth.security.appConfig.UserRole;
import com.serverside.AngularSpringAuth.api.dao.BaseDAO;
import com.serverside.AngularSpringAuth.security.appConfig.ApplicationResources;

 
@Repository
public class ApplicationUserDAO extends BaseDAO {

	private final PasswordEncoder passwordEncoder; 
	private final ApplicationResources applicationResources; 
	
	@Autowired
	public ApplicationUserDAO(PasswordEncoder passwordEncoder, ApplicationResources applicationResources) { 
		this.passwordEncoder = passwordEncoder;
		this.applicationResources = applicationResources; 
	}
	
	
	public class ApplicationUserRowMapper implements RowMapper<ApplicationUser> {
		@Override
		public ApplicationUser mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			List<String> applicationRoles = applicationResources.getRoles(); 
			String userRole = rs.getString("role").trim(); 
			
			if(!applicationRoles.contains(userRole.toUpperCase())) return null; 
			
			ApplicationUser user = new ApplicationUser(
					rs.getString("username").trim(), 
					rs.getString("password").trim(), 
					UserRole.valueOf(userRole).getGrantedAuthorities(), 
					rs.getBoolean("isaccountnonexpired"), 
					rs.getBoolean("isaccountnonlocked"), 
					rs.getBoolean("iscredentialsnonexpired"), 
					rs.getBoolean("isenabled")
			);

		      
		      return user;
		}
	}
	
	public ApplicationUser selectApplicationUserByUsername(String username) {
		Object[] args = { username }; 
		String sql = "SELECT * FROM private.app_user WHERE username = ?"; 
		
		List<ApplicationUser> users = jdbcTemplate.query(sql, args, new ApplicationUserRowMapper());
		
		if(users == null || users.size() == 0) return null; 	//should not be more than one
		
		return users.get(0); 
	}
	
	public int registerUser(String username, String password, String role) {
		Object[] args = { username, passwordEncoder.encode(password), role, true, true, true, true }; 
		String sql = "INSERT INTO private.app_user VALUES (?, ?, ?, ?, ?, ?, ?)"; 
		
		int res = jdbcTemplate.update(sql, args);
		
		return res; 
	}

}
