package com.jweb.beans;

public class User {
	private long id;
	private String firstName;
	private String lastName;
	private String email;
	private int news;

	public User() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String first) {
		this.firstName = first;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String last) {
		this.lastName = last;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setNews(Boolean news) {
		this.news = news ? 1 : 0;
	}

	public int getNews() {
		return this.news;
	}
}
