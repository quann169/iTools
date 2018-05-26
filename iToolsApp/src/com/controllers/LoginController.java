/**
 * 
 */
package com.controllers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
			while (rs.next()) {
				System.out.println(rs);
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			mysqlConnect.disconnect();
		}
	}

}
