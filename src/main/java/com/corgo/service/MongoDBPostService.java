package com.corgo.service;


import java.util.List;
import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.corgo.DTO.*;
import com.corgo.model.*;
import com.corgo.repository.*;
import com.corgo.transformer.PostTransformer;
import com.corgo.transformer.UserTransformer;

@Service
final class MongoDBPostService implements PostService{
	
	private final PostRepository postRepository;
	private final UserTransformer userTransformer;
	private final PostTransformer postTransformer;
	
	
	@Autowired
    MongoDBPostService(PostRepository postRepository, UserTransformer userTransformer, PostTransformer postTransformer) {
        this.postRepository = postRepository;
        this.userTransformer = userTransformer;
        this.postTransformer = postTransformer;
    }
	
	@Override
	public PostDTO create(PostDTO post) {
		// TODO Auto-generated method stub
		Post persisted = Post.getBuilder(post.getDate(), 
				userTransformer.ConvertUserStubDTOToUser(post.getOwner()), 
				post.getTitle(), 
				post.getDescription(), 
				post.getPayment())
				.interestedQueue(userTransformer.ConvertListOfUserStubDTOToUser(post.getInterestedQueue()))
				.serviceGiven(post.isServiceGiven())
				.serviceReceived(post.isServiceReceived()).build();
		persisted = postRepository.save(persisted);
		return postTransformer.ConvertPostToPostDTO(persisted);
	}

	@Override
	public PostDTO delete(String id) {
		// TODO Auto-generated method stub
		Post deleted = FindPostById(id);
		postRepository.delete(deleted);
		return postTransformer.ConvertPostToPostDTO(deleted);
	}

	@Override
	public List<PostDTO> findAll() {
		// TODO Auto-generated method stub
		List<Post> postEntries = postRepository.findAll();
		return postTransformer.ConvertListOfPostsToPostDTO(postEntries);
	}

	@Override
	public PostDTO findById(String id) {
		// TODO Auto-generated method stub
		Post found = FindPostById(id);
		return postTransformer.ConvertPostToPostDTO(found);
	}

	@Override
	public PostDTO update(PostDTO post) {
		// TODO Auto-generated method stub
		Post updated = FindPostById(post.getId());
		updated.update(new Post.Builder(post.getDate(), 
				userTransformer.ConvertUserStubDTOToUser(post.getOwner()), 
				post.getTitle(), 
				post.getDescription(), 
				post.getPayment())
				.interestedQueue(userTransformer.ConvertListOfUserStubDTOToUser(post.getInterestedQueue()))
				.serviceGiven(post.isServiceGiven())
				.serviceReceived(post.isServiceReceived()));
		updated = postRepository.save(updated);
		return postTransformer.ConvertPostToPostDTO(updated);
	}

	private Post FindPostById(String id) {
		Optional<Post> result = postRepository.findOne(id);
		//TODO: IMPLEMENT EXCEPTION AND ERROR HANDLING
		return result.get();
	}
	

}
