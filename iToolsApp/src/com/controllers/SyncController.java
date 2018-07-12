/**
 * 
 */
package com.controllers;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.utils.MysqlConnect;

/**
 * @author svi-quannguyen
 *
 */
public class SyncController {

	MysqlConnect mysqlConnect = new MysqlConnect();
	final static Logger logger = Logger.getLogger(SyncController.class);

	/**
	 * 
	 */
	public SyncController() {

	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public List<String> syncDataManually(String companyCode, String machineCode) {
		PreparedStatement ps = null;

		try {
			ps = mysqlConnect.connect().prepareStatement("call SyncHostToLocal(?, ?)");
			ps.setString(1, companyCode);
			ps.setString(2, machineCode);
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			return null;
		} finally {
			mysqlConnect.disconnect();
		}
		List<String> result = new ArrayList<>();
		return result;
	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public List<String> syncDataAutomatically(String companyCode, String machineCode) {

		CallableStatement cs = null;
		try {
			logger.info("Start SyncHostToLocal");
			long startTime = System.currentTimeMillis();
			cs = mysqlConnect.connect().prepareCall("{call SyncHostToLocal(?,?,?)}");
			cs.registerOutParameter(3, Types.LONGVARCHAR);
			cs.setString(1, companyCode);
			cs.setString(2, machineCode);
			cs.execute();
			String str = cs.getString(3);
			if (str != null) {
				logger.info(str);
			} else {
				logger.error(str);
			}

			long duration = System.currentTimeMillis() - startTime;
			startTime = System.currentTimeMillis();
			logger.info("End SyncHostToLocal: " + duration / 1000 + "s");

			logger.info("Start SyncLocalToHost");
			startTime = System.currentTimeMillis();
			cs = mysqlConnect.connect().prepareCall("{call SyncLocalToHost(?,?,?)}");
			cs.registerOutParameter(3, Types.LONGVARCHAR);
			cs.setString(1, companyCode);
			cs.setString(2, machineCode);
			cs.execute();
			str = cs.getString(3);
			if (str != null) {
				logger.info(str);
			} else {
				logger.error(str);
			}

			duration = System.currentTimeMillis() - startTime;
			logger.info("End SyncLocalToHost: " + duration / 1000 + "s");

		} catch (SQLException e) {
			logger.error(e.getMessage());
			return null;
		} finally {
			mysqlConnect.disconnect();
		}
		List<String> result = new ArrayList<>();
		return result;
	}

}
