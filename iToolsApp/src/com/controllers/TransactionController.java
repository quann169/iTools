/**
 * 
 */
package com.controllers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
		} catch (Exception e) {
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
		} catch (Exception e) {
			logger.info(e.getMessage());
			return -1;
		} finally {
			mysqlConnect.disconnect();
		}
	}

	public List<List<String>> getQuantityTrayInfo(String machineCode) {
		String sql = " select MachineCode, ToolCode, TrayIndex, Quantity, UpdatedDate "
				+ "from toolsmachinetray "
				+ "where toolsmachinetray.IsActive = '1' and toolsmachinetray.MachineCode = '" + machineCode + "';";
		logger.info(sql);
		List<List<String>> result = new ArrayList<>();
		try {

			PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
			ResultSet rs = statement.executeQuery(sql);
			List<String> list1Row = new ArrayList<>();
			while (rs.next()) {
				list1Row.add(rs.getString(1));
				list1Row.add(rs.getString(2));
				list1Row.add(rs.getString(3));
				list1Row.add(rs.getString(4));
				list1Row.add(rs.getString(5));
				result.add(list1Row);
				list1Row = new ArrayList<>();
			}
			
		} catch (Exception e) {
			logger.info(e.getMessage());
			return null;
		} finally {
			mysqlConnect.disconnect();
		}
		return result;
	}

}
