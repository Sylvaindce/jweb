package com.jweb.dao;

import java.sql.SQLException;
import java.util.List;

import com.jweb.beans.Newsletter;

public interface NewsletterDao {

	void create(Newsletter newsletter) throws SQLException;

	void erase(Newsletter newsletter) throws SQLException;

	List<Newsletter> listNews();
}
