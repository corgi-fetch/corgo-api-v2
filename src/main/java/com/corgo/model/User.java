package com.corgo.model;

import java.util.List;

import org.springframework.data.annotation.Id;

public class User {
	
	@Id
	private String id;
    private int rating;
    private String name;
    private String email;
    
    private String userId;
    
    private List<Post> postHistory;
    private List<Post> currentPosts;
    private List<Post> currentJobs;
    
    private String creditCardNumber;
    private String bankAccount;
    
    private List<Group> groups;

    public User() {

    }
    
    public void update(User user) {
    	this.userId = user.userId;
    	
    	this.rating = user.rating;
    	this.name = user.name;
    	this.email = user.email;
    	
    	this.postHistory = user.postHistory;
    	this.currentPosts = user.currentPosts;
    	this.currentJobs = user.currentJobs;
    	
    	this.creditCardNumber = user.creditCardNumber;
    	this.bankAccount = user.bankAccount;
    }
    
    public String getUserId() {
    	return userId;
    }
    
    public void setUserId(String _userId) {
    	userId = _userId;
    }
    
    public String getId() {
    	return id;
    }
        
    public void setGroups(List<Group> _groups) {
    	groups = _groups;
    }

    public List<Group> getGroups() {
    	return groups;
    }

    public void setRating(int _rating) {
        rating = _rating;
    }

    public int getRating() {
        return rating;
    }

    public void setName(String _name) {
        name = _name;
    }
    
    public String getName() {
        return name;
    }

    public void setEmail(String _email) {
        email = _email;
    }

    public String getEmail() {
        return email;
    }

    public void setPostHistory(List<Post> _postHistory) {
        postHistory = _postHistory;
    }

    public List<Post> getPostHistory() {
        return postHistory;
    }

    public void setCurrentPost(List<Post> _currentPosts) {
        currentPosts = _currentPosts;
    }

    public List<Post> getCurrentPost() {
        return currentPosts;
    }

    public void setCurrentJobs(List<Post> _currentJobs) {
        currentJobs = _currentJobs;
    }

    public List<Post> getCurrentJobs() {
        return currentJobs;
    }
    
    public void setCreditCardNumber(String _creditCardNumber) {
        creditCardNumber = _creditCardNumber;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setBankAccount(String _bankAccount) {
        bankAccount = _bankAccount;
    }

    public String getBankAccount() {
        return bankAccount;
    }
}
