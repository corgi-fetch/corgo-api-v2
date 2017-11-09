package com.corgo.DTO;

import java.util.List;

import com.corgo.model.Group;

public class UserDTO {
	private String id;
	private String userId;
	
    private int rating;
    private String name;
    private String email;
    
    private List<PostDTO> postHistory;
    
    private List<PostDTO> currentPosts;
    
    private List<PostDTO> currentJobs;
    private String creditCardNumber;
    private String bankAccount;
    
    private List<GroupDTO> groups;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getUserId() {
    	return userId;
    }
    
    public void setUserId(String _userId) {
    	this.userId = _userId;
    }
    
    public void setGroups(List<GroupDTO> _groups) {
    	groups = _groups;
    }

    public List<GroupDTO> getGroups() {
    	return groups;
    }
	
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<PostDTO> getPostHistory() {
		return postHistory;
	}
	public void setPostHistory(List<PostDTO> postHistory) {
		this.postHistory = postHistory;
	}
	public List<PostDTO> getCurrentPosts() {
		return currentPosts;
	}
	public void setCurrentPosts(List<PostDTO> currentPosts) {
		this.currentPosts = currentPosts;
	}
	public List<PostDTO> getCurrentJobs() {
		return currentJobs;
	}
	public void setCurrentJobs(List<PostDTO> currentJobs) {
		this.currentJobs = currentJobs;
	}
	public String getCreditCardNumber() {
		return creditCardNumber;
	}
	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
    
    
}