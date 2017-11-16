package com.corgo.model;

import java.util.List;
import org.springframework.data.annotation.Id;

public class Post {
	@Id
	private String id;
	
	private int date;
	private User owner;
	private String title;
	private String description;
	private double payment;
	
	private String groupId;
	
	private List<User> interestedQueue;
	private boolean serviceGiven;
	private boolean serviceReceived;
	
    private String state;

	private Post(Builder builder) {
		this.date = builder.date;
		this.owner = builder.owner;
		this.title = builder.title;
		this.description = builder.description;
		this.payment = builder.payment;

		this.interestedQueue = builder.interestedQueue;
		this.serviceGiven = builder.serviceGiven;
		this.serviceReceived = builder.serviceReceived;
		
		//this.state = builder.state;
	}
	
	public Post() {}

	public static Builder getBuilder(int _date, User _owner, String _title, String _description, double _payment, String _groupId, String _state) {
		return new Builder(_date, _owner, _title, _description, _payment, _groupId, _state);
	}
	
	public String getId() {
		return this.id;
	}

	public int getDate() {
		return this.date;
	}

	public User getOwner() {
		return this.owner;
	}

	public String getTitle() {
		return this.title;
	}

	public String getDescription() {
		return this.description;
	}

	public double getPayment() {
		return this.payment;
	}

	public List<User> getInterestedQueue() {
		return this.interestedQueue;
	}

	public boolean getServiceGiven() {
		return this.serviceGiven;
	}

	public boolean getServiceReceived() {
		return this.serviceReceived;
	}
	
	public String getGroupId() {
		return this.groupId;
	}
	
	public String getState() {
		return this.state;
	}

	public void update(Builder builder) {
		this.date = builder.date;
		this.owner = builder.owner;
		this.title = builder.title;
		this.description = builder.description;
		this.payment = builder.payment;

		this.interestedQueue = builder.interestedQueue;
		this.serviceGiven = builder.serviceGiven;
		this.serviceReceived = builder.serviceReceived;
		
		this.groupId = builder.groupId;
		
		this.state = builder.state;
	}

	public static class Builder {
		
		private int date;
		private User owner;
		private String title;
		private String description;
		private double payment;
		
		private String groupId;
		
		private List<User> interestedQueue;
		private boolean serviceGiven = false;
		private boolean serviceReceived = false;
		
		private String state;

		public Builder(int date, User owner, String title, String description, double payment, String groupId, String state) {
			this.date = date;
			this.owner = owner;
			this.title = title;
			this.description = description;
			this.payment = payment;
			this.groupId = groupId;
			this.state = state;
		}

		public Builder interestedQueue(List<User> _interestedQueue) 
			{ this.interestedQueue = _interestedQueue;	return this; }
		public Builder serviceGiven(boolean _serviceGiven)
			{ this.serviceGiven = _serviceGiven; return this; }
		public Builder serviceReceived(boolean _serviceReceived)
			{ this.serviceReceived = _serviceReceived; return this; }

		public Post build() {
			return new Post(this);
		}
		
	}
}
