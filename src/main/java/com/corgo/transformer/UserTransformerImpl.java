package com.corgo.transformer;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.corgo.DTO.UserDTO;
import com.corgo.DTO.UserStubDTO;
import com.corgo.model.GroupStub;
import com.corgo.model.User;
import com.corgo.model.UserStub;
import com.corgo.repository.UserRepository;
import com.corgo.service.UserService;

@Component
final class UserTransformerImpl implements UserTransformer {
	
	private final PostTransformer postTransformer;
	private final UserRepository userRepository;
	private final GroupTransformer groupTransformer;
	private final UserService userService;
	private final PostStubTransformer postStubTransformer;
	
	@Autowired
	UserTransformerImpl(@Lazy PostTransformer postTransformer, UserRepository userRepository, GroupTransformer groupTransformer, UserService userService, PostStubTransformer postStubTransformer) {
		this.postTransformer = postTransformer;
		this.groupTransformer = groupTransformer;
		this.userRepository = userRepository;
		this.userService = userService;
		this.postStubTransformer = postStubTransformer;
	}

	public UserDTO ConvertUserToUserDTO(User model) {
		UserDTO userDTO = new UserDTO();
		
		userDTO.setId(model.getId());
		
		userDTO.setRating(model.getRating());
		userDTO.setName(model.getName());
		userDTO.setEmail(model.getEmail());
		
		userDTO.setUserId(model.getUserId());
		
		userDTO.setPostHistory(postStubTransformer.ConvertListOfPostStubsToPostStubDTO(model.getPostHistory()));
		userDTO.setCurrentPosts(postStubTransformer.ConvertListOfPostStubsToPostStubDTO(model.getCurrentPost()));
		userDTO.setCurrentJobs(postStubTransformer.ConvertListOfPostStubsToPostStubDTO(model.getCurrentJobs()));
		
		userDTO.setCreditCardNumber(model.getCreditCardNumber());
		userDTO.setBankAccount(model.getBankAccount());
		
		userDTO.setGroups(groupTransformer.ConvertListOfGroupStubToGroupStubDTO(model.getGroups()));
		
		
		return userDTO;
	}
	
	public List<UserDTO> ConvertListOfUsersToUserDTO(List<User> listUser) {
		if (listUser == null || listUser.size() == 0) {
			return new ArrayList<UserDTO>();
		} else {
			return listUser.stream()
					.map(this::ConvertUserToUserDTO)
					.collect(toList());
		}
	}
	
	public List<UserStubDTO> ConvertListOfUsersToUserStubDTO(List<User> listUser) {
		if (listUser == null || listUser.size() == 0) {
			return new ArrayList<UserStubDTO>();
		} else {
			return listUser.stream()
					.map(this::ConvertUserToUserStubDTO)
					.collect(toList());
		}
	}
	
	public UserStubDTO ConvertUserToUserStubDTO(User model)
	{
		UserStubDTO dto = new UserStubDTO();
		dto.setUserId(model.getUserId());
		dto.setName(model.getName());
		dto.setRating(model.getRating());
		return dto;
	}
	
	public UserStubDTO ConvertUserDTOToUserStubDTO(UserDTO userDTO) {
		UserStubDTO dto = new UserStubDTO();
		dto.setUserId(userDTO.getUserId());
		dto.setName(userDTO.getName());
		dto.setRating(userDTO.getRating());
		return dto;
	}
	
	public User ConvertUserStubDTOToUser(UserStubDTO userStubDTO)
	{
		Optional<User> toReturn = userRepository.findByUserId(userStubDTO.getUserId());
		//TODO: IMPLEMENT EXCEPTION AND ERROR HANDLING
		return toReturn.get();
	}
	
	public List<User> ConvertListOfUserStubDTOToUser(List<UserStubDTO> listUserStubDTO)
	{
		if (listUserStubDTO == null || listUserStubDTO.size() == 0) {
			return new ArrayList<User>();
		} else {
			return listUserStubDTO.stream()
					.map(this::ConvertUserStubDTOToUser)
					.collect(toList());
		}
	}

	@Override
	public List<User> ConvertListOfUserDTOToUser(List<UserDTO> listUserDTO) {
		if (listUserDTO == null || listUserDTO.size() == 0) {
			return new ArrayList<User>();
		} else {
			return listUserDTO.stream()
					.map(this::ConvertUserDTOToUser)
					.collect(toList());
		}
	}

	@Override
	public User ConvertUserDTOToUser(UserDTO userDTO) {
		return userRepository.findByUserId(userDTO.getUserId()).get();
	}

	@Override
	public List<UserStubDTO> ConvertListOfUserStubsToUserStubDTO(List<UserStub> listUserStub) {
		if (listUserStub == null || listUserStub.size() == 0) {
			return new ArrayList<UserStubDTO>();
		} else {
			return listUserStub.stream()
					.map(this::ConvertUserStubToUserStubDTO)
					.collect(toList());
		}
	}
	
	@Override
	public UserStubDTO ConvertUserStubToUserStubDTO(UserStub stub) {
		UserStubDTO toReturn = new UserStubDTO();
		toReturn.setName(stub.getName());
		toReturn.setRating(stub.getRating());
		toReturn.setUserId(stub.getUserId());
		return toReturn;
	}

	@Override
	public List<UserStub> ConvertListOfUserStubDTOToUserStubs(List<UserStubDTO> listUserStubDTO) {
		if (listUserStubDTO == null || listUserStubDTO.size() == 0) {
			return new ArrayList<UserStub>();
		} else {
			return listUserStubDTO.stream()
					.map(this::ConvertUserStubDTOToUserStub)
					.collect(toList());
		}
	}
	
	@Override
	public UserStub ConvertUserStubDTOToUserStub(UserStubDTO stubDTO) {
		UserStub toReturn = new UserStub();
		toReturn.setName(stubDTO.getName());
		toReturn.setRating(stubDTO.getRating());
		toReturn.setUserId(stubDTO.getUserId());
		return toReturn;
	}

}
