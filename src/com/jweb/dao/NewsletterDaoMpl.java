package com.jweb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jweb.beans.Newsletter;

public class NewsletterDaoMpl implements NewsletterDao {

	private DAOFactory daoFactory;

	public NewsletterDaoMpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public void create(Newsletter newsletter) throws SQLException {
		Connection connexion = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		ResultSet generatedId = null;
		String SQL_INSERT = "INSERT INTO news (title, content, date) VALUES (\"" + newsletter.getTitle() + "\", \""
				+ newsletter.getContent() + "\", NOW());";

		try {
			connexion = daoFactory.getConnection();
			statement = connexion.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
			int status = statement.executeUpdate();

			if (status == 0) {
				throw new SQLException("Cannot add the user to the DB");
			}
			generatedId = statement.getGeneratedKeys();
			if (generatedId.next()) {
				newsletter.setId(generatedId.getLong(1));
			} else {
				throw new SQLException("No auto generated ID found");
			}
		} catch (SQLException e) {
			System.out.println("error sql create" + e.getMessage());
			throw new SQLException(e);
		} finally {
			DAOUtility.close(resultSet, statement, connexion);
		}
	}

	@Override
	public List<Newsletter> listNews() {
		Connection connexion = null;
		Statement statement = null;
		ResultSet resultSet = null;
		List<Newsletter> news = new ArrayList<Newsletter>();

		try {
			connexion = daoFactory.getConnection();
			statement = connexion.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM news ORDER BY date;");
			while (resultSet.next()) {
				news.add(map(resultSet));
			}
		} catch (SQLException e) {
			System.out.println("error listNews" + e.getMessage());
		} finally {
			DAOUtility.close(resultSet, statement, connexion);
		}
		return news;
	}

	@Override
	public void erase(Newsletter newsletter) throws SQLException {
		Connection connexion = null;
		Statement statement = null;
		ResultSet resultSet = null;
		String SQL_DELETE = "DELETE FROM news WHERE id = " + newsletter.getId() + ";";

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

	private static Newsletter map(ResultSet resultSet) throws SQLException {
		Newsletter news = new Newsletter();
		news.setId(resultSet.getLong("id"));
		news.setTitle(resultSet.getString("title"));
		news.setContent(resultSet.getString("content"));
		return news;
	}
}
