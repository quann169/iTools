package com.views;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;

import org.apache.log4j.Logger;

import com.utils.AdvancedEncryptionStandard;
import com.utils.Config;
import com.utils.EmailUtils;

/**
 * A Simple service to register as Windows Service, the program will log on a
 * file when it starts, ends and every 10 seconds it will log is alive state
 * 
 * 
 */
public class CheckMissingTools {
	final static Logger logger = Logger.getLogger("file1");

	final static Logger emailLog = Logger.getLogger("email");
	private static final Config cfg = new Config();
	private static final String companyCode = AdvancedEncryptionStandard.decrypt(cfg.getProperty("COMPANY_CODE"));
	private static final String machineCode = AdvancedEncryptionStandard.decrypt(cfg.getProperty("MACHINE_CODE"));

	private static final String toEmail = cfg.getProperty("TO_EMAIL");

	static EmailUtils emailUtils = new EmailUtils(companyCode, machineCode);

	public static void main(String[] args) throws FileNotFoundException {

		/*
		 * Set the standard output stream as on a log file
		 */
		String pathname = "./log_check_missing_tool.log";
		FileOutputStream out = new FileOutputStream(new File(pathname), true);
		PrintStream printStream = new PrintStream(out);
		System.setOut(printStream);

		/*
		 * Add the shutdown hook
		 */
		Runtime.getRuntime().addShutdownHook(new Thread(new ShutdownHook()));

		Thread two = new Thread() {
			public void run() {

				while (true) {

					try {
						Thread.sleep(15 * 1000);

						logger.info("Check log to send email");
						readFile("./log/email_logging.log", "./log/board_logging.log");
						Thread.sleep(15 * 60 * 1000);
					} catch (Exception e) {
						logger.error(e.getMessage());
					}

				}

			}
		};

		two.start();

		onStart();
		while (true) {
			doWork();
			try {
				Thread.sleep(60 * 60 * 1000);
			} catch (InterruptedException e) {
				logger.error(e.getMessage());
			}

		}

	}

	public static void readFile(String fileLogEmail, String fileLogBoard) throws IOException {
		Long fileLength = 0L;
		String latestTime = "1970-01-01 00:30:00";
		String line = "";

		File tempFile = new File(fileLogEmail);
		boolean emailFileExisted = tempFile.exists();

		if (emailFileExisted) {
			BufferedReader inEmail = new BufferedReader(new java.io.FileReader(fileLogEmail));
			inEmail.skip(fileLength);
			while ((line = inEmail.readLine()) != null) {
				latestTime = line.trim();
			}
			inEmail.close();
		}
		// logger.info("============");
		// logger.info("latestTime: " + latestTime);

		File tempFile2 = new File(fileLogBoard);
		emailFileExisted = tempFile2.exists();

		if (emailFileExisted) {
			BufferedReader in = new BufferedReader(new java.io.FileReader(fileLogBoard));
			in.skip(fileLength);

			String dataLog = "";
			int numLine = 0;
			while ((line = in.readLine()) != null) {
				if (line.compareTo(latestTime) > 0 && line.contains("Board return with meassage")) {
					line = line.trim();
					String[] tmpLine = line.split("INFO");
					numLine++;
					dataLog += tmpLine[0] + "\n";
				}
			}
			in.close();
			if (!"".equals(dataLog)) {
				emailUtils.sendEmail(toEmail,
						"Warning Missing Item - " + companyCode + " - " + machineCode, dataLog,
						numLine);
				emailLog.warn("");
			}

		}
	}

	/**
	 * Log the alive state of the service
	 * 
	 * @param customMessage
	 *            A custom message
	 */
	private static void doWork() {

		ReadData obj = new ReadData();
		try {
			obj.executeAction();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			// logger.error(e);
		}
	}

	/**
	 * Log the boot of the service
	 */
	private static void onStart() {
		System.out.println("Starts at " + new Date());
		String outFilePath1 = "./log/board_logging.log";
		String outFilePath2 = "./log/email_logging.log";

		try {
			Thread.sleep(1 * 60 * 1000);
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
		}

		emailUtils.sendEmailWithAttachedFile("quann169@gmail.com",
				companyCode + " - " + machineCode + ": ITools Missing Items App Log - Start ", outFilePath1,
				"log_check_missing_tool.log");

		emailUtils.sendEmailWithAttachedFile("quann169@gmail.com",
				companyCode + " - " + machineCode + ": ITools Missing Items App Log - Start ", outFilePath2,
				"email_logging.log");

	}

	/**
	 * A shutdown hook
	 * 
	 * 
	 */
	private static class ShutdownHook implements Runnable {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			onStop();
		}

		/**
		 * Logs when the service is stopped
		 */
		private void onStop() {
			System.out.println("Ends at " + new Date());
			System.out.flush();
			System.out.close();
			String outFilePath1 = "./log/board_logging.log";
			String outFilePath2 = "./log/email_logging.log";
			emailUtils.sendEmailWithAttachedFile("quann169@gmail.com",
					companyCode + " - " + machineCode + ": ITools Missing Items App Log - Shutdown ", outFilePath1,
					"board_logging.log");
		}

	}

}