package com.corgo.DTO;

import java.util.List;

import com.corgo.model.UserStub;

public class GroupStubDTO {
	private String id;
	
	private String title;
	private String description;
	
	private List<UserStubDTO> users;
	
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

	public List<UserStubDTO> getUsers() {
		return users;
	}

	public void setUsers(List<UserStubDTO> users) {
		this.users = users;
	}
}
