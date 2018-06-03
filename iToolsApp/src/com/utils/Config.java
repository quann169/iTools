package com.utils;

import java.util.Properties;

public class Config {
	Properties configFile;

	public Config() {
		configFile = new java.util.Properties();
		try {
			configFile.load(this.getClass().getClassLoader().getResourceAsStream("config.properties"));
		} catch (Exception eta) {
			eta.printStackTrace();
		}
	}

	public String getProperty(String key) {
		String value = this.configFile.getProperty(key).trim();
		return value;
	}
}
