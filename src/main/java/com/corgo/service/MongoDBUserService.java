package com.corgo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.corgo.DTO.*;
import com.corgo.model.*;
import com.corgo.repository.*;
import static java.util.stream.Collectors.toList;

@Service
public class MongoDBUserService implements UserService{
	
	private final PostRepository postRepository;
	private final UserRepository userRepository;
	
	@Autowired
    MongoDBUserService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }
	
	@Override
	public UserDTO create(UserDTO user) {
		User persisted = new User();
		persisted.setRating(user.getRating());
		persisted.setName(user.getName());
		persisted.setEmail(user.getEmail());
		
		persisted.setPostHistory(ConvertListOfPostDTOToPost(user.getPostHistory()));
		persisted.setCurrentPost(ConvertListOfPostDTOToPost(user.getCurrentPosts()));
		persisted.setCurrentJobs(ConvertListOfPostDTOToPost(user.getCurrentJobs()));
		
		persisted.setCreditCardNumber(user.getCreditCardNumber());
		persisted.setBankAccount(user.getBankAccount());
		
		persisted = userRepository.save(persisted);
		return ConvertUserToUserDTO(persisted);
	}
	
	@Override 
	public UserDTO delete(String id) {
		User deleted = FindUserById(id);
		userRepository.delete(deleted);
		return ConvertUserToUserDTO(deleted);
	}
	
	@Override
	public List<UserDTO> findAll() {
		List<User> userEntries = userRepository.findAll();
		return ConvertListOfUsersToUserDTO(userEntries);
	}
	
	@Override
	public UserDTO findById(String id) {
		User found = FindUserById(id);
		return ConvertUserToUserDTO(found);
	}
	
	@Override
	public UserDTO update(UserDTO user) {
		User updated = FindUserById(user.getId());

		updated.setRating(user.getRating());
		updated.setName(user.getName());
		updated.setEmail(user.getEmail());
		
		updated.setPostHistory(ConvertListOfPostDTOToPost(user.getPostHistory()));
		updated.setCurrentPost(ConvertListOfPostDTOToPost(user.getCurrentPosts()));
		updated.setCurrentJobs(ConvertListOfPostDTOToPost(user.getCurrentJobs()));
		
		updated.setCreditCardNumber(user.getCreditCardNumber());
		updated.setBankAccount(user.getBankAccount());
		
		updated.update(updated);
		return ConvertUserToUserDTO(updated);
	}
	
	
	private Post ConvertPostDTOToPost(PostDTO postDTO) {
		Optional<Post> toReturn = postRepository.findOne(postDTO.getId());
		//TODO: IMPLEMENT EXCEPTION AND ERROR HANDLING
		return toReturn.get();
	}
	
	private List<Post> ConvertListOfPostDTOToPost(List<PostDTO> listPostDTO) {
		if (listPostDTO == null || listPostDTO.size() == 0) {
			return new ArrayList<Post>();
		} else {
			return listPostDTO.stream()
					.map(this::ConvertPostDTOToPost)
					.collect(toList());
		}
	}
	
	private UserDTO ConvertUserToUserDTO(User model) {
		UserDTO userDTO = new UserDTO();
		
		userDTO.setId(model.getId());
		
		userDTO.setRating(model.getRating());
		userDTO.setName(model.getName());
		userDTO.setEmail(model.getEmail());
		
		userDTO.setPostHistory(ConvertListOfPostsToPostDTO(model.getPostHistory()));
		userDTO.setCurrentPosts(ConvertListOfPostsToPostDTO(model.getCurrentPost()));
		userDTO.setCurrentJobs(ConvertListOfPostsToPostDTO(model.getCurrentJobs()));
		
		userDTO.setCreditCardNumber(model.getCreditCardNumber());
		userDTO.setBankAccount(model.getBankAccount());
		
		
		return userDTO;
	}
	
	private List<PostDTO> ConvertListOfPostsToPostDTO(List<Post> listPost) {
		return listPost.stream()
				.map(this::ConvertPostToPostDTO)
				.collect(toList());
	}
	
	private UserStubDTO ConvertUserToUserStubDTO(User model)
	{
		UserStubDTO dto = new UserStubDTO();
		dto.setId(model.getId());
		dto.setName(model.getName());
		dto.setRating(model.getRating());
		return dto;
	}
	
	private List<UserDTO> ConvertListOfUsersToUserDTO(List<User> listUser) {
		return listUser.stream()
				.map(this::ConvertUserToUserDTO)
				.collect(toList());
	}
	
	private PostDTO ConvertPostToPostDTO(Post model) {
        PostDTO dto = new PostDTO();
 
        dto.setId(model.getId());
        
        dto.setDate(model.getDate());
        dto.setOwner(ConvertUserToUserStubDTO(model.getOwner()));
        dto.setTitle(model.getTitle());
        dto.setDescription(model.getDescription());
        dto.setPayment(model.getPayment());
        dto.setServiceGiven(model.getServiceGiven());
        dto.setServiceReceived(model.getServiceReceived());
        return dto;
    }
	
	private User FindUserById(String id) {
		Optional<User> result = userRepository.findOne(id);
		//TODO: IMPLEMENT EXCEPTION AND ERROR HANDLING
		return result.get();
	}

}
