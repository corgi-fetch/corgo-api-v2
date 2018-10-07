package com.corgo.DTO;

import java.util.List;

public class PostDTO {
	
	private String id;
	
	private int date;
	private UserStubDTO owner;
	private String title;
	private String description;
	private double payment;
	
	private String groupId;
	
	private List<UserStubDTO> interestedQueue;
	private boolean serviceGiven;
	private boolean serviceReceived;
	
	private int state;
	public UserStubDTO selectedUserId;
	public UserStubDTO responderUserId;
	
	public int getState() {
		return state;
	}
	
	public void setState(int state) {
		this.state = state;
	}
	
	public String getGroupId() {
		return groupId;
	}
	
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getDate() {
		return date;
	}
	public void setDate(int date) {
		this.date = date;
	}
	public UserStubDTO getOwner() {
		return owner;
	}
	public void setOwner(UserStubDTO owner) {
		this.owner = owner;
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
	public double getPayment() {
		return payment;
	}
	public void setPayment(double payment) {
		this.payment = payment;
	}
	public List<UserStubDTO> getInterestedQueue() {
		return interestedQueue;
	}
	public void setInterestedQueue(List<UserStubDTO> interestedQueue) {
		this.interestedQueue = interestedQueue;
	}
	public boolean isServiceGiven() {
		return serviceGiven;
	}
	public void setServiceGiven(boolean serviceGiven) {
		this.serviceGiven = serviceGiven;
	}
	public boolean isServiceReceived() {
		return serviceReceived;
	}
	public void setServiceReceived(boolean serviceReceived) {
		this.serviceReceived = serviceReceived;
	}
	
	public void setSelectedUserId(UserStubDTO selectedUserId) {
		this.selectedUserId = selectedUserId;
	}
	
	public UserStubDTO getSelectedUserId() {
		return selectedUserId;
	}
	
	public void setResponderUserId(UserStubDTO responderUserId) {
		this.responderUserId = responderUserId;
	}
	
	public UserStubDTO getResponderUserId() {
		return responderUserId;
	}
}
