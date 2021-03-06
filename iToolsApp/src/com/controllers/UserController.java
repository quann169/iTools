/**
 * 
 */
package com.controllers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.log4j.Logger;

import com.message.Enum;
import com.models.Assessor;
import com.utils.EmailUtils;
import com.utils.MysqlConnect;
import com.utils.RandomString;

/**
 * @author svi-quannguyen
 *
 */
public class UserController {

	MysqlConnect mysqlConnect = new MysqlConnect();
	final static Logger logger = Logger.getLogger(UserController.class);
	static LoginController ctlObj = new LoginController();

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
		String sql = "SELECT AssessorID, UserName, Password, CompanyCode, IsActive, IsLocked, FirstName, LastName FROM Assessor where Assessor.IsActive=1 and Assessor.CompanyCode = '"
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
				String firstName = rs.getString(7);
				String lastName = rs.getString(8);
				Assessor user = new Assessor(username, password, companyCode);
				user.setAssessorId(userId);
				user.setActive(isActive);
				user.setLocked(isLocked);
				user.setFirstName(firstName);
				user.setLastName(lastName);
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
	 * @return
	 */
	public boolean updateIsActive(String username, String companyCode, int status) {
		String sql = "Update Assessor set Assessor.IsActive = " + status + " where Assessor.Username = '" + username
				+ "' and Assessor.CompanyCode = '" + companyCode + "';";
		logger.info(sql);
		try {
			PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
			int rows = statement.executeUpdate();
			logger.info(rows + " row(s) updated!");
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		
		sql = "Update federated_assessor set federated_assessor.IsActive = " + status + " where federated_assessor.Username = '" + username
				+ "' and federated_assessor.CompanyCode = '" + companyCode + "';";
		logger.info(sql);
		try {
			PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
			int rows = statement.executeUpdate();
			logger.info(rows + " row(s) updated!");
			return true;
		} catch (Exception e) {
			logger.info(e.getMessage());
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
		String sql = "Update Assessor set Assessor.FailTimes = 0, IsFirstTimeLogin = 0, Assessor.IsLocked = " + status + " where Assessor.Username = '" + username
				+ "' and Assessor.CompanyCode = '" + companyCode + "';";
		logger.info(sql);
		try {
			PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
			int rows = statement.executeUpdate();
			logger.info(rows + " row(s) updated!");
		} catch (Exception e) {
			logger.info(e.getMessage());
			return false;
		}
		
		
		sql = "Update federated_assessor set federated_assessor.FailTimes = 0, IsFirstTimeLogin = 0, federated_assessor.IsLocked = " + status + " where federated_assessor.Username = '" + username
				+ "' and federated_assessor.CompanyCode = '" + companyCode + "';";
		logger.info(sql);
		try {
			PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
			int rows = statement.executeUpdate();
			logger.info(rows + " row(s) updated!");
			return true;
		} catch (Exception e) {
			logger.info(e.getMessage());
			return false;
		} finally {
			mysqlConnect.disconnect();
		}
	}

	/**
	 * 
	 * @return
	 */
	public String updatePassword(String username, String companyCode, String password, int isFirstChange) {

		String sql = "Update Assessor set Assessor.FailTimes = 0, Assessor.Password = md5('" + password + "'), Assessor.IsFirstTimeLogin = "
				+ isFirstChange + " where Assessor.Username = '" + username + "' and Assessor.CompanyCode = '"
				+ companyCode + "';";
		logger.info(sql);
		try {
			PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
			int rows = statement.executeUpdate();
			logger.info(rows + " row(s) updated!");
		} catch (Exception e) {
			logger.info(e.getMessage());
			return "Err";
		}
		
		sql = "Update federated_assessor set federated_assessor.FailTimes = 0, federated_assessor.Password = md5('" + password + "'), federated_assessor.IsFirstTimeLogin = "
				+ isFirstChange + " where federated_assessor.Username = '" + username + "' and federated_assessor.CompanyCode = '"
				+ companyCode + "';";
		logger.info(sql);
		try {
			PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
			int rows = statement.executeUpdate();
			logger.info(rows + " row(s) updated!");
			return password;
		} catch (Exception e) {
			logger.info(e.getMessage());
			return "Err";
		} finally {
			mysqlConnect.disconnect();
		}
	}

	/**
	 * 
	 * @param text
	 * @param email
	 * @param machineCode
	 * @param companyCode
	 * @return
	 */
	public boolean validateUsernameEmail(String username, String email, String companyCode, String machineCode) {
		String sql = "SELECT * FROM assessor " + "where assessor.UserName = '" + username
				+ "' and assessor.EmailAddress = '" + email + "' and assessor.CompanyCode = '" + companyCode + "';";
		logger.info(sql);
		try {
			PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
			ResultSet rs = statement.executeQuery(sql);

			while (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
		} finally {
			mysqlConnect.disconnect();
		}
		return false;
	}

	/**
	 * 
	 * @param userName
	 * @param email
	 * @param companyCode
	 * @param machineCode
	 */
	public boolean resetPassword(String userName, String email, String companyCode, String machineCode) {

		RandomString generate = new RandomString(8, ThreadLocalRandom.current());
		String passwordTmp = generate.nextString().toUpperCase();

		String sql = "Update Assessor set Assessor.LastPassword = md5('" + passwordTmp + "') "
				+ ", Assessor.IsFirstTimeLogin = 1 " + " where Assessor.Username = '" + userName
				+ "' and Assessor.CompanyCode = '" + companyCode + "';";
		logger.info(sql);
		try {
			PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
			int rows = statement.executeUpdate();
			logger.info(rows + " row(s) updated!");
			
			if (rows > 0) {
				String emailUser;
				if (companyCode.contains("MFC")) {
					emailUser = ctlObj.getEmailSubAdmin(companyCode);
				} else {
					emailUser = email;
				}
				EmailUtils emailUtils = new EmailUtils(Enum.FORGOT_PASS_PAGE, userName, companyCode, machineCode);
				
				emailUtils.sendEmail(emailUser, "New password reset", "New password: " + passwordTmp);
			} else {
				logger.warn(rows + " row(s) updated!");
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			return false;
		}
		
		sql = "Update federated_assessor set federated_assessor.LastPassword = md5('" + passwordTmp + "') "
				+ ", federated_assessor.IsFirstTimeLogin = 1 " + " where federated_assessor.Username = '" + userName
				+ "' and federated_assessor.CompanyCode = '" + companyCode + "';";
		logger.info(sql);
		try {
			PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
			int rows = statement.executeUpdate();
			logger.info(rows + " row(s) updated!");
			
			if (rows > 0) {
				String emailUser;
				if (companyCode.contains("MFC")) {
					emailUser = ctlObj.getEmailSubAdmin(companyCode);
				} else {
					emailUser = email;
				}
				EmailUtils emailUtils = new EmailUtils(Enum.FORGOT_PASS_PAGE, userName, companyCode, machineCode);
				emailUtils.sendEmail(emailUser, "New password reset", "New password: " + passwordTmp);
				return true;
			} else {
				logger.warn(rows + " row(s) updated!");
				return false;
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			return false;
		} finally {
			mysqlConnect.disconnect();
		}
	}

}
