package com.jweb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jweb.beans.User;

public class UserDaoMpl implements UserDao {
	private DAOFactory daoFactory;

	public UserDaoMpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public void create(User User) throws IllegalArgumentException, SQLException {
		Connection connexion = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		ResultSet generatedId = null;
		String SQL_INSERT = "INSERT INTO users (date, firstName, lastName, email, news) VALUES (NOW(), \""
				+ User.getFirstName() + "\", \"" + User.getLastName() + "\", \"" + User.getEmail() + "\", \""
				+ User.getNews() + "\");";

		try {
			connexion = daoFactory.getConnection();

			statement = connexion.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
			int status = statement.executeUpdate();

			if (status == 0) {
				throw new SQLException("Cannot add the user to the DB");
			}
			generatedId = statement.getGeneratedKeys();
			if (generatedId.next()) {
				User.setId(generatedId.getLong(1));
			} else {
				throw new SQLException("No auto generated ID found");
			}
		} catch (SQLException e) {
			throw new SQLException(e);
		} finally {
			DAOUtility.close(resultSet, statement, connexion);
		}
	}

	public List<User> listUser() {
		Connection connexion = null;
		Statement statement = null;
		ResultSet resultSet = null;
		List<User> user = new ArrayList<User>();

		try {
			connexion = daoFactory.getConnection();
			statement = connexion.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM users ORDER BY id");
			while (resultSet.next()) {
				System.out.println(resultSet);
				user.add(map(resultSet));
			}
		} catch (SQLException e) {
			System.out.println("Error on listUser() " + e.getMessage());
		} finally {
			DAOUtility.close(resultSet, statement, connexion);
		}
		return user;
	}

	@Override
	public void erase(User user) throws SQLException {
		Connection connexion = null;
		Statement statement = null;
		ResultSet resultSet = null;
		String SQL_DELETE = "DELETE FROM users WHERE id = " + user.getId() + ";";

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

	private static User map(ResultSet resultSet) throws SQLException {
		User user = new User();
		user.setId(resultSet.getLong("id"));
		user.setFirstName(resultSet.getString("firstName"));
		user.setLastName(resultSet.getString("lastName"));
		user.setEmail(resultSet.getString("email"));
		user.setNews(resultSet.getBoolean("news"));
		return user;
	}
}
