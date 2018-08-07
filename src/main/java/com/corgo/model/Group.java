package com.corgo.model;

import java.util.List;

import org.springframework.data.annotation.Id;

public class Group {
	@Id
	private String id;
	
	private String title;
	private String description;
	private List<UserStub> users;
	private List<PostStub> posts;
	private List<String> invited;
	
	@Override
	public String toString() {
		return "Group [id=" + id + ", title=" + title + ", posts=" + posts + ", users=" + users + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public List<String> getInvited() {
		return invited;
	}

	public void setInvited(List<String> invited) {
		this.invited = invited;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<UserStub> getUsers() {
		return users;
	}

	public void setUsers(List<UserStub> users) {
		this.users = users;
	}

	public List<PostStub> getPosts() {
		return posts;
	}

	public void setPosts(List<PostStub> posts) {
		this.posts = posts;
	}
	
	
}
