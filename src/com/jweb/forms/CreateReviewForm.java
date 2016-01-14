package com.jweb.forms;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

//import org.joda.time.DateTime;

import com.jweb.beans.Review;
import com.jweb.dao.ReviewDao;

public final class CreateReviewForm {
	private static final String AUTHOR_SECTION = "author";
	private static final String CONTENT_SECTION = "content";
	private static final String NOTE_SECTION = "note";
	
	private String result;
	private int error;
	private ReviewDao reviewDao;

	public CreateReviewForm(ReviewDao reviewDao) {
		this.reviewDao = reviewDao;
	}

	public String getResult() {
		return result;
	}

	public Review createReview(HttpServletRequest request) {
		String author = getValueByName(request, AUTHOR_SECTION);
		String content = getValueByName(request, CONTENT_SECTION);
		int note;
		
		try {
			note = Integer.parseInt(getValueByName(request, NOTE_SECTION));
		} catch(NumberFormatException e) {
			note = 42;
		}
		
		Review reviews = new Review();

		checkAuthor(author, reviews);
		checkContent(content, reviews);
		checkNote(note, reviews);

		if (error == 0) {
			try {
				reviewDao.create(reviews);
				result = "Review created with success.";
			} catch (SQLException e) {
				System.out.println("Error while creating reviewDao" + e.getMessage());
			}
		} else {
			result = "Failed";
		}
		return reviews;
	}

	private void checkAuthor(String author, Review news) {
		if (author != null) {
			if (author.length() > 2) {
				news.setAuthor(author);
				System.out.println("Set author");
			} else {
				error += 1;
				System.out.println("Please enter author with at least 3 letters");
			}
		} else {
			error += 1;
			System.out.println("Please complete author section.");
		}
	}

	private void checkContent(String content, Review news) {
		if (content != null) {
			if (content.length() > 3) {
				news.setContent(content);
				System.out.println("Set Content");
			} else {
				error += 1;
				System.out.println("Please enter content with at least 3 letters");
			}
		} else {
			error += 1;
			System.out.println("Please complete content section");
		}
	}
	
	private void checkNote(int note, Review news) {
		System.out.println(note);
		if (note >= 0) {
			if (note <= 5) {
				news.setNote(note);
				System.out.println("Set Note");
			} else {
				error += 1;
				System.out.println("Please enter number between 0 and 5");
			}
		} else {
			error += 1;
			System.out.println("Please enter number");
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
