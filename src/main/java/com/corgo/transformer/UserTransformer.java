package com.corgo.transformer;

import java.util.List;

import com.corgo.DTO.UserDTO;
import com.corgo.DTO.UserStubDTO;
import com.corgo.model.User;

public interface UserTransformer {
	public User ConvertUserStubDTOToUser(UserStubDTO userStubDTO);
	public UserStubDTO ConvertUserToUserStubDTO(User model);
	public List<User> ConvertListOfUserStubDTOToUser(List<UserStubDTO> listUserStubDTO);
	
	public UserDTO ConvertUserToUserDTO(User model);
	public List<UserDTO> ConvertListOfUsersToUserDTO(List<User> listUser);
	public UserStubDTO ConvertUserDTOToUserStubDTO(UserDTO userDTO);
}
