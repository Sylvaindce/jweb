package com.jweb.forms;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

//import org.joda.time.DateTime;

import com.jweb.beans.User;
import com.jweb.dao.UserDao;

public final class CreateUserForm {
	private static final String FIRSTNAME_SECTION = "firstName";
	private static final String LASTNAME_SECTION = "lastName";
	private static final String EMAIL_SECTION = "email";
	private static final String NEWS_SECTION = "news";

	private String result;
	private int error;
	private UserDao userDao;

	public CreateUserForm(UserDao userDao) {
		this.userDao = userDao;
	}

	public String getResult() {
		return result;
	}

	public User createUser(HttpServletRequest request) {
		String firstName = getValueByName(request, FIRSTNAME_SECTION);
		String lastName = getValueByName(request, LASTNAME_SECTION);
		String email = getValueByName(request, EMAIL_SECTION);
		Boolean news;

		User user = new User();

		if (request.getParameter(NEWS_SECTION) == null) {
			news = false;
		} else {
			news = true;
		}

		checkFirst(firstName, user);
		checkLast(lastName, user);
		checkEmail(email, user);
		user.setNews(news);

		if (error == 0) {
			try {
				userDao.create(user);
				result = "User created with success.";
			} catch (SQLException e) {
				System.out.println("Error creating UserDao" + e.getMessage());
			}
		} else {
			result = "Error creating User";
		}
		return user;
	}

	private void checkFirst(String str, User user) {
		if (str != null) {
			if (str.length() > 2) {
				user.setFirstName(str);
				System.out.println("Set firstname");
			} else {
				error += 1;
				System.out.println("Please enter firstname with at least 3 letters");
			}
		} else {
			error += 1;
			System.out.println("Please complete firstname Section");
		}
	}

	private void checkLast(String str, User user) {
		if (str != null) {
			if (str.length() > 2) {
				user.setLastName(str);
				System.out.println("Set lastname");
			} else {
				error += 1;
				System.out.println("Please enter lastname with at least 3 letters");
			}
		} else {
			error += 1;
			System.out.println("Please complete lastname section");
		}
	}

	private void checkEmail(String str, User user) {
		String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

		if (str != null) {
			if (str.matches(EMAIL_REGEX) == true) {
				user.setEmail(str);
				System.out.println("Set email");
			} else {
				error += 1;
				System.out.println("Please enter email with valid format");
			}
		} else {
			error += 1;
			System.out.println("Please complete email section");
		}
	}

	public int getErrors() {
		return this.error;
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
