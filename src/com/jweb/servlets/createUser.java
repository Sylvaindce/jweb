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
import com.jweb.beans.User;
import com.jweb.dao.UserDao;
import com.jweb.dao.DAOFactory;
import com.jweb.forms.CreateUserForm;

public class createUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String CONF_DAO_FACTORY = "daofactory";
	public static final String ATT_NEWS = "user";
	public static final String ATT_FORM = "form";

	public static final String VUE_SUCCES = "/WEB-INF/listUsers.jsp";
	public static final String VUE_FORM = "/WEB-INF/createUser.jsp";

	private UserDao userDao;

	public void init() throws ServletException {
		this.userDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getUserDao();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);
	}

	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CreateUserForm form = new CreateUserForm(userDao);
		User user = form.createUser(request);

		request.setAttribute(ATT_NEWS, user);
		request.setAttribute(ATT_FORM, form);

		if (form.getErrors() == 0) {
			HttpSession session = request.getSession();
			Map<Long, User> user_map = (HashMap<Long, User>) session.getAttribute(ATT_NEWS);
			System.out.println(user_map);
			if (user_map == null) {
				user_map = new HashMap<Long, User>();
			}
			user_map.put(user.getId(), user);
			session.setAttribute(ATT_NEWS, user_map);
			Mail.sendWelcome(user);
			this.getServletContext().getRequestDispatcher(VUE_SUCCES).forward(request, response);
		} else {
			this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);
		}
	}
}
