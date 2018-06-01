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
import com.models.Role;
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
		
	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public Assessor validateUser(String username, String password) {
		String sql = "SELECT AssessorID, UserName, FirstName, LastName, CompanyCode FROM Assessor where Assessor.UserName='" + username.toLowerCase() + "' and Password=md5('" + password
				+ "');";
		// System.out.println(sql);
		try {
			PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
			ResultSet rs = statement.executeQuery(sql);
			if (rs.isBeforeFirst()) {
				while (rs.next()) {
					String companyCode = rs.getString(5);
					
					Assessor user = new Assessor(username, password, companyCode);
					user.setFirstName(rs.getString(3));
					user.setLastName(rs.getString(4));
					return user;
				}
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
		String sql = "SELECT AssessorID, UserName, Password, CompanyId FROM Assessor where Assessor.IsActive=1;";
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
			e.printStackTrace();
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
				+ " inner join roles on roles.RoleID = roleassessor.RoleID where assessor.CompanyCode = '"
				+ companyCode + "' and assessor.UserName= '" + userName.toLowerCase() + "'";
//		System.out.println(sql);
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
			e.printStackTrace();
			return listAllRoles;
		} finally {
			mysqlConnect.disconnect();
		}
	}
}
