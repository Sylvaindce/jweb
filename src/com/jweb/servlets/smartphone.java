package com.jweb.servlets;

import javax.servlet.http.*;

import com.jweb.beans.Review;
import com.jweb.dao.DAOFactory;
import com.jweb.dao.ReviewDao;
import com.jweb.forms.CreateReviewForm;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.*;

public class smartphone extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String VUE = "/WEB-INF/smartphone.jsp";
	public static final String CONF_DAO_FACTORY = "daofactory";
	public static final String ATT_NEWS = "review";
	public static final String ATT_FORM = "form";

	private ReviewDao reviewDao;

	public void init() throws ServletException {
		this.reviewDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getReviewDao();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CreateReviewForm form = new CreateReviewForm(reviewDao);
		Review news = form.createReview(request);

		request.setAttribute(ATT_NEWS, news);
		request.setAttribute(ATT_FORM, form);

		if (form.getErrors() == 0) {
			HttpSession session = request.getSession();
			Map<Long, Review> news_map = (HashMap<Long, Review>) session.getAttribute(ATT_NEWS);
			if (news_map == null) {
				news_map = new HashMap<Long, Review>();
			}
			news_map.put(news.getId(), news);
			session.setAttribute(ATT_NEWS, news_map);
			this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
		} else {
			this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
		}
	}
}
