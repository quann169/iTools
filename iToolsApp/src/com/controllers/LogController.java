/**
 * 
 */
package com.controllers;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.message.Enum;
import com.utils.MysqlConnect;

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
		} catch (SQLException e) {
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
			logger.info("======================MasterLog prepareStatement done");
			int result = statement.executeUpdate(sql);
			logger.info("======================MasterLog executeUpdate done");
			if (result > 0) {
				logger.info("======================MasterLog insertLog done");
				return true;
			}
		} catch (SQLException e) {
			logger.info(e.getMessage());
			logger.info("======================MasterLog insertLog fail");
			return false;
		} finally {
			mysqlConnect.disconnect();
			logger.info("======================MasterLog disconnect done");
		}
		return false;
	}

}
