package com.corgo.transformer;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.corgo.DTO.UserDTO;
import com.corgo.DTO.UserStubDTO;
import com.corgo.model.User;
import com.corgo.repository.UserRepository;

@Component
final class UserTransformerImpl implements UserTransformer {
	
	private final PostTransformer postTransformer;
	private final UserRepository userRepository;
	private final GroupTransformer groupTransformer;
	
	@Autowired
	UserTransformerImpl(@Lazy PostTransformer postTransformer, UserRepository userRepository, GroupTransformer groupTransformer) {
		this.postTransformer = postTransformer;
		this.groupTransformer = groupTransformer;
		this.userRepository = userRepository;
	}

	public UserDTO ConvertUserToUserDTO(User model) {
		UserDTO userDTO = new UserDTO();
		
		userDTO.setId(model.getId());
		
		userDTO.setRating(model.getRating());
		userDTO.setName(model.getName());
		userDTO.setEmail(model.getEmail());
		
		userDTO.setUserId(model.getUserId());
		
		userDTO.setPostHistory(postTransformer.ConvertListOfPostsToPostDTO(model.getPostHistory()));
		userDTO.setCurrentPosts(postTransformer.ConvertListOfPostsToPostDTO(model.getCurrentPost()));
		userDTO.setCurrentJobs(postTransformer.ConvertListOfPostsToPostDTO(model.getCurrentJobs()));
		
		userDTO.setCreditCardNumber(model.getCreditCardNumber());
		userDTO.setBankAccount(model.getBankAccount());
		
		userDTO.setGroups(groupTransformer.ConvertListOfGroupToGroupDTO(model.getGroups()));
		
		
		return userDTO;
	}
	
	public List<UserDTO> ConvertListOfUsersToUserDTO(List<User> listUser) {
		return listUser.stream()
				.map(this::ConvertUserToUserDTO)
				.collect(toList());
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
		return listUserStubDTO.stream()
				.map(this::ConvertUserStubDTOToUser)
				.collect(toList());
	}

}
