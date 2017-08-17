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
		return listPost.stream()
				.map(this::ConvertPostToPostDTO)
				.collect(toList());
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
		Optional<Post> toReturn = postRepository.findOne(postDTO.getId());
		//TODO: IMPLEMENT EXCEPTION AND ERROR HANDLING
		return toReturn.get();
	}
	
	public PostDTO ConvertPostToPostDTO(Post model) {
        PostDTO dto = new PostDTO();
 
        dto.setId(model.getId());
        dto.setDate(model.getDate());
        dto.setOwner(userTransformer.ConvertUserToUserStubDTO(model.getOwner()));
        dto.setTitle(model.getTitle());
        dto.setDescription(model.getDescription());
        dto.setPayment(model.getPayment());
        dto.setServiceGiven(model.getServiceGiven());
        dto.setServiceReceived(model.getServiceReceived());
        return dto;
    }
	
}
