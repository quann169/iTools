/**
 * 
 */
package com.controllers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.utils.MysqlConnect;

/**
 * @author svi-quannguyen
 *
 */
public class TransactionController {

	MysqlConnect mysqlConnect = new MysqlConnect();
	final static Logger logger = Logger.getLogger(TransactionController.class);
	/**
	 * 
	 */
	public TransactionController() {

	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @return
	 */

	public int insertTransaction(String userId, String companyCode, String machineCode, String woCode, String opCode,
			String toolCode, String trayIndex, String quantity, String transactionType, String transactioStatus) {
		String sql = "insert into WorkingTransaction( TransactionDate, MachineCode, CompanyCode, AssessorID, WOCode, OPCode, ToolCode, "
				+ "TrayIndex, Quantity, UpdatedDate, TransactionStatus, TransactionType) values (now(), '" + machineCode
				+ "', '" + companyCode + "', '" + userId + "', '" + woCode + "', '" + opCode + "', '" + toolCode
				+ "', '" + trayIndex + "', '" + quantity + "', now(), '" + transactioStatus + "', '" + transactionType + "');";
		logger.info(sql);
		try {
			PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			int rs = statement.executeUpdate();
			if (rs == 0) {
				throw new SQLException("Creating transaction failed, no rows affected.");
			}
			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					return generatedKeys.getInt(1);
				} else {
					throw new SQLException("Creating transaction failed, no ID obtained.");
				}
			}
		} catch (SQLException e) {
			logger.info(e.getMessage());
			return -1;
		} finally {
			mysqlConnect.disconnect();
		}
	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @return
	 */

	public int updateTransaction(int transactionID, String columnName, String value) {
		String sql = "update WorkingTransaction set UpdatedDate=now(), " + columnName + "='" + value
				+ "' where WorkingTransactionID = " + transactionID + ";";
		logger.info(sql);
		try {
			PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			int rs = statement.executeUpdate();
			return rs;
		} catch (SQLException e) {
			logger.info(e.getMessage());
			return -1;
		} finally {
			mysqlConnect.disconnect();
		}
	}

}
