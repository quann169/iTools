/**
 * 
 */
package com.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * @author QuanN
 *
 */
public class EncryptionTools {
	
	final static Logger logger = Logger.getLogger(EncryptionTools.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		List<String> listKeys = new ArrayList<String>();
		File file = new File("./keys.txt");
		if (file.exists() && file.canRead()) {
			// long fileLength = file.length();
			try {
				listKeys = readKeysFile(file, 0L);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		} else {
			logger.error("keys.txt not existed");
		}
		logger.info("All keys: " + listKeys);
		
		
		List<List<String>> keysValue = new ArrayList<>();
		file = new File("./config.properties");
		if (file.exists() && file.canRead()) {
			try {
				keysValue = readConfigFile(file, 0L, listKeys);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		} else {
			logger.error("config.properties not existed");
		}
		
		logger.info("Key Value: " + keysValue);
		
		for (List<String> listTmp : keysValue) {
			if (listTmp.size() == 2) {
				String valueRaw = listTmp.get(1);
				valueRaw = AdvancedEncryptionStandard.encrypt(valueRaw);
				listTmp.set(1, valueRaw);
			}
		}
		
		logger.info("Key Value Encrypted: " + keysValue);
		
		String fileNameOut = "./config_encrypted.txt";
		
		
		try {
			Files.deleteIfExists(Paths.get(fileNameOut));
			logger.info("Delete old file successfully.");
		} catch (Exception e) {
			logger.error(new Date() + ": Cannot delete log file");
		}
		
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(fileNameOut));
			String outCfg = "";
			for (List<String> list : keysValue) {
				if (list.size() == 1) {
					outCfg += list.get(0) + "\n";
				} else {
					String cfgTmp = list.get(0) + " = " + list.get(1);
					outCfg += cfgTmp + "\n";
				}
				
			}
			writer.write(outCfg);

			writer.close();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		
		
		logger.info("Write File Done: " + fileNameOut);
		
	}
	
	/**
	 * 
	 * @param file
	 * @param fileLength
	 * @return
	 * @throws IOException
	 */
	public static List<String> readKeysFile(File file, Long fileLength) throws IOException {
		
		List<String> result = new ArrayList<>();

		String line = "";
		BufferedReader in = new BufferedReader(new java.io.FileReader(file));
		in.skip(fileLength);

		while ((line = in.readLine()) != null) {
			line = line.trim();
			result.add(line);
		}
		in.close();


		return result;
	}
	
	
	/**
	 * 
	 * @param file
	 * @param fileLength
	 * @return
	 * @throws IOException
	 */
	public static List<List<String>> readConfigFile(File file, Long fileLength, List<String> listKeys) throws IOException {
		
		List<List<String>> result = new ArrayList<>();

		String line = "";
		BufferedReader in = new BufferedReader(new java.io.FileReader(file));
		in.skip(fileLength);

		while ((line = in.readLine()) != null) {
			line = line.trim();
			List<String> subList =  new ArrayList<>();
			boolean isMatched = false;
			for (String key : listKeys) {
				if (line.startsWith(key)) {
					isMatched = true;
					if (line.contains("=")) {
						String[] splitList = line.split("=");
						subList.add(splitList[0].trim());
						subList.add(splitList[1].trim());
						break;
					} else {
						subList.add(line);
						break;
					}
				}
			}
			if (!isMatched) {
				subList.add(line);
			}
			result.add(subList);
			
		}
		in.close();


		return result;
	}

}
