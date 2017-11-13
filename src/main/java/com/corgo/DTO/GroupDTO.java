package com.corgo.DTO;

import java.util.List;

import org.springframework.data.annotation.Id;

import com.corgo.model.Post;
import com.corgo.model.User;

public class GroupDTO {
	@Id
	private String id;
	
	private String name;
	private List<UserDTO> users;
	private List<PostDTO> posts;
	List<String> invited;
	
	private String description;
	
	public String getId() {
		return id;
	}
	
	public List<String> getInvited() {
		return invited;
	}

	public void setInvited(List<String> invited) {
		this.invited = invited;
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
	public List<UserDTO> getUsers() {
		return users;
	}
	public void setUsers(List<UserDTO> users) {
		this.users = users;
	}
	public List<PostDTO> getPosts() {
		return posts;
	}
	public void setPosts(List<PostDTO> posts) {
		this.posts = posts;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	

}
