/**
 * 
 */
package com.controllers;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.message.Enum;
import com.utils.MysqlConnect;

/**
 * @author svi-quannguyen
 *
 */
public class LogController {

	MysqlConnect mysqlConnect = new MysqlConnect();

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
		System.out.println(sql);
		try {
			PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
			int result = statement.executeUpdate(sql);
			if (result > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
		System.out.println(sql);
		try {
			PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
			int result = statement.executeUpdate(sql);
			if (result > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			mysqlConnect.disconnect();
		}
		return false;
	}

}
