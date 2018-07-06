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
import com.models.Role;
import com.utils.MysqlConnect;
import com.utils.StringUtils;

/**
 * @author svi-quannguyen
 *
 */
public class LoginController {

	MysqlConnect mysqlConnect = new MysqlConnect();
	final static Logger logger = Logger.getLogger(LoginController.class);

	/**
	 * 
	 */
	public LoginController() {

	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public Assessor validateUser(String username, String password) {
		String sql = "SELECT AssessorID, UserName, FirstName, LastName, CompanyCode, IsFirstTimeLogin FROM Assessor where Assessor.UserName='"
				+ username.toLowerCase() + "' and (Password=md5('" + password + "') or LastPassword=md5('" + password
				+ "'));";
		// System.out.println(sql);
		logger.info(sql);
		try {
			PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
			ResultSet rs = statement.executeQuery(sql);
			if (rs.isBeforeFirst()) {
				while (rs.next()) {
					String companyCode = rs.getString(5);
					Assessor user = new Assessor(username, password, companyCode);
					user.setAssessorId(Integer.valueOf(rs.getString(1)));
					user.setFirstName(rs.getString(3));
					user.setUsername(username);
					user.setPassword(password);
					user.setLastName(rs.getString(4));
					user.setFirstTimeLogin(StringUtils.converToBoolean(rs.getString(6)));
					return user;
				}
			} else {
				return null;
			}
		} catch (SQLException e) {
			logger.info(e.getMessage());
			return null;
		} finally {
			mysqlConnect.disconnect();
		}
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public List<Assessor> getAllUsers() {
		String sql = "SELECT AssessorID, UserName, Password, CompanyCode FROM Assessor where Assessor.IsActive=1;";
		logger.info(sql);
		List<Assessor> listAllUsers = new ArrayList<>();
		try {
			PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
			ResultSet rs = statement.executeQuery(sql);

			while (rs.next()) {
				int userId = Integer.parseInt(rs.getString(1));
				String username = rs.getString(2);
				String password = rs.getString(3);
				String companyCode = rs.getString(4);

				Assessor user = new Assessor(username, password, companyCode);
				user.setAssessorId(userId);
				listAllUsers.add(user);
			}
			return listAllUsers;
		} catch (SQLException e) {
			logger.info(e.getMessage());
			return listAllUsers;
		} finally {
			mysqlConnect.disconnect();
		}
	}

	/**
	 * 
	 */
	public List<Role> getUserRoles(String userName, String companyCode) {
		String sql = "select distinct RoleName "
				+ " from assessor inner join roleassessor  on assessor.AssessorID = roleassessor.AssessorID"
				+ " inner join roles on roles.RoleID = roleassessor.RoleID where assessor.CompanyCode = '" + companyCode
				+ "' and assessor.UserName= '" + userName.toLowerCase() + "'";
		// System.out.println(sql);
		logger.info(sql);
		List<Role> listAllRoles = new ArrayList<>();
		try {
			PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
			ResultSet rs = statement.executeQuery(sql);

			while (rs.next()) {
				String roleName = rs.getString(1);
				Role role = new Role(0, roleName);

				listAllRoles.add(role);
			}
			return listAllRoles;
		} catch (SQLException e) {
			logger.info(e.getMessage());
			return listAllRoles;
		} finally {
			mysqlConnect.disconnect();
		}
	}

	/**
	 * 
	 * @return
	 */
	public List<String> getDatabaseVersion() {
		List<String> result = new ArrayList<>();
		result.add(MysqlConnect.getHost());
		result.add(MysqlConnect.getUsername());
		result.add(MysqlConnect.getPassword());
		result.add(MysqlConnect.getDatabaseName());
		result.add(MysqlConnect.getPort());
		
		
		String lastSync = "";
		String sql = "select SyncDate from SyncHistory order by SyncHistory.SyncDate desc limit 1;";
//		logger.info(sql);
		try {
			PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
			ResultSet rs = statement.executeQuery(sql);

			while (rs.next()) {
				lastSync = rs.getString(1);
			}
		} catch (SQLException e) {
			logger.info(e.getMessage());
		} finally {
			mysqlConnect.disconnect();
		}
		
		String version = "";
		String updatedDate = "";
		sql = "select iToolAppDatabase, UpdatedDate from DatabaseVersion";
//		logger.info(sql);
		try {
			PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
			ResultSet rs = statement.executeQuery(sql);

			while (rs.next()) {
				version = rs.getString(1);
				updatedDate = rs.getString(2);
			}
		} catch (SQLException e) {
			logger.info(e.getMessage());
		} finally {
			mysqlConnect.disconnect();
		}
		
		result.add(version + " - " + updatedDate);
		result.add(lastSync);
		return result;
	}

}
