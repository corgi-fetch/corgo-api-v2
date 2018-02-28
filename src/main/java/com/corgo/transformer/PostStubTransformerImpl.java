package com.corgo.transformer;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.corgo.DTO.PostStubDTO;
import com.corgo.model.Post;
import com.corgo.repository.PostRepository;

@Component
public class PostStubTransformerImpl implements PostStubTransformer {
	private final PostRepository postRepository;
	private final UserTransformer userTransformer;
	
	@Autowired
	PostStubTransformerImpl(PostRepository postRepository, @Lazy UserTransformer userTransformer) {
        this.postRepository = postRepository;
        this.userTransformer = userTransformer;
    }
	
	public List<PostStubDTO> ConvertListOfPostsToPostStubDTO(List<Post> listPost) {
		return listPost.stream()
				.map(this::ConvertPostToPostStubDTO)
				.collect(toList());
	}
	
	public PostStubDTO ConvertPostToPostStubDTO(Post model) {
		if (model != null) {
	        PostStubDTO dto = new PostStubDTO();
	        dto.setId(model.getId());
	        dto.setDate(model.getDate());
	        dto.setOwner(model.getOwner());
	        dto.setTitle(model.getTitle());
	        dto.setDescription(model.getDescription());
	        dto.setPayment(model.getPayment());
	        dto.setGroupId(model.getGroupId());
	        return dto;
		} else {
			return null;
		}
        
    }
	
	public List<Post> ConvertListOfPostStubDTOToPost(List<PostStubDTO> listPost) {
		return listPost.stream()
				.map(this::ConvertPostStubDTOToPost)
				.collect(toList());
	}
	
	public Post ConvertPostStubDTOToPost(PostStubDTO dto) {
		Post toReturn;
		if (dto != null && dto.getId() != null) {
			toReturn = postRepository.findOne(dto.getId()).get();
		} else {
			toReturn = null;
		}
		
		return toReturn;
        
    }
}
	

