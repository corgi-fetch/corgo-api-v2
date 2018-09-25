package com.corgo.service;

import java.util.ArrayList;
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
	private final PostStubTransformer postStubTransformer;
	
	@Autowired
    MongoDBUserService(@Lazy UserRepository userRepository, @Lazy UserTransformer userTransformer, PostTransformer postTransformer, GroupTransformer groupTransformer, PostStubTransformer postStubTransformer) {
        this.userRepository = userRepository;
        this.userTransformer = userTransformer;
        this.postTransformer = postTransformer;
        this.groupTransformer = groupTransformer;
        this.postStubTransformer = postStubTransformer;
    }
	
	@Override
	public UserDTO create(UserDTO user) {
		User persisted = new User();
		persisted.setRating(user.getRating());
		persisted.setName(user.getName());
		persisted.setEmail(user.getEmail());
		persisted.setUserId(user.getUserId());
		
		persisted.setPostHistory(postStubTransformer.ConvertListOfPostStubDTOToPostStubs(user.getPostHistory()));
		persisted.setCurrentPost(postStubTransformer.ConvertListOfPostStubDTOToPostStubs(user.getCurrentPosts()));
		persisted.setCurrentJobs(postStubTransformer.ConvertListOfPostStubDTOToPostStubs(user.getCurrentJobs()));
		
		persisted.setCreditCardNumber(user.getCreditCardNumber());
		persisted.setBankAccount(user.getBankAccount());
		
		persisted.setGroups(groupTransformer.ConvertListOfGroupStubDTOToGroupStub(user.getGroups()));
		
		persisted.setPushToken(user.getPushToken());
		
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
		System.out.println(FindUserById(userId));
		return userTransformer.ConvertUserToUserDTO(result.get());
		
	}
	
	@Override
	public UserDTO update(UserDTO user) {
		User updated = FindUserById(user.getUserId());
		
		updated.setUserId(user.getUserId());
		
		updated.setRating(user.getRating());
		updated.setName(user.getName());
		updated.setEmail(user.getEmail());
		
		updated.setPostHistory(postStubTransformer.ConvertListOfPostStubDTOToPostStubs(user.getPostHistory()));
		updated.setCurrentPost(postStubTransformer.ConvertListOfPostStubDTOToPostStubs(user.getCurrentPosts()));
		updated.setCurrentJobs(postStubTransformer.ConvertListOfPostStubDTOToPostStubs(user.getCurrentJobs()));
		
		updated.setCreditCardNumber(user.getCreditCardNumber());
		updated.setBankAccount(user.getBankAccount());
		
		updated.setGroups(groupTransformer.ConvertListOfGroupStubDTOToGroupStub(user.getGroups()));
		
		updated.setPushToken(user.getPushToken());
		
		updated.update(updated);
		updated = userRepository.save(updated);
		return userTransformer.ConvertUserToUserDTO(updated);
	}
	
	@Override
	public UserDTO updateWithNewPost(PostDTO post, String userId) {
		//Getting the currentUser
		UserDTO currentUser = findByUserId(userId);
		
		//Getting existing user posts
		List<PostStubDTO> userPosts = currentUser.getCurrentPosts();
		
		PostStubDTO toAdd = new PostStubDTO();
		toAdd.setDate(post.getDate());
		toAdd.setDescription(post.getDescription());
		toAdd.setGroupId(post.getGroupId());
		toAdd.setId(post.getId());
		toAdd.setOwner(post.getOwner());
		toAdd.setPayment(post.getPayment());
		toAdd.setTitle(post.getTitle());
		toAdd.setState(post.getState());
		
		
		//Adding to list the new post
		userPosts.add(toAdd);
		
		//Set current user
		currentUser.setCurrentPosts(userPosts);
		
		//Update current user
		//NOTE: this unnecessarily retrieves user twice, once  here, once in update
		UserDTO updatedDTO = update(currentUser);
		return updatedDTO;
	}
	
	@Override
	public UserDTO updateWithExistingPost(Post persisted, String userId) {
		//Getting the currentUser
		UserDTO currentUser = findByUserId(userId);
		
		//Getting existing user posts
		List<PostStubDTO> userPosts = currentUser.getCurrentPosts();
		
		List<PostStubDTO> updatedList = findAndReplace(userPosts, postStubTransformer.ConvertPostToPostStubDTO(persisted));
		
		//Set current user
		currentUser.setCurrentPosts(updatedList);
		
		//Update current user
		//NOTE: this unnecessarily retrieves user twice, once  here, once in update
		UserDTO updatedDTO = update(currentUser);
		return updatedDTO;
	}
	
	
	
	private User FindUserById(String userId) {
		Optional<User> result = userRepository.findByUserId(userId);
		//TODO: IMPLEMENT EXCEPTION AND ERROR HANDLING
		return result.get();
	}
	
	List<PostStubDTO> findAndReplace(List<PostStubDTO> listStub, PostStubDTO stubDTO) {
		List<PostStubDTO> toReturn = new ArrayList<>();
	    for(PostStubDTO stub : listStub) {
	    	PostStubDTO toAdd = new PostStubDTO();
	        if(stub.getId().equals(stubDTO.getId())) {
	        	toAdd.setDate(stubDTO.getDate());
	        	toAdd.setDescription(stubDTO.getDescription());
	        	toAdd.setGroupId(stubDTO.getGroupId());
	        	toAdd.setId(stubDTO.getId());
	        	toAdd.setOwner(stubDTO.getOwner());
	        	toAdd.setPayment(stubDTO.getPayment());
	        	toAdd.setState(stubDTO.getState());
	        	toAdd.setTitle(stubDTO.getTitle());
	        } else {
	        	toAdd.setDate(stub.getDate());
	        	toAdd.setDescription(stub.getDescription());
	        	toAdd.setGroupId(stub.getGroupId());
	        	toAdd.setId(stub.getId());
	        	toAdd.setOwner(stub.getOwner());
	        	toAdd.setPayment(stub.getPayment());
	        	toAdd.setState(stub.getState());
	        	toAdd.setTitle(stub.getTitle());
	        }
	        
	        toReturn.add(toAdd);
	    }
	    
	    return toReturn;
	}

}
