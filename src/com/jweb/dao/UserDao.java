package com.jweb.dao;

import java.sql.SQLException;
import java.util.List;

import com.jweb.beans.User;

public interface UserDao {

	void create(User user) throws SQLException;

	void erase(User user) throws SQLException;

    List<User> listUser();
}
