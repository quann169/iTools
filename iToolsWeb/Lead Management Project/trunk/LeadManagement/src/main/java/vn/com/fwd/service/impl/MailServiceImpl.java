package vn.com.fwd.service.impl;

import java.io.IOException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MailServiceImpl {

	private final static Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);
	
	private String mailSmtpHost;
	private String mailFrom;
	
	
	public String getMailSmtpHost() {
		return mailSmtpHost;
	}
	public void setMailSmtpHost(String mailSmtpHost) {
		this.mailSmtpHost = mailSmtpHost;
	}

	public String getMailFrom() {
		return mailFrom;
	}
	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}

	
	public void sendEmail2Client(String to, String body, String subject) throws MessagingException{
		String from = this.mailFrom;	
		System.out.println("MailServiceImpl.sendForgotPassword. emailTo="+to + " content="+body);
		this.sendEmail2Client(to, from, subject, body);	
	}
	
	private boolean sendEmail2Client(String to, String from, String subject, String body) throws MessagingException {
		String host = this.mailSmtpHost;

		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.setProperty("mail.smtp.host", host);

		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties);

		try {
			// send html message with attachment

			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Set Subject: header field
			message.setSubject(subject, "UTF-8");

			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();
			
			String htmlText = "<div><img src=\"cid:image\"></div>";
			htmlText += "</body></html>";
			body = body + htmlText;

			// Fill the message
			// messageBodyPart.setText(body);
			messageBodyPart.setContent(body, "text/html; charset=utf-8");

			// Create a multipar message
			Multipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);
			
			// second part (the image)
			messageBodyPart = new MimeBodyPart();
			DataSource fds = new FileDataSource("C:/Map_Final25.jpg");

			messageBodyPart.setDataHandler(new DataHandler(fds));
			messageBodyPart.setHeader("Content-ID", "<image>");

			// add image to the multipart
			multipart.addBodyPart(messageBodyPart);

			// Send the complete message parts
			message.setContent(multipart);

			// Send message
			Transport.send(message);
			// System.out.println("Sent with to email : " + to);
			return true;
		} catch (MessagingException mex) {
			logger.error(ExceptionUtils.getStackTrace(mex));
			throw mex;
		}
	}
}
