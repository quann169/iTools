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
public class UserController {

	MysqlConnect mysqlConnect = new MysqlConnect();

	/**
	 * 
	 */
	public UserController() {

	}

	/**
	 * 
	 * @return
	 */
	public List<Assessor> getUsersOfCompany(String companyCode) {
		String sql = "SELECT AssessorID, UserName, Password, CompanyCode, IsActive FROM Assessor where Assessor.IsActive=1 and Assessor.CompanyCode = '"
				+ companyCode + "';";
		List<Assessor> listAllUsers = new ArrayList<>();
		try {
			PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
			ResultSet rs = statement.executeQuery(sql);

			while (rs.next()) {
				int userId = Integer.parseInt(rs.getString(1));
				String username = rs.getString(2);
				String password = rs.getString(3);
				String isActive = rs.getString(5);
				Assessor user = new Assessor(username, password, companyCode);
				user.setAssessorId(userId);
				user.setActive(isActive);
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

	/**
	 * 
	 * @return
	 */
	public boolean updateIsActive(String username, String companyCode, int status) {
		String sql = "Update  Assessor set Assessor.IsActive = " + status + " where Assessor.Username = '" + username
				+ "' and Assessor.CompanyCode = '" + companyCode + "';";
		System.out.println(sql);
		try {
			PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
			int rows = statement.executeUpdate();
			System.out.printf("%d row(s) updated!\n", rows);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			mysqlConnect.disconnect();
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean updatePassword(String username, String companyCode, String password, boolean isFirstChange) {
		int isFirstChangeInt = 0;
		if (isFirstChange) {
			isFirstChangeInt = 1;
		}
		String sql = "Update  Assessor set Assessor.Password = md5('" + password + "') and Assessor.IsFirstTimeLogin = " + isFirstChangeInt + " where Assessor.Username = '" + username
				+ "' and Assessor.CompanyCode = '" + companyCode + "';";
		System.out.println(sql);
		try {
			PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
			int rows = statement.executeUpdate();
			System.out.printf("%d row(s) updated!\n", rows);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			mysqlConnect.disconnect();
		}
	}

}
