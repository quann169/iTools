/**
 * 
 */
package com.controllers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.models.Assessor;
import com.utils.MysqlConnect;

/**
 * @author svi-quannguyen
 *
 */
public class UserController {

	MysqlConnect mysqlConnect = new MysqlConnect();
	final static Logger logger = Logger.getLogger(UserController.class);
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
		String sql = "SELECT AssessorID, UserName, Password, CompanyCode, IsActive, IsLocked FROM Assessor where Assessor.IsActive=1 and Assessor.CompanyCode = '"
				+ companyCode + "';";
		logger.info(sql);
		List<Assessor> listAllUsers = new ArrayList<>();
		try {
			PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
			ResultSet rs = statement.executeQuery(sql);

			while (rs.next()) {
				int userId = Integer.parseInt(rs.getString(1));
				String username = rs.getString(2);
				String password = rs.getString(3);
				String isActive = rs.getString(5);
				String isLocked = rs.getString(6);
				Assessor user = new Assessor(username, password, companyCode);
				user.setAssessorId(userId);
				user.setActive(isActive);
				user.setLocked(isLocked);
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
		logger.info(sql);
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
	public boolean updateIsLocked(String username, String companyCode, int status) {
		String sql = "Update  Assessor set Assessor.IsLocked = " + status + " where Assessor.Username = '" + username
				+ "' and Assessor.CompanyCode = '" + companyCode + "';";
		logger.info(sql);
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
		logger.info(sql);
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
