/**
 * 
 */
package com.controllers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.models.Assessor;
import com.utils.MysqlConnect;

/**
 * @author svi-quannguyen
 *
 */
public class LoginController {

	MysqlConnect mysqlConnect = new MysqlConnect();

	/**
	 * 
	 */
	public LoginController() {
		// TODO Auto-generated constructor stub
	}

	public boolean validateUser(String username, String password) {
		String sql = "SELECT * FROM Assessor where Assessor.UserName='" + username + "' and Password=md5(" + password
				+ ");";
		try {
			PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
			ResultSet rs = statement.executeQuery(sql);
			if (!rs.isBeforeFirst() ) {    
			    return false;
			} else {
				return true;
			}			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			mysqlConnect.disconnect();
		}
	}

	
	public List<Assessor> getAllUsers() {
		String sql = "SELECT AssessorID, UserName, Password, CompanyId FROM Assessor where Assessor.IsActive=1;";
		List<Assessor> listAllUsers = new ArrayList<>();
		try {
			PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
			ResultSet rs = statement.executeQuery(sql);
			
			while (rs.next()) {
			    String userId = rs.getString(1);
			    String username = rs.getString(2);
			    String password = rs.getString(3);
			    String companyIdStr = rs.getString(4);
			    int companyId = -1;
			    try {
			    	companyId = Integer.parseInt(companyIdStr);
				} catch (Exception e) {
					
				}
			    
			    Assessor user = new Assessor(username, password, companyId);
			    listAllUsers.add(user);
			}
			return listAllUsers;
		} catch (SQLException e) {
			e.printStackTrace();
			return listAllUsers;
		} finally {
			mysqlConnect.disconnect();
		}
	}

}
