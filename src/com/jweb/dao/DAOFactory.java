package com.jweb.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DAOFactory {

	private static final String PROPERTIES_FILE = "/com/jweb/dao/dao.properties";
	private static final String PROPERTY_URL = "url";
	private static final String PROPERTY_DRIVER = "driver";
	private static final String PROPERTY_USERNAME = "username";
	private static final String PROPERTY_PWD = "password";

	private String url;
	private String username;
	private String password;

	DAOFactory(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	public static DAOFactory getIntance() throws SQLException {
		Properties properties = new Properties();
		String url;
		String driver;
		String username;
		String password;

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream propertiesFile = classLoader.getResourceAsStream(PROPERTIES_FILE);

		if (propertiesFile == null) {
			throw new SQLException("File " + PROPERTIES_FILE + "not found.");
		}

		try {
			properties.load(propertiesFile);
			url = properties.getProperty(PROPERTY_URL);
			driver = properties.getProperty(PROPERTY_DRIVER);
			username = properties.getProperty(PROPERTY_USERNAME);
			password = properties.getProperty(PROPERTY_PWD);
		} catch (IOException e) {
			throw new SQLException("Can't load properties file " + PROPERTIES_FILE, e);
		}

		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			throw new SQLException("The driver can't be found in the classpath", e);
		}

		DAOFactory instance = new DAOFactory(url, username, password);
		return instance;
	}

	Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}

	public NewsletterDao getNewsletterDao() {
		return new NewsletterDaoMpl(this);
	}

	public UserDao getUserDao() {
		return new UserDaoMpl(this);
	}

	public ReviewDao getReviewDao() {
		return new ReviewDaoMpl(this);
	}

}
