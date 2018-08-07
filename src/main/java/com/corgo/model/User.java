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
    
    private List<PostStub> postHistory;
    private List<PostStub> currentPosts;
    private List<PostStub> currentJobs;
    
    private String creditCardNumber;
    private String bankAccount;
    
    private List<GroupStub> groups;

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
        
    public void setGroups(List<GroupStub> _groups) {
    	groups = _groups;
    }

    public List<GroupStub> getGroups() {
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

    public void setPostHistory(List<PostStub> _postHistory) {
        postHistory = _postHistory;
    }

    public List<PostStub> getPostHistory() {
        return postHistory;
    }

    public void setCurrentPost(List<PostStub> _currentPosts) {
        currentPosts = _currentPosts;
    }

    public List<PostStub> getCurrentPost() {
        return currentPosts;
    }

    public void setCurrentJobs(List<PostStub> _currentJobs) {
        currentJobs = _currentJobs;
    }

    public List<PostStub> getCurrentJobs() {
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
