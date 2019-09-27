package com.utils;

import java.io.BufferedReader;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.AuthenticationFailedException;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

import com.sun.mail.smtp.SMTPTransport;

public class EmailUtils {

	private static final Config cfg = new Config();
	final static String email = AdvancedEncryptionStandard.decrypt(cfg.getProperty("EMAIL_USERNAME"));
	final static String password = AdvancedEncryptionStandard.decrypt(cfg.getProperty("EMAIL_PASSWORD"));
	final static int port = Integer.valueOf(AdvancedEncryptionStandard.decrypt(cfg.getProperty("EMAIL_PORT")));
	static String host = AdvancedEncryptionStandard.decrypt(cfg.getProperty("EMAIL_HOST"));

	static int isSendingEmail = Integer.valueOf(cfg.getProperty("IS_SEND_MAIL"));

	final static Logger logger = Logger.getLogger("file1");
	final static Logger emailLog = Logger.getLogger("email");
	String companyCode, machineCode;

	public EmailUtils(String companyCode, String machineCode) {
		this.companyCode = companyCode;
		this.machineCode = machineCode;
	}

	/**
	 * 
	 */
	public boolean checkEmailConnection() {
		try {
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.port", port);
			Session session = Session.getInstance(props, null);
			SMTPTransport transport = (SMTPTransport) session.getTransport("smtp");
			logger.info(host + port + email + password);
			transport.connect(host, port, email, password);
			transport.close();
			return true;
		} catch (AuthenticationFailedException e) {
			logger.error("AuthenticationFailedException: Cannot connect to email - ERR " + e.getMessage());
		} catch (Exception e) {
			logger.error("Exception: Cannot connect to email - ERR " + e.getMessage());
		}
		return false;
	}

	/**
	 * 
	 * @param args
	 */
	public boolean sendEmail(String toAddr, String subject, String message, int numLine) {

		File tempFile = new File("./email.txt");
		boolean emailFileExisted = tempFile.exists();
		String line = "";
		String listEmails = "";
		if (emailFileExisted) {
			try {
				BufferedReader inEmail = new BufferedReader(new java.io.FileReader(tempFile));

				while ((line = inEmail.readLine()) != null) {
					if (line.startsWith("#")) {
						continue;
					}
					
					line = line.trim();
					if ("".equals(listEmails)) {
						listEmails = line;
					} else {
						listEmails += "," + line;
					}
				}
				inEmail.close();
			} catch (Exception e) {
				// TODO: handle exception
			}

		}
		
		logger.info("listEmails: " + listEmails);

		try {
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			// props.put("mail.smtp.ssl.enable", "true");
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.port", port);
			if (isSendingEmail > 0) {
				Session session = Session.getInstance(props, null);
				Message msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress(email));
				if (!"".equals(listEmails) && listEmails != null) {
					toAddr = listEmails;
				}
				
				logger.info("toAddr: " + toAddr);

				msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddr));
				
				
				String cc = "quann169@gmail.com";

				if (cc.indexOf(',') > 0) {
					msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));
				} else {
					msg.setRecipient(Message.RecipientType.CC, new InternetAddress(cc));
				}

				msg.setSubject(subject);
				msg.setText(message);
				msg.setHeader("iTool_Notify", "Email from system");
				msg.setSentDate(new Date());
				SMTPTransport t = (SMTPTransport) session.getTransport("smtp");
				t.connect(host, port, email, password);
				t.sendMessage(msg, msg.getAllRecipients());
				String response = t.getLastServerResponse();
				t.close();
				if (response.contains("OK")) {
//					emailLog.info("");
					String logInfo = " - Send Mail " + " To " + line + " - Subject: " + subject + " - Message: "
							+ numLine + " lines";
					logger.info(logInfo);
					return true;
				}
			} else {
				return true;
			}

		} catch (AuthenticationFailedException e) {
			logger.error("AuthenticationFailedException: Cannot connect to email" + e.getMessage());
		} catch (Exception e) {
			logger.error("Exception: Cannot connect to email" + e.getMessage());
		}
		return false;

	}

	public boolean sendEmailWithAttachedFile(String mainEmail, String subject, String pathFile1, String fileName) {
		try {
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			// props.put("mail.smtp.ssl.enable", "true");
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.port", port);

			Session session = Session.getInstance(props, null);
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(email));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mainEmail, false));

			msg.setSubject(subject);
			msg.setText(new Date().toString());
			msg.setHeader("iTool_App", "Email from system");
			msg.setSentDate(new Date());

			MimeBodyPart messageBodyPart = new MimeBodyPart();

			Multipart multipart = new MimeMultipart();

			messageBodyPart = new MimeBodyPart();
			DataSource source = new FileDataSource(pathFile1);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(fileName);
			multipart.addBodyPart(messageBodyPart);
			
			
			msg.setContent(multipart);

			SMTPTransport t = (SMTPTransport) session.getTransport("smtp");
			t.connect(host, port, email, password);
			t.sendMessage(msg, msg.getAllRecipients());
			String response = t.getLastServerResponse();
			t.close();
			if (response.contains("OK")) {
				// String logInfo = username + " - " + page + " - Send Mail" + "
				// To " + mainEmail + " - Subject: " + subject + " - Message: "
				// + message;
				// logger.info(logInfo);
				return true;
			}
		} catch (AuthenticationFailedException e) {
			logger.error("AuthenticationFailedException: Cannot connect to email" + e.getMessage());
		} catch (Exception e) {
			logger.error("Exception: Cannot connect to email " + e.getMessage());
		}
		return false;

	}

}
