/**
 * 
 */
package com.controllers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.message.Enum;
import com.models.Assessor;
import com.utils.MysqlConnect;
import com.utils.StringUtils;

/**
 * @author svi-quannguyen
 *
 */
public class LogController {

	MysqlConnect mysqlConnect = new MysqlConnect();
	final static Logger logger = Logger.getLogger(LogController.class);

	/**
	 * 
	 */
	public LogController() {

	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean insertLog(String username, Enum tableName, Object columnName, Enum action, String oldvalue,
			String newValue, String companyCode, String machineCode, String note, int columnId) {

		String columnNameText = "";
		if (columnName instanceof Enum) {
			columnNameText = ((Enum) columnName).text();
		} else if (columnName instanceof String && columnName != null) {
			columnNameText = columnName.toString();
		}

		if (columnId <= 0) {
			return insertLog(username, tableName, columnNameText, action, oldvalue, newValue, companyCode, machineCode,
					note);
		}

		String sql = "insert into masterlog (LogDate, AssessorName, TblName, ColumnName, ColumnId, Action, OldValue, NewValue, companyCode, machineCode, Notes)  values (now(), '"
				+ username + "', '" + tableName.text() + "', '" + columnNameText + "', '" + columnId + "', '"
				+ action.text() + "', '" + oldvalue + "', '" + newValue + "', '" + companyCode + "', '" + machineCode
				+ "', '" + note + "');";
		logger.info(sql);
		try {
			PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
			int result = statement.executeUpdate(sql);
			if (result > 0) {
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
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean insertLog(String username, Enum tableName, Object columnName, Enum action, String oldvalue,
			String newValue, String companyCode, String machineCode, String note) {

		String columnNameText = "";
		if (columnName instanceof Enum) {
			columnNameText = ((Enum) columnName).text();
		} else if (columnName instanceof String && columnName != null) {
			columnNameText = columnName.toString();
		}

		String sql = "insert into MasterLog (LogDate, AssessorName, TblName, ColumnName, Action, OldValue, NewValue, companyCode, machineCode, Notes)  values (now(), '"
				+ username + "', '" + tableName.text() + "', '" + columnNameText + "', '" + action.text() + "', '"
				+ oldvalue + "', '" + newValue + "', '" + companyCode + "', '" + machineCode + "', '" + note + "');";

		logger.info(sql);

		try {
			PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
			// logger.info("======================MasterLog prepareStatement
			// done");
			int result = statement.executeUpdate(sql);
			// logger.info("======================MasterLog executeUpdate
			// done");
			if (result > 0) {
				// logger.info("======================MasterLog insertLog
				// done");
				return true;
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			// logger.info("======================MasterLog insertLog fail");
			return false;
		} finally {
			mysqlConnect.disconnect();
			// logger.info("======================MasterLog disconnect done");
		}
		return false;
	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public String getLatestEmailLog() {

		String sql = "select NewValue from MasterLog where Action = 'SendLog' order by NewValue desc limit 1;";
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(0);
		String dateTime = dateFormat.format(date);
		logger.info(sql);

		try {
			PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
			ResultSet rs = statement.executeQuery(sql);

			while (rs.next()) {
				String newValue = rs.getString(1);
				return newValue;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return dateTime;
		} finally {
			mysqlConnect.disconnect();
		}
		System.out.println(dateTime);
		return dateTime;
	}

	/**
	 * 
	 * @param strDate
	 * @return
	 */
	public boolean updateLatestEmailLog(String strDate) {

		String sql = "insert into MasterLog(Action, NewValue) values('SendLog', '" + strDate + "')";
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

}
