package com.corgo.transformer;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.corgo.DTO.PostStubDTO;
import com.corgo.model.GroupStub;
import com.corgo.model.Post;
import com.corgo.model.PostStub;
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
		if (listPost == null || listPost.size() == 0) {
			return new ArrayList<PostStubDTO>();
		} else {
			return listPost.stream()
					.map(this::ConvertPostToPostStubDTO)
					.collect(toList());
		}
	}
	
	public PostStubDTO ConvertPostToPostStubDTO(Post model) {
		if (model != null) {
			System.out.println("this is the id: " + model.getId());
	        PostStubDTO dto = new PostStubDTO();
	        dto.setId(model.getId());
	        dto.setDate(model.getDate());
	        dto.setOwner(userTransformer.ConvertUserStubToUserStubDTO(model.getOwner()));
	        dto.setTitle(model.getTitle());
	        dto.setDescription(model.getDescription());
	        dto.setPayment(model.getPayment());
	        dto.setGroupId(model.getGroupId());
	        dto.setState(model.getState());
	        System.out.println("this is the state after transformation: " + dto.getState());
	        return dto;
		} else {
			return null;
		}
        
    }
	
	public List<Post> ConvertListOfPostStubDTOToPost(List<PostStubDTO> listPost) {
		if (listPost == null || listPost.size() == 0) {
			return new ArrayList<Post>();
		} else {
			return listPost.stream()
					.map(this::ConvertPostStubDTOToPost)
					.collect(toList());
		}
	}
	
	
	
	public Post ConvertPostStubDTOToPost(PostStubDTO dto) {
		Post toReturn;
		if (dto != null && dto.getId() != null) {
			toReturn = postRepository.findById(dto.getId()).get();
		} else {
			toReturn = null;
		}
		
		return toReturn;
        
    }

	@Override
	public List<PostStub> ConvertListOfPostStubDTOToPostStubs(List<PostStubDTO> listPost) {
		if (listPost == null || listPost.size() == 0) {
			return new ArrayList<PostStub>();
		} else {
			return listPost.stream()
					.map(this::ConvertPostStubDTOToPostStub)
					.collect(toList());
		}
	}
	
	@Override
	public PostStub ConvertPostStubDTOToPostStub(PostStubDTO stubDTO) {
		PostStub toReturn = new PostStub();
		toReturn.setDate(stubDTO.getDate());
		toReturn.setDescription(stubDTO.getDescription());
		toReturn.setGroupId(stubDTO.getGroupId());
		toReturn.setId(stubDTO.getId());
		toReturn.setOwner(userTransformer.ConvertUserStubDTOToUserStub(stubDTO.getOwner()));
		toReturn.setPayment(stubDTO.getPayment());
		toReturn.setTitle(stubDTO.getTitle());
		toReturn.setState(stubDTO.getState());
		return toReturn;
	}
	
	@Override
	public List<PostStubDTO> ConvertListOfPostStubsToPostStubDTO(List<PostStub> listPost) {
		if (listPost == null || listPost.size() == 0) {
			return new ArrayList<PostStubDTO>();
		} else {
			return listPost.stream()
					.map(this::ConvertPostStubToPostStubDTO)
					.collect(toList());
		}
	}
	
	@Override
	public PostStubDTO ConvertPostStubToPostStubDTO(PostStub stub) {
		PostStubDTO toReturn = new PostStubDTO();
		toReturn.setDate(stub.getDate());
		toReturn.setDescription(stub.getDescription());
		toReturn.setGroupId(stub.getGroupId());
		toReturn.setId(stub.getId());
		toReturn.setOwner(userTransformer.ConvertUserStubToUserStubDTO(stub.getOwner()));
		toReturn.setPayment(stub.getPayment());
		toReturn.setTitle(stub.getTitle());
		toReturn.setState(stub.getState());
		
		System.out.println("what is the stub " + toReturn.getState());
		return toReturn;
	}
}
	

