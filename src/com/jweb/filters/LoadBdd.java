package com.jweb.filters;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jweb.beans.Newsletter;
import com.jweb.beans.Review;
import com.jweb.beans.User;
import com.jweb.dao.UserDao;
import com.jweb.dao.DAOFactory;
import com.jweb.dao.NewsletterDao;
import com.jweb.dao.ReviewDao;

public class LoadBdd implements Filter {
	public static final String CONF_DAO_FACTORY = "daofactory";
	public static final String ATT_SESSION_CLIENTS = "user";
	public static final String ATT_SESSION_NEWS = "news";
	public static final String ATT_SESSION_REVIEWS = "review";

	private UserDao clientDao;
	private NewsletterDao newsDao;
	private ReviewDao reviewDao;

	public void init(FilterConfig config) throws ServletException {
		this.clientDao = ((DAOFactory) config.getServletContext().getAttribute(CONF_DAO_FACTORY)).getUserDao();
		this.newsDao = ((DAOFactory) config.getServletContext().getAttribute(CONF_DAO_FACTORY)).getNewsletterDao();
		this.reviewDao = ((DAOFactory) config.getServletContext().getAttribute(CONF_DAO_FACTORY)).getReviewDao();
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpSession session = request.getSession();
		if (session.getAttribute(ATT_SESSION_CLIENTS) == null) {
			List<User> listUser = clientDao.listUser();
			Map<Long, User> mapUser = new HashMap<Long, User>();
			for (User user : listUser) {
				mapUser.put(user.getId(), user);
			}
			session.setAttribute(ATT_SESSION_CLIENTS, mapUser);
		}

		if (session.getAttribute(ATT_SESSION_NEWS) == null) {
			List<Newsletter> listNews = newsDao.listNews();
			Map<Long, Newsletter> mapNews = new HashMap<Long, Newsletter>();
			for (Newsletter news : listNews) {
				mapNews.put(news.getId(), news);
			}
			session.setAttribute(ATT_SESSION_NEWS, mapNews);
		}

		if (session.getAttribute(ATT_SESSION_REVIEWS) == null) {
			List<Review> listReviews = reviewDao.listReview();
			Map<Long, Review> mapReviews = new HashMap<Long, Review>();
			for (Review reviews : listReviews) {
				mapReviews.put(reviews.getId(), reviews);
			}
			session.setAttribute(ATT_SESSION_REVIEWS, mapReviews);
		}
		chain.doFilter(request, res);
	}

	public void destroy() {
	}
}