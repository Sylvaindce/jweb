package com.jweb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jweb.beans.Review;

public class ReviewDaoMpl implements ReviewDao {

	private DAOFactory daoFactory;
	
	public ReviewDaoMpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public void create(Review review) throws SQLException {
		Connection connexion = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		ResultSet generatedId = null;
		String SQL_INSERT = "INSERT INTO reviews (author, content, note, date) VALUES (\"" + review.getAuthor()
				+ "\", \"" + review.getContent() + "\", " + review.getNote() + ", NOW())";

		try {
			connexion = daoFactory.getConnection();
			statement = connexion.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
			int status = statement.executeUpdate();

			if (status == 0) {
				throw new SQLException("Cannot add the user to the DB");
			}
			generatedId = statement.getGeneratedKeys();
			if (generatedId.next()) {
				review.setId(generatedId.getLong(1));
			} else {
				throw new SQLException("No auto generated ID found");
			}
		} catch (SQLException e) {
			throw new SQLException(e);
		} finally {
			DAOUtility.close(resultSet, statement, connexion);
		}
	}

	@Override
	public List<Review> listReview() {
		Connection connexion = null;
		Statement statement = null;
		ResultSet resultSet = null;
		List<Review> review = new ArrayList<Review>();
		try {
			connexion = daoFactory.getConnection();
			statement = connexion.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM reviews ORDER BY id");
			while (resultSet.next()) {
				review.add(map(resultSet));
			}
		} catch (SQLException e) {
			System.out.println("Error in listReview() " + e.getMessage());
		} finally {
			DAOUtility.close(resultSet, statement, connexion);
		}
		return review;
	}

	@Override
	public void erase(Review review) throws SQLException {
		Connection connexion = null;
		Statement statement = null;
		ResultSet resultSet = null;
		String SQL_DELETE = "DELETE FROM reviews WHERE id = " + review.getId() + ";";

		try {
			connexion = daoFactory.getConnection();
			statement = connexion.createStatement();
			int status = statement.executeUpdate(SQL_DELETE);

			if (status == 0) {
				throw new SQLException("Cannot delete the user");
			}
		} catch (SQLException e) {
			throw new SQLException(e);
		} finally {
			DAOUtility.close(resultSet, statement, connexion);
		}
	}

	private static Review map(ResultSet resultSet) throws SQLException {
		Review review = new Review();
		review.setId(resultSet.getLong("id"));
		review.setAuthor(resultSet.getString("author"));
		review.setContent(resultSet.getString("content"));
		review.setNote(resultSet.getInt("note"));
		return review;
	}
}
