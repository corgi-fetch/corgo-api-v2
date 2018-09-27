package com.corgo.transformer;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.corgo.DTO.PostDTO;
import com.corgo.model.Post;
import com.corgo.repository.PostRepository;

@Component
final class PostTransformerImpl implements PostTransformer{
	
	private final PostRepository postRepository;
	private final UserTransformer userTransformer;
	
	@Autowired
	PostTransformerImpl(PostRepository postRepository, @Lazy UserTransformer userTransformer) {
        this.postRepository = postRepository;
        this.userTransformer = userTransformer;
    }
	
	public List<PostDTO> ConvertListOfPostsToPostDTO(List<Post> listPost) {
		if (listPost == null || listPost.size() == 0) {
			return new ArrayList<PostDTO>();
		} else {
			return listPost.stream()
					.map(this::ConvertPostToPostDTO)
					.collect(toList());
		}
	}
	
	public List<Post> ConvertListOfPostDTOToPost(List<PostDTO> listPostDTO) {
		if (listPostDTO == null || listPostDTO.size() == 0) {
			return new ArrayList<Post>();
		} else {
			return listPostDTO.stream()
					.map(this::ConvertPostDTOToPost)
					.collect(toList());
		}
	}
	
	public Post ConvertPostDTOToPost(PostDTO postDTO) {
		
		Post toReturn;
		if (postDTO.getId() != null) {
			toReturn = postRepository.findById(postDTO.getId()).get();
		} else {
			toReturn = new Post().getBuilder(postDTO.getDate(), userTransformer.ConvertUserStubDTOToUserStub(postDTO.getOwner()), postDTO.getTitle(), 
					postDTO.getDescription(), postDTO.getPayment(), postDTO.getGroupId(), postDTO.getState()).
					interestedQueue(userTransformer.ConvertListOfUserStubDTOToUserStubs(postDTO.getInterestedQueue()))
					.responderUserId(postDTO.getResponderUserId())
					.selectedUserId(postDTO.getSelectedUserId())
					.serviceGiven(postDTO.isServiceGiven())
					.serviceReceived(postDTO.isServiceReceived()).build();
		}
		
		//TODO: IMPLEMENT EXCEPTION AND ERROR HANDLING
		return toReturn;
	}
	
	public PostDTO ConvertPostToPostDTO(Post model) {
		if (model != null) {
	        PostDTO dto = new PostDTO();
	        dto.setInterestedQueue(userTransformer.ConvertListOfUserStubsToUserStubDTO(model.getInterestedQueue()));
	        if(dto.getInterestedQueue().size() != 0)
	        		System.out.println("this is in the convert" + dto.getInterestedQueue().get(0).getName());
	        dto.setId(model.getId());
	        dto.setDate(model.getDate());
	        dto.setOwner(userTransformer.ConvertUserStubToUserStubDTO(model.getOwner()));
	        dto.setTitle(model.getTitle());
	        dto.setDescription(model.getDescription());
	        dto.setPayment(model.getPayment());
	        dto.setServiceGiven(model.getServiceGiven());
	        dto.setServiceReceived(model.getServiceReceived());
	        dto.setGroupId(model.getGroupId());
	        dto.setSelectedUserId(model.getSelectedUserId());
	        dto.setResponderUserId(model.getResponderUserId());
	        dto.setState(model.getState());
	        return dto;
		} else {
			return null;
		}
        
    }
	
}
