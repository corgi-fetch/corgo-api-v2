package com.corgo.model;

import java.util.List;

import org.springframework.data.annotation.Id;

public class Group {
	@Id
	private String id;
	
	private String name;
	private String description;
	private List<User> users;
	private List<Post> posts;
	private List<String> invited;
	
	@Override
	public String toString() {
		return "Group [id=" + id + ", name=" + name + ", posts=" + posts + ", users=" + users + "]";
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	
	
}
