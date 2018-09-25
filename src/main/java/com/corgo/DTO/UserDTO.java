package com.corgo.DTO;

import java.util.List;

import com.corgo.model.Group;

public class UserDTO {
	private String id;
	private String userId;
	
    private int rating;
    private String name;
    private String email;
    
    private List<PostStubDTO> postHistory;
    
    private List<PostStubDTO> currentPosts;
    
    private List<PostStubDTO> currentJobs;
    private String creditCardNumber;
    private String bankAccount;
    
    private List<GroupStubDTO> groups;
    
    private String pushToken;
    
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
    
    public void setGroups(List<GroupStubDTO> _groups) {
    	groups = _groups;
    }

    public List<GroupStubDTO> getGroups() {
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
	public List<PostStubDTO> getPostHistory() {
		return postHistory;
	}
	public void setPostHistory(List<PostStubDTO> postHistory) {
		this.postHistory = postHistory;
	}
	public List<PostStubDTO> getCurrentPosts() {
		return currentPosts;
	}
	public void setCurrentPosts(List<PostStubDTO> currentPosts) {
		this.currentPosts = currentPosts;
	}
	public List<PostStubDTO> getCurrentJobs() {
		return currentJobs;
	}
	public void setCurrentJobs(List<PostStubDTO> currentJobs) {
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
	
	public String getPushToken() {
		return this.pushToken;
	}
	
	public void setPushToken(String pushToken) {
		this.pushToken = pushToken;
	}
    
    
}