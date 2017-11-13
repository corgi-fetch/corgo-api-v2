package com.corgo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.corgo.DTO.*;
import com.corgo.model.*;
import com.corgo.repository.*;
import com.corgo.transformer.*;

@Service
public class MongoDBUserService implements UserService{
	
	private final UserRepository userRepository;
	private final UserTransformer userTransformer;
	private final PostTransformer postTransformer;
	private final GroupTransformer groupTransformer;
	
	@Autowired
    MongoDBUserService(@Lazy UserRepository userRepository, @Lazy UserTransformer userTransformer, PostTransformer postTransformer, GroupTransformer groupTransformer) {
        this.userRepository = userRepository;
        this.userTransformer = userTransformer;
        this.postTransformer = postTransformer;
        this.groupTransformer = groupTransformer;
    }
	
	@Override
	public UserDTO create(UserDTO user) {
		User persisted = new User();
		persisted.setRating(user.getRating());
		persisted.setName(user.getName());
		persisted.setEmail(user.getEmail());
		persisted.setUserId(user.getUserId());
		
		persisted.setPostHistory(postTransformer.ConvertListOfPostDTOToPost(user.getPostHistory()));
		persisted.setCurrentPost(postTransformer.ConvertListOfPostDTOToPost(user.getCurrentPosts()));
		persisted.setCurrentJobs(postTransformer.ConvertListOfPostDTOToPost(user.getCurrentJobs()));
		
		persisted.setCreditCardNumber(user.getCreditCardNumber());
		persisted.setBankAccount(user.getBankAccount());
		
		persisted.setGroups(groupTransformer.ConvertListOfGroupDTOToGroup(user.getGroups()));
		
		persisted = userRepository.save(persisted);
		return userTransformer.ConvertUserToUserDTO(persisted);
	}
	
	@Override 
	public UserDTO delete(String userId) {
		User deleted = FindUserById(userId);
		userRepository.delete(deleted);
		return userTransformer.ConvertUserToUserDTO(deleted);
	}
	
	@Override
	public List<UserDTO> findAll() {
		List<User> userEntries = userRepository.findAll();
		return userTransformer.ConvertListOfUsersToUserDTO(userEntries);
	}
	
	@Override
	public UserDTO findById(String userId) {
		User found = FindUserById(userId);
		return userTransformer.ConvertUserToUserDTO(found);
	}
	
	public UserDTO findByUserId(String userId) {
		Optional<User> result = userRepository.findByUserId(userId);
		//TODO: IMPLEMENT EXCEPTION AND ERROR HANDLING
		return userTransformer.ConvertUserToUserDTO(result.get());
		
	}
	
	@Override
	public UserDTO update(UserDTO user) {
		User updated = FindUserById(user.getId());
		
		updated.setUserId(user.getUserId());
		
		updated.setRating(user.getRating());
		updated.setName(user.getName());
		updated.setEmail(user.getEmail());
		
		updated.setPostHistory(postTransformer.ConvertListOfPostDTOToPost(user.getPostHistory()));
		updated.setCurrentPost(postTransformer.ConvertListOfPostDTOToPost(user.getCurrentPosts()));
		updated.setCurrentJobs(postTransformer.ConvertListOfPostDTOToPost(user.getCurrentJobs()));
		
		updated.setCreditCardNumber(user.getCreditCardNumber());
		updated.setBankAccount(user.getBankAccount());
		
		updated.update(updated);
		updated = userRepository.save(updated);
		return userTransformer.ConvertUserToUserDTO(updated);
	}
	
	private User FindUserById(String userId) {
		Optional<User> result = userRepository.findByUserId(userId);
		//TODO: IMPLEMENT EXCEPTION AND ERROR HANDLING
		return result.get();
	}

}
