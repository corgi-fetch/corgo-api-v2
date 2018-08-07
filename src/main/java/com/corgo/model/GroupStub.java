package com.corgo.model;

import java.util.List;

public class GroupStub {
	private String id;
	
	private String title;
	private String description;
	
	private List<UserStub> users;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<UserStub> getUsers() {
		return users;
	}

	public void setUsers(List<UserStub> users) {
		this.users = users;
	}

	
}
