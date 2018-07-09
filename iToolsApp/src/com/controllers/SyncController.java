/**
 * 
 */
package com.controllers;

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
		
		
		
		
		List<String> result = new ArrayList<>();
		return result;
	}

}
