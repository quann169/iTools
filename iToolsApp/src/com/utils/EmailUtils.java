package com.utils;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.AuthenticationFailedException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

import com.controllers.LogController;
import com.message.Enum;
import com.sun.mail.smtp.SMTPTransport;

public class EmailUtils {

	private static final Config cfg = new Config();
	final static String email = AdvancedEncryptionStandard.decrypt(cfg.getProperty("EMAIL_USERNAME"));
	final static String password = AdvancedEncryptionStandard.decrypt(cfg.getProperty("EMAIL_PASSWORD"));
	final static int port = Integer.valueOf(AdvancedEncryptionStandard.decrypt(cfg.getProperty("EMAIL_PORT")));
	static String host = AdvancedEncryptionStandard.decrypt(cfg.getProperty("EMAIL_HOST"));
	
	static int isSendingEmail = Integer.valueOf(cfg.getProperty("IS_SEND_MAIL"));

	final static Logger logger = Logger.getLogger(EmailUtils.class);
	LogController masterLogObj = new LogController();
	String username, companyCode, machineCode;
	Enum page;

	public EmailUtils(Enum page, String username, String companyCode, String machineCode) {
		this.username = username;
		this.companyCode = companyCode;
		this.machineCode = machineCode;
		this.page = page;
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
			transport.connect(host, port, email, password);
			transport.close();
			return true;
		} catch (AuthenticationFailedException e) {
			logger.warn("AuthenticationFailedException: Cannot connect to email" + e.getMessage());
		} catch (MessagingException e) {
			logger.warn("MessagingException: Cannot connect to email" + e.getMessage());
		}
		return false;
	}

	/**
	 * 
	 * @param args
	 */
	public boolean sendEmail(String toAddr, String subject, String message) {

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
				msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddr, false));
				msg.setSubject(subject);
				msg.setText(message);
				msg.setHeader("iTool_App", "Email from system");
				msg.setSentDate(new Date());
				SMTPTransport t = (SMTPTransport) session.getTransport("smtp");
				t.connect(host, port, email, password);
				t.sendMessage(msg, msg.getAllRecipients());
				String response = t.getLastServerResponse();
				t.close();
				if (response.contains("OK")) {
					String logInfo = username + " - " + page + " - Send Mail" + " To " + toAddr + " - Subject: " + subject
							+ " - Message: " + message;
					logger.info(logInfo);
					return true;
				}
			} else {
				String logInfo = username + " - " + page + " - Send Mail" + " To " + toAddr + " - Subject: " + subject
						+ " - Message: " + message;
				logger.info(logInfo);
				return true;
			}
			
		} catch (AuthenticationFailedException e) {
			logger.error("AuthenticationFailedException: Cannot connect to email" + e.getMessage());
		} catch (MessagingException e) {
			logger.error("MessagingException: Cannot connect to email" + e.getMessage());
		}
		return false;

	}

	public boolean sendEmail(String mainEmail, List<String> listCCEmail, String subject, String message) {
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
				msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mainEmail, false));
	
				String cc = "";
	
				for (String ccEmail : listCCEmail) {
					cc += "," + ccEmail;
				}
	
				if (cc.startsWith(",")) {
					cc = cc.replaceFirst(",", "");
				}
	
				if (cc.indexOf(',') > 0) {
					msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));
				} else {
					msg.setRecipient(Message.RecipientType.CC, new InternetAddress(cc));
				}
	
				msg.setSubject(subject);
				msg.setText(message);
				msg.setHeader("iTool_App", "Email from system");
				msg.setSentDate(new Date());
				SMTPTransport t = (SMTPTransport) session.getTransport("smtp");
				t.connect(host, port, email, password);
				t.sendMessage(msg, msg.getAllRecipients());
				String response = t.getLastServerResponse();
				t.close();
				if (response.contains("OK")) {
					String logInfo = username + " - " + page + " - Send Mail" + " To " + mainEmail + " - CC: " + listCCEmail
							+ " - Subject: " + subject + " - Message: " + message;
					logger.info(logInfo);
					return true;
				}
			} else {
				String logInfo = username + " - " + page + " - Send Mail" + " To " + mainEmail + " - CC: " + listCCEmail
						+ " - Subject: " + subject + " - Message: " + message;
				logger.info(logInfo);
				return true;
			}
		} catch (AuthenticationFailedException e) {
			logger.error("AuthenticationFailedException: Cannot connect to email" + e.getMessage());
		} catch (MessagingException e) {
			logger.error("MessagingException: Cannot connect to email" + e.getMessage());
		}
		return false;

	}

	public boolean sendEmailWithAttachedFile(String mainEmail, String subject, String pathFile) {
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
			DataSource source = new FileDataSource(pathFile);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName("LogFile.txt");
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
		} catch (MessagingException e) {
			logger.error("MessagingException: Cannot connect to email" + e.getMessage());
		}
		return false;

	}

}
