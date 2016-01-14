package com.jweb.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jweb.dao.NewsletterDao;
import com.jweb.beans.Newsletter;
import com.jweb.dao.DAOFactory;

public class delNews extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String CONF_DAO_FACTORY = "daofactory";
	public static final String PARAM_ID_NEWS = "idNews";
	public static final String SESSION_NEWS = "news";

	public static final String VUE = "/newsAdmin";

	private NewsletterDao newsletterDao;

	public void init() throws ServletException {
		this.newsletterDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getNewsletterDao();
	}

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idNews = getValueByName(request, PARAM_ID_NEWS);
		Long id = Long.parseLong(idNews);
		HttpSession session = request.getSession();
		Map<Long, Newsletter> news = (HashMap<Long, Newsletter>) session.getAttribute(SESSION_NEWS);
		if (id != null && news != null) {
			try {
				newsletterDao.erase(news.get(id));
				news.remove(id);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			session.setAttribute(SESSION_NEWS, news);
		}
		response.sendRedirect(request.getContextPath() + VUE);
	}

	private static String getValueByName(HttpServletRequest request, String name) {
		String value = request.getParameter(name);
		if (value == null || value.trim().length() == 0) {
			return null;
		} else {
			return value;
		}
	}
}
