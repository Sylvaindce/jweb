package com.jweb.dao;

import java.sql.SQLException;
import java.util.List;

import com.jweb.beans.Review;

public interface ReviewDao {

	void create(Review review) throws SQLException;

	void erase(Review review) throws SQLException;

	List<Review> listReview();
}
