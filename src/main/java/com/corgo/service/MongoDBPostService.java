package com.corgo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.corgo.DTO.*;
import com.corgo.model.*;
import com.corgo.repository.*;
import static java.util.stream.Collectors.toList;

@Service
final class MongoDBPostService implements PostService{
	
	private final PostRepository postRepository;
	private final UserRepository userRepository;
	
	@Autowired
    MongoDBPostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }
	
	@Override
	@ResponseStatus(HttpStatus.CREATED)
	public PostDTO create(@RequestBody @Valid PostDTO post) {
		// TODO Auto-generated method stub
		Post persisted = Post.getBuilder(post.getDate(), 
				ConvertUserStubDTOToUser(post.getOwner()), 
				post.getTitle(), 
				post.getDescription(), 
				post.getPayment())
				.interestedQueue(ConvertListOfUserStubDTOToUser(post.getInterestedQueue()))
				.serviceGiven(post.isServiceGiven())
				.serviceReceived(post.isServiceReceived()).build();
		persisted = postRepository.save(persisted);
		return ConvertPostToPostDTO(persisted);
	}

	@Override
	public PostDTO delete(String id) {
		// TODO Auto-generated method stub
		Post deleted = FindPostById(id);
		postRepository.delete(deleted);
		return ConvertPostToPostDTO(deleted);
	}

	@Override
	public List<PostDTO> findAll() {
		// TODO Auto-generated method stub
		List<Post> postEntries = postRepository.findAll();
		return ConvertListOfPostsToPostDTO(postEntries);
	}

	@Override
	public PostDTO findById(String id) {
		// TODO Auto-generated method stub
		Post found = FindPostById(id);
		return ConvertPostToPostDTO(found);
	}

	@Override
	public PostDTO update(PostDTO post) {
		// TODO Auto-generated method stub
		Post updated = FindPostById(post.getId());
		updated.update(new Post.Builder(post.getDate(), 
				ConvertUserStubDTOToUser(post.getOwner()), 
				post.getTitle(), 
				post.getDescription(), 
				post.getPayment())
				.interestedQueue(ConvertListOfUserStubDTOToUser(post.getInterestedQueue()))
				.serviceGiven(post.isServiceGiven())
				.serviceReceived(post.isServiceReceived()));
		updated = postRepository.save(updated);
		return ConvertPostToPostDTO(updated);
	}
	
	private User ConvertUserStubDTOToUser(UserStubDTO userStubDTO)
	{
		Optional<User> toReturn = userRepository.findOne(userStubDTO.getId());
		//TODO: IMPLEMENT EXCEPTION AND ERROR HANDLING
		return toReturn.get();
	}
	
	private List<User> ConvertListOfUserStubDTOToUser(List<UserStubDTO> listUserStubDTO)
	{
		return listUserStubDTO.stream()
				.map(this::ConvertUserStubDTOToUser)
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
	
	private List<PostDTO> ConvertListOfPostsToPostDTO(List<Post> listPost) {
		return listPost.stream()
				.map(this::ConvertPostToPostDTO)
				.collect(toList());
	}
	
	private Post FindPostById(String id) {
		Optional<Post> result = postRepository.findOne(id);
		//TODO: IMPLEMENT EXCEPTION AND ERROR HANDLING
		return result.get();
	}
	

}
