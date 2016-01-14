package com.jweb.config;

import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.jweb.dao.DAOFactory;

public class InitDAOFactory implements ServletContextListener {
	private static final String ATT_DAO_FACTORY = "daofactory";

	private DAOFactory daoFactory;

	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext servletContext = event.getServletContext();
		try {
			this.daoFactory = DAOFactory.getIntance();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		servletContext.setAttribute(ATT_DAO_FACTORY, this.daoFactory);
		System.out.println("init facto done");
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
	}
}
