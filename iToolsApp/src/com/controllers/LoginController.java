/**
 * 
 */
package com.controllers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
	public Assessor validateUser(String username, String password, String machineCode) {
		String sql = "SELECT AssessorID, UserName, FirstName, LastName, CompanyCode, IsFirstTimeLogin, IsLocked FROM Assessor where Assessor.IsActive = 1 and Assessor.UserName='"
				+ username.toLowerCase() + "' and (Password=md5('" + password + "') or LastPassword=md5('" + password
				+ "')) and MachineCode like '%" + machineCode + "%';";
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
					user.setLocked(rs.getString(7));
					return user;
				}
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		} finally {
			mysqlConnect.disconnect();
		}
		return null;
	}

	/**
	 * 
	 * @param companyCode
	 * @param username
	 * @return
	 */
	public boolean updateFailTimes(String companyCode, String username) {

		String sql = "update Assessor set FailTimes = FailTimes + 1 where Assessor.UserName = '" + username
				+ "' and Assessor.CompanyCode = '" + companyCode + "' ;";
		logger.info(sql);

		try {

			PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
			int countResult = statement.executeUpdate();
		} catch (Exception e) {
			logger.info(e.getMessage());
			return false;
		}

		sql = "update federated_assessor set FailTimes = FailTimes + 1 where federated_assessor.UserName = '" + username
				+ "' and federated_assessor.CompanyCode = '" + companyCode + "' ;";
		logger.info(sql);

		try {

			PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
			int countResult = statement.executeUpdate();
			if (countResult > 0) {
				return true;
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			return false;
		} finally {
			mysqlConnect.disconnect();
		}

		return true;
	}

	/**
	 * 
	 * @param companyCode
	 * @param username
	 * @return
	 */
	public boolean lockUser(String companyCode, String username) {

		String sql = "update Assessor set Assessor.IsLocked = 1 where Assessor.UserName = '" + username
				+ "' and Assessor.CompanyCode = '" + companyCode + "' and Assessor.FailTimes > 2;";
		logger.info(sql);

		try {

			PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
			int countResult = statement.executeUpdate();

		} catch (Exception e) {
			logger.info(e.getMessage());
			return false;
		}

		sql = "update federated_assessor set federated_assessor.IsLocked = 1 where federated_assessor.UserName = '"
				+ username + "' and federated_assessor.CompanyCode = '" + companyCode
				+ "' and federated_assessor.FailTimes > 2;";
		logger.info(sql);

		try {

			PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
			int countResult = statement.executeUpdate();
			if (countResult > 0) {
				return true;
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			return false;
		} finally {
			mysqlConnect.disconnect();
		}

		return false;
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
		} catch (Exception e) {
			logger.info(e.getMessage());
			return listAllUsers;
		} finally {
			mysqlConnect.disconnect();
		}
	}

	/**
	 * 
	 */
	public List<Role> getUserRoles(String userName, String companyCode, String companyCodeUH) {
		String sql = "select distinct RoleName "
				+ " from assessor inner join roleassessor  on assessor.AssessorID = roleassessor.AssessorID"
				+ " inner join roles on roles.RoleID = roleassessor.RoleID where assessor.CompanyCode = '" + companyCode
				+ "' and assessor.UserName= '" + userName + "'";
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

			if (listAllRoles.size() == 0) {
				sql = "select distinct RoleName "
						+ " from assessor inner join roleassessor  on assessor.AssessorID = roleassessor.AssessorID"
						+ " inner join roles on roles.RoleID = roleassessor.RoleID where assessor.CompanyCode = '"
						+ companyCodeUH + "' and assessor.UserName= '" + userName + "'";
				logger.info("Check Role in UH Com.....");
				logger.info(sql);

				statement = mysqlConnect.connect().prepareStatement(sql);
				rs = statement.executeQuery(sql);

				while (rs.next()) {
					String roleName = rs.getString(1);
					Role role = new Role(0, roleName);
					listAllRoles.add(role);
				}
			}

			return listAllRoles;
		} catch (Exception e) {
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

		String localMessage = "";
		String lastSync = "";
		String sql = "select SyncDate from SyncHistory order by SyncHistory.SyncDate desc limit 1;";
		// logger.info(sql);
		try {
			PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
			ResultSet rs = statement.executeQuery(sql);

			while (rs.next()) {
				lastSync = rs.getString(1);
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			localMessage = e.getMessage();
		} finally {
			mysqlConnect.disconnect();
		}

		String version = "";
		String updatedDate = "";
		sql = "select iToolAppDatabase, UpdatedDate from DatabaseVersion";
		// logger.info(sql);
		try {
			PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
			ResultSet rs = statement.executeQuery(sql);

			while (rs.next()) {
				version = rs.getString(1);
				updatedDate = rs.getString(2);
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
		} finally {
			mysqlConnect.disconnect();
		}

		result.add(version + " - " + updatedDate);
		result.add(lastSync);

		String hostMessage = "";
		sql = "select count(*) from federated_assessor";
		// logger.info(sql);
		try {
			PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
			ResultSet rs = statement.executeQuery(sql);

			while (rs.next()) {
				hostMessage = "OK";
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			hostMessage = e.getMessage();
		} finally {
			mysqlConnect.disconnect();
		}

		result.add(hostMessage);
		result.add(localMessage);

		return result;
	}

	public String getEmailUser(String companycode, String username) {
		String emailUser = "";
		String sql = "SELECT EmailAddress FROM Assessor where Assessor.UserName = '" + username
				+ "' and Assessor.CompanyCode = '" + companycode + "';";
		logger.info(sql);
		try {
			PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
			ResultSet rs = statement.executeQuery(sql);

			while (rs.next()) {
				emailUser = rs.getString(1);
				break;
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			return "";
		} finally {
			mysqlConnect.disconnect();
		}
		return emailUser;
	}

	public String getEmailUser(String username) {
		String emailUser = "";
		String sql = "SELECT EmailAddress FROM Assessor where Assessor.UserName = '" + username + "';";
		logger.info(sql);
		try {
			PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
			ResultSet rs = statement.executeQuery(sql);

			while (rs.next()) {
				emailUser = rs.getString(1);
				break;
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			return "";
		} finally {
			mysqlConnect.disconnect();
		}
		return emailUser;
	}

	public String getEmailAdmin() {
		String emailUser = "";
		String sql = "SELECT EmailAddress FROM Assessor where Assessor.UserName = 'admin';";
		logger.info(sql);
		try {
			PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
			ResultSet rs = statement.executeQuery(sql);

			while (rs.next()) {
				emailUser = rs.getString(1);
				break;
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			return "";
		} finally {
			mysqlConnect.disconnect();
		}
		return emailUser;
	}

	public String getEmailSubAdmin(String companyCode) {
		String emailUser = "";
		String sql = "select assessor.EmailAddress from roleassessor inner join assessor on roleassessor.AssessorID = assessor.AssessorID "
				+ " inner join roles on roleassessor.RoleID = roles.RoleID "
				+ "where roles.RoleName = 'SubAdmin' and assessor.CompanyCode = '" + companyCode + "';";
		logger.info(sql);
		try {
			PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
			ResultSet rs = statement.executeQuery(sql);

			while (rs.next()) {
				emailUser = rs.getString(1);
				break;
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			return "";
		} finally {
			mysqlConnect.disconnect();
		}
		return emailUser;
	}

}
