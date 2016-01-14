package com.jweb.servlets;

import javax.servlet.http.*;
import java.io.IOException;
import javax.servlet.*;

public class listUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String VUE = "/WEB-INF/listUsers.jsp";

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}
}
