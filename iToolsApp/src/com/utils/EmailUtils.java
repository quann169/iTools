package com.utils;

import java.util.Date;
import java.util.Properties;

import javax.mail.AuthenticationFailedException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.smtp.SMTPTransport;

public class EmailUtils {

	public EmailUtils() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		final String username = "itool_app@tqteam.net";
		final String password = "1qazxsw2!A";
		final int port = 25;
		String host = "mail.tqteam.net";

		try {
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
//			props.put("mail.smtp.ssl.enable", "true");
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.port", port);
			
			
			Session session = Session.getInstance(props, null);
	        Message msg = new MimeMessage(session);
	        msg.setFrom(new InternetAddress(username));;
	        msg.setRecipients(Message.RecipientType.TO,
	        InternetAddress.parse("ngngoctanthuong@gmail.com", false));
	        msg.setSubject("Test email from tqteam.vn");
	        msg.setText("Text Text Text Text Text Text Text");
	        msg.setHeader("Header1", "Header2");
	        msg.setSentDate(new Date());
	        SMTPTransport t =
	            (SMTPTransport)session.getTransport("smtp");
	        t.connect(host, port, username, password);
	        t.sendMessage(msg, msg.getAllRecipients());
	        System.out.println("Response: " + t.getLastServerResponse());
	        t.close();
			
			
			
			
//			props.put("mail.smtp.starttls.enable", "true");
			// or use getDefaultInstance instance if desired...
//			Session session = Session.getInstance(props, null);
//			Transport transport = session.getTransport("smtp");
//			transport.connect(host, port, username, password);
//			transport.close();
			System.out.println("success");
		} catch (AuthenticationFailedException e) {
			System.out.println("AuthenticationFailedException - for authentication failures");
			System.out.println(e.getMessage());
		} catch (MessagingException e) {
			System.out.println("for other failures");
			System.out.println(e.getMessage());
		}

	}

}
