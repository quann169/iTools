package com.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.controllers.UserController;

public class MysqlConnect {
	
	final static Logger logger = Logger.getLogger(MysqlConnect.class);
	
	// init database constants
	private static final Config cfg = new Config();
	private static final String DATABASE_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DATABASE_NAME = "mDbName";
	private static final String USERNAME = "mDbUser";
	private static final String HOST = "mDbHost";
	private static final String PORT = "mDbPort";
	private static final String PASSWORD = "mDbPwds";
	private static final String MAX_POOL = "250";

	// init connection object
	private Connection connection;
	// init properties object
	private Properties properties;

	/**
	 * 
	 * @return
	 */
	private Properties getProperties() {
		if (properties == null) {
			properties = new Properties();
			properties.setProperty("user", AdvancedEncryptionStandard.decrypt(cfg.getProperty(USERNAME)));
			properties.setProperty("password", AdvancedEncryptionStandard.decrypt(cfg.getProperty(PASSWORD)));
			properties.setProperty("MaxPooledStatements", MAX_POOL);
			properties.setProperty("useSSL", "false");
			properties.setProperty("autoReconnect", "true");
		}
		return properties;
	}

	/**
	 * 
	 * @return
	 */
	public Connection connect() {
		if (connection == null) {
			try {
				Class.forName(DATABASE_DRIVER);

				String DATABASE_URL = "jdbc:mysql://" + AdvancedEncryptionStandard.decrypt(cfg.getProperty(HOST)) + ":"
						+ AdvancedEncryptionStandard.decrypt(cfg.getProperty(PORT)) + "/"
						+ AdvancedEncryptionStandard.decrypt(cfg.getProperty(DATABASE_NAME));
				connection = DriverManager.getConnection(DATABASE_URL, getProperties());
			} catch (ClassNotFoundException | SQLException e) {
				logger.info(e.getMessage());
			}
		}
		return connection;
	}

	/**
	 * 
	 */
	public void disconnect() {
		if (connection != null) {
			try {
				connection.close();
				connection = null;
			} catch (SQLException e) {
				logger.info(e.getMessage());
			}
		}
	}

	/**
	 * @return the databaseName
	 */
	public static String getDatabaseName() {
		return AdvancedEncryptionStandard.decrypt(cfg.getProperty(DATABASE_NAME));
	}

	/**
	 * @return the username
	 */
	public static String getUsername() {
		return AdvancedEncryptionStandard.decrypt(cfg.getProperty(USERNAME));
	}

	/**
	 * @return the host
	 */
	public static String getHost() {
		return AdvancedEncryptionStandard.decrypt(cfg.getProperty(HOST));
	}

	/**
	 * @return the port
	 */
	public static String getPort() {
		return AdvancedEncryptionStandard.decrypt(cfg.getProperty(PORT));
	}

	/**
	 * @return the password
	 */
	public static String getPassword() {
		return AdvancedEncryptionStandard.decrypt(cfg.getProperty(PASSWORD));
	}
	
}