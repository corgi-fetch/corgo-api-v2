package com.corgo.DTO;

public class UserStubDTO {
	private String userId;
    private int rating;
    private String name;
    
    public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	
	@Override 
	public boolean equals(Object obj) {
		if(!(obj instanceof UserStubDTO)) {
			return false;
		}
		UserStubDTO stub = (UserStubDTO) obj;
		return stub.getUserId().equals(this.getUserId());
	}

}