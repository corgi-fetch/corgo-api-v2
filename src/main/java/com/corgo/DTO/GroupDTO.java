package com.corgo.DTO;

import java.util.List;

import org.springframework.data.annotation.Id;

import com.corgo.model.Post;
import com.corgo.model.User;

public class GroupDTO {
	@Id
	private String id;
	
	private String name;
	private List<User> users;
	private List<Post> posts;
	
	private String description;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	

}
