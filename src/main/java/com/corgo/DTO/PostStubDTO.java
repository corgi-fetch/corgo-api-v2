package com.corgo.DTO;

public class PostStubDTO {
	private String id;
	
	private int date;
	private UserStubDTO owner;
	private String title;
	private String description;
	private double payment;
	
	private String groupId;
	
	private int state;

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

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

}
