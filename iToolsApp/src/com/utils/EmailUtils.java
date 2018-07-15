package com.utils;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.AuthenticationFailedException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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
				masterLogObj.insertLog(username, Enum.EMAIL, "", page, "",
						"To " + toAddr + " - Subject: " + subject + " - Message: " + message, companyCode, machineCode,
						StringUtils.getCurrentClassAndMethodNames());
				return true;
			}
		} catch (AuthenticationFailedException e) {
			logger.warn("AuthenticationFailedException: Cannot connect to email" + e.getMessage());
			masterLogObj.insertLog(username, Enum.EMAIL, "", page, "",
					"AuthenticationFailedException: Cannot connect to email" + e.getMessage(), companyCode, machineCode,
					StringUtils.getCurrentClassAndMethodNames());
		} catch (MessagingException e) {
			logger.warn("MessagingException: Cannot connect to email" + e.getMessage());
			masterLogObj.insertLog(username, Enum.EMAIL, "", page, "",
					"AuthenticationFailedException: Cannot connect to email" + e.getMessage(), companyCode, machineCode,
					StringUtils.getCurrentClassAndMethodNames());
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

			Session session = Session.getInstance(props, null);
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(email));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mainEmail, false));
			for (String ccEmail : listCCEmail) {
				msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmail, false));
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
				masterLogObj.insertLog(username, Enum.EMAIL, "",
						page, "", "To " + mainEmail + " - CC: " + listCCEmail + " - Subject: " + subject
								+ " - Message: " + message,
						companyCode, machineCode, StringUtils.getCurrentClassAndMethodNames());
				return true;
			}
		} catch (AuthenticationFailedException e) {
			logger.warn("AuthenticationFailedException: Cannot connect to email" + e.getMessage());
			masterLogObj.insertLog(username, Enum.EMAIL, "", page, "",
					"AuthenticationFailedException: Cannot connect to email" + e.getMessage(), companyCode, machineCode,
					StringUtils.getCurrentClassAndMethodNames());
		} catch (MessagingException e) {
			logger.warn("MessagingException: Cannot connect to email" + e.getMessage());
			masterLogObj.insertLog(username, Enum.EMAIL, "", page, "",
					"AuthenticationFailedException: Cannot connect to email" + e.getMessage(), companyCode, machineCode,
					StringUtils.getCurrentClassAndMethodNames());
		}
		return false;

	}

}
