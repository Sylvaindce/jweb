package com.jweb.beans;

import java.util.*;
import java.util.Map.Entry;

import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.http.HttpSession;

public class Mail {
	public static void sendNews(String emailto, Newsletter news) {
		final String username = "anonymous.devapp@gmail.com";
		final String password = "motdepasse";
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		Session sessionmail = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		try {
			Message message = new MimeMessage(sessionmail);
			message.setFrom(new InternetAddress("anonymous.devapp@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailto));
			message.setSubject("[TECHSIS Newsletter] " + news.getTitle());
			message.setText(news.getContent());
			Transport.send(message);
			System.out.println("Newsletter sent at " + emailto);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void sendNews(HttpSession session, Newsletter news) {
		Object u = session.getAttribute("user");
		HashMap user = (HashMap) u;
		Map<Long, User> test = (Map<Long, User>) u;
		user.putAll(test);
		Iterator i = test.entrySet().iterator();
		while (i.hasNext()) {
			Entry thisEntry = (Entry) i.next();
			User value = (User) thisEntry.getValue();
			if (value.getNews() == 1) {
				sendNews(value.getEmail(), news);
			}
		}
	}

	public static void sendWelcome(User user) {
		final String username = "anonymous.devapp@gmail.com";
		final String password = "awzxecrv";
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		Session sessionmail = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		try {
			Message message = new MimeMessage(sessionmail);
			message.setFrom(new InternetAddress("anonymous.devapp@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
			message.setSubject("[TECHSIS Newsletter] Bienvenue " + user.getFirstName() + " " + user.getLastName());
			message.setText("Bienvenue " + user.getFirstName() + " " + user.getLastName()
					+ ",\r\n Merci de votre inscription à la Newsletter Techsis.\r\nVous recevrez très prochainement les nouvelles news par mail.\r\n\r\nBonne journée.\r\nL'équipe TECHSIS");
			Transport.send(message);
			System.out.println("Welcome mail sent at " + user.getEmail());
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
