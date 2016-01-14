package com.jweb.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class DAOUtility {

	private DAOUtility() {
	}

	public static void close(ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				System.out.println("failed close resulset : " + e.getMessage());
			}
		}
	}

	public static void close(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				System.out.println("failed close statement : " + e.getMessage());
			}
		}
	}

	public static void close(Connection connexion) {
		if (connexion != null) {
			try {
				connexion.close();
			} catch (SQLException e) {
				System.out.println("failed close connexion : " + e.getMessage());
			}
		}
	}

	public static void close(Statement statement, Connection connexion) {
		close(statement);
		close(connexion);
	}

	public static void close(ResultSet resultSet, Statement statement, Connection connexion) {
		close(resultSet);
		close(statement);
		close(connexion);
	}
}