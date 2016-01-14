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

import com.jweb.dao.UserDao;
import com.jweb.beans.User;
import com.jweb.dao.DAOFactory;

public class delUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String CONF_DAO_FACTORY = "daofactory";
	public static final String PARAM_ID = "idUser";
	public static final String SESSION = "user";

	public static final String VUE = "/listUsers";

	private UserDao userDao;

	public void init() throws ServletException {
		this.userDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getUserDao();
	}

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idUser = getValueByName(request, PARAM_ID);
		Long id = Long.parseLong(idUser);
		HttpSession session = request.getSession();
		Map<Long, User> user = (HashMap<Long, User>) session.getAttribute(SESSION);
		if (id != null && user != null) {
			try {
				userDao.erase(user.get(id));
				user.remove(id);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			session.setAttribute(SESSION, user);
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
