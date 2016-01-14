package com.jweb.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.jweb.beans.Mail;
import com.jweb.beans.Newsletter;
import com.jweb.dao.NewsletterDao;
import com.jweb.dao.DAOFactory;
import com.jweb.forms.CreateNewsForm;

public class createNews extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String CONF_DAO_FACTORY = "daofactory";
	public static final String ATT_NEWS = "news";
	public static final String ATT_FORM = "form";

	public static final String VUE_SUCCES = "/WEB-INF/news.jsp";
	public static final String VUE_FORM = "/WEB-INF/createNews.jsp";

	private NewsletterDao newsDao;

	public void init() throws ServletException {
		this.newsDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getNewsletterDao();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);
	}

	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CreateNewsForm form = new CreateNewsForm(newsDao);
		Newsletter news = form.createNews(request);

		request.setAttribute(ATT_NEWS, news);
		request.setAttribute(ATT_FORM, form);

		if (form.getErrors() == 0) {
			HttpSession session = request.getSession();
			Map<Long, Newsletter> news_map = (HashMap<Long, Newsletter>) session.getAttribute(ATT_NEWS);
			if (news_map == null) {
				news_map = new HashMap<Long, Newsletter>();
			}
			news_map.put(news.getId(), news);
			session.setAttribute(ATT_NEWS, news_map);

			Mail.sendNews(session, news);
			
			this.getServletContext().getRequestDispatcher(VUE_SUCCES).forward(request, response);
		} else {
			this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);
		}
	}
}
