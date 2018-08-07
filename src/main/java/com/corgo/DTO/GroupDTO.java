package com.corgo.DTO;

import java.util.List;

import org.springframework.data.annotation.Id;

import com.corgo.model.Post;
import com.corgo.model.User;

public class GroupDTO {

	private String id;
	
	private String title;
	private List<UserStubDTO> users;
	private List<PostStubDTO> posts;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<UserStubDTO> getUsers() {
		return users;
	}
	public void setUsers(List<UserStubDTO> users) {
		this.users = users;
	}
	public List<PostStubDTO> getPosts() {
		return posts;
	}
	public void setPosts(List<PostStubDTO> posts) {
		this.posts = posts;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	

}
