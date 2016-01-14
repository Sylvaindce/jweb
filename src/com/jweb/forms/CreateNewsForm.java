package com.jweb.forms;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

//import org.joda.time.DateTime;

import com.jweb.beans.Newsletter;
import com.jweb.dao.NewsletterDao;

public final class CreateNewsForm {
	private static final String TITLE_SECTION = "title";
	private static final String CONTENT_SECTION = "content";

	private String result;
	private int error;
	private NewsletterDao newsDao;

	public CreateNewsForm(NewsletterDao newsDao) {
		this.newsDao = newsDao;
	}

	public String getResult() {
		return result;
	}

	public Newsletter createNews(HttpServletRequest request) {
		String title = getValueByName(request, TITLE_SECTION);
		String content = getValueByName(request, CONTENT_SECTION);

		Newsletter news = new Newsletter();

		checkTitle(title, news);
		checkContent(content, news);

		if (error == 0) {
			try {
				newsDao.create(news);
				result = "Success creating the newsletter";
			} catch (SQLException e) {
				System.out.println("Error creating newsDao" + e.getMessage());
			}
		} else {
			result = "Failed while creating News";
		}
		return news;
	}

	private void checkTitle(String title, Newsletter news) {
		if (title != null) {
			if (title.length() > 2) {
				news.setTitle(title);
				System.out.println("Set title");
			} else {
				error += 1;
				System.out.println("Please enter title with at least 3 letters");
			}
		} else {
			error += 1;
			System.out.println("Please complet title section");
		}
	}

	private void checkContent(String content, Newsletter news) {
		if (content != null) {
			if (content.length() > 10) {
				news.setContent(content);
				System.out.println("Set Content");
			} else {
				error += 1;
				System.out.println("Please enter content with at least 10 letters");
			}
		} else {
			error += 1;
			System.out.println("Please complet content section");
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
