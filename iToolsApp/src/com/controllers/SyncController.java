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
	public List<String> syncDataManually(String companyCode, String machineCode, String productMode) {
		

		try {
			syncDataAutomatically(companyCode, machineCode, productMode);
		} catch (Exception e) {
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
	public List<String> syncDataAutomatically(String companyCode, String machineCode, String productMode) {

		CallableStatement cs = null;
		try {
			logger.info("Start SyncHostToLocal");
			long startTime = System.currentTimeMillis();
			cs = mysqlConnect.connect().prepareCall("{call SyncHostToLocal_Assessor(?,?,?)}");
			cs.registerOutParameter(3, Types.LONGVARCHAR);
			cs.setString(1, companyCode);
			cs.setString(2, machineCode);
			cs.execute();
			String str = cs.getString(3);
			logger.info("SyncHostToLocal_Assessor" + str);
			
			cs = mysqlConnect.connect().prepareCall("{call SyncHostToLocal_RoleAssessor(?,?,?)}");
			cs.registerOutParameter(3, Types.LONGVARCHAR);
			cs.setString(1, companyCode);
			cs.setString(2, machineCode);
			cs.execute();
			str = cs.getString(3);
			logger.info("SyncHostToLocal_RoleAssessor" + str);
			
			cs = mysqlConnect.connect().prepareCall("{call SyncHostToLocal_Machine(?,?,?)}");
			cs.registerOutParameter(3, Types.LONGVARCHAR);
			cs.setString(1, companyCode);
			cs.setString(2, machineCode);
			cs.execute();
			str = cs.getString(3);
			logger.info("SyncHostToLocal_Machine: " + str);
			
			cs = mysqlConnect.connect().prepareCall("{call SyncHostToLocal_Company(?,?,?)}");
			cs.registerOutParameter(3, Types.LONGVARCHAR);
			cs.setString(1, companyCode);
			cs.setString(2, machineCode);
			cs.execute();
			str = cs.getString(3);
			logger.info("SyncHostToLocal_Company: " + str);
			
			cs = mysqlConnect.connect().prepareCall("{call SyncHostToLocal_CompanyMachine(?,?,?)}");
			cs.registerOutParameter(3, Types.LONGVARCHAR);
			cs.setString(1, companyCode);
			cs.setString(2, machineCode);
			cs.execute();
			str = cs.getString(3);
			logger.info("SyncHostToLocal_CompanyMachine: " + str);
			
			cs = mysqlConnect.connect().prepareCall("{call SyncHostToLocal_Tools(?,?,?)}");
			cs.registerOutParameter(3, Types.LONGVARCHAR);
			cs.setString(1, companyCode);
			cs.setString(2, machineCode);
			cs.execute();
			str = cs.getString(3);
			logger.info("SyncHostToLocal_Tools: " + str);

			long duration = System.currentTimeMillis() - startTime;
			startTime = System.currentTimeMillis();
			logger.info("End SyncHostToLocal: " + duration / 1000 + "s");

			if (!productMode.equals("Dev")) {

				logger.info("Start SyncLocalToHost");
				startTime = System.currentTimeMillis();


				cs = mysqlConnect.connect().prepareCall("{call SyncLocalToHost_ToolsMachineTray(?,?,?)}");
				cs.registerOutParameter(3, Types.LONGVARCHAR);
				cs.setString(1, companyCode);
				cs.setString(2, machineCode);
				cs.execute();
				str = cs.getString(3);
				logger.info("SyncLocalToHost_ToolsMachineTray: " + str);
				
				cs = mysqlConnect.connect().prepareCall("{call SyncLocalToHost_WorkingTransaction(?,?,?)}");
				cs.registerOutParameter(3, Types.LONGVARCHAR);
				cs.setString(1, companyCode);
				cs.setString(2, machineCode);
				cs.execute();
				str = cs.getString(3);
				logger.info("SyncLocalToHost_WorkingTransaction: " + str);

				duration = System.currentTimeMillis() - startTime;
				logger.info("End SyncLocalToHost: " + duration / 1000 + "s");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		} finally {
			mysqlConnect.disconnect();
		}
		List<String> result = new ArrayList<>();
		return result;
	}

}
