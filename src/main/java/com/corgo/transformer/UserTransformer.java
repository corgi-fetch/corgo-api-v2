package com.corgo.transformer;

import java.util.List;

import com.corgo.DTO.UserDTO;
import com.corgo.DTO.UserStubDTO;
import com.corgo.model.User;
import com.corgo.model.UserStub;

public interface UserTransformer {
	public User ConvertUserStubDTOToUser(UserStubDTO userStubDTO);
	public UserStubDTO ConvertUserToUserStubDTO(User model);
	public List<User> ConvertListOfUserStubDTOToUser(List<UserStubDTO> listUserStubDTO);
	
	public List<User> ConvertListOfUserDTOToUser(List<UserDTO> listUserDTO);
	public User ConvertUserDTOToUser(UserDTO userDTO);
	
	
	public UserDTO ConvertUserToUserDTO(User model);
	public List<UserDTO> ConvertListOfUsersToUserDTO(List<User> listUser);
	public UserStubDTO ConvertUserDTOToUserStubDTO(UserDTO userDTO);
	public List<UserStubDTO> ConvertListOfUsersToUserStubDTO(List<User> listUser);
	
	public List<UserStubDTO> ConvertListOfUserStubsToUserStubDTO(List<UserStub> listUser);
	public List<UserStub> ConvertListOfUserStubDTOToUserStubs(List<UserStubDTO> listUser);
	UserStubDTO ConvertUserStubToUserStubDTO(UserStub stub);
	UserStub ConvertUserStubDTOToUserStub(UserStubDTO stubDTO);
}
