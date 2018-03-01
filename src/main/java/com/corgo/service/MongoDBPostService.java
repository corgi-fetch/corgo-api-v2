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
import com.corgo.transformer.PostStubTransformer;

@Service
final class MongoDBPostService implements PostService{
	
	private final PostRepository postRepository;
	private final UserTransformer userTransformer;
	private final PostTransformer postTransformer;
	private final UserService userService;
	private final GroupService groupService;
	private final PostStubTransformer postStubTransformer;
	
	
	@Autowired
    MongoDBPostService(PostRepository postRepository, UserTransformer userTransformer, PostTransformer postTransformer, UserService userService, GroupService groupService, PostStubTransformer postStubTransformer) {
        this.postRepository = postRepository;
        this.userTransformer = userTransformer;
        this.postTransformer = postTransformer;
        this.userService = userService;
        this.groupService = groupService;
        this.postStubTransformer = postStubTransformer;
    }
	
	@Override
	public PostDTO create(PostDTO post) {
		// TODO Auto-generated method stub
		//Saving in PostReository
		//System.out.println("prior to save " + post.getGroupId());
		
		Post persisted = Post.getBuilder(post.getDate(), 
				post.getOwner(), 
				post.getTitle(), 
				post.getDescription(), 
				post.getPayment(),
				post.getGroupId(),
				post.getState())
				.interestedQueue(userTransformer.ConvertListOfUserStubDTOToUser(post.getInterestedQueue()))
				.serviceGiven(post.isServiceGiven())
				.serviceReceived(post.isServiceReceived())
				.selectedUserId(post.getSelectedUserId())
				.responderUserId(post.getResponderUserId()).build();
		persisted = postRepository.save(persisted);
		
		
		//Updating UserRepository
		String userId = post.getOwner();
		List<PostDTO> userPosts = userService.findByUserId(userId).getCurrentPosts();
		userPosts.add(post);
		UserDTO currentUser = userService.findByUserId(userId);
		currentUser.setCurrentPosts(userPosts);
		userService.update(currentUser);
		
		//Updating GroupRepository
		GroupDTO group = groupService.findById(post.getGroupId());
		List<PostStubDTO> currentPosts = group.getPosts();
		Post transPost = postTransformer.ConvertPostDTOToPost(post);
		currentPosts.add(postStubTransformer.ConvertPostToPostStubDTO(transPost));
		group.setPosts(currentPosts);
		System.out.println("prior to post service " + group.getPosts().toString());
		
		group = groupService.update(group);
		
		System.out.println("in post service " + group.getPosts().toString());
		
		return postTransformer.ConvertPostToPostDTO(persisted);
	}

	@Override
	public PostDTO delete(String id) {
		//PostRepository
		Post deleted = FindPostById(id);
		postRepository.delete(deleted);
		
		String userId = deleted.getOwner();
		
		if (findById(id).isServiceGiven() && findById(id).isServiceReceived()) {
			return null;
		} else {
			//PostDTO toDelete = findById(id);
			List<PostDTO> userPosts = userService.findByUserId(userId).getPostHistory();
			userPosts.remove(findById(id));
		}
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
		System.out.println("this is in findById " + found.getInterestedQueue().size());
		return postTransformer.ConvertPostToPostDTO(found);
	}

	@Override
	public PostDTO update(PostDTO post) {
		// TODO Auto-generated method stub
		/** FIX GROUP WHEN GROUP IS CHANGED **/
		Post updated = FindPostById(post.getId());
		updated.update(new Post.Builder(post.getDate(), 
				post.getOwner(), 
				post.getTitle(), 
				post.getDescription(), 
				post.getPayment(),
				post.getGroupId(),
				post.getState())
				.interestedQueue(userTransformer.ConvertListOfUserStubDTOToUser(post.getInterestedQueue()))
				.serviceGiven(post.isServiceGiven())
				.serviceReceived(post.isServiceReceived())
				.selectedUserId(post.getSelectedUserId())
				.responderUserId(post.getResponderUserId()));
		System.out.println(updated.getInterestedQueue().get(0).getName());
		updated = postRepository.save(updated);
		System.out.println(updated.getInterestedQueue().get(0).getName());
		System.out.println((postRepository.findOne(updated.getId())).get().getInterestedQueue().size());
		PostDTO dto = postTransformer.ConvertPostToPostDTO(updated);
		System.out.println("dto " + dto.getInterestedQueue().size());
		return postTransformer.ConvertPostToPostDTO(updated);
	}

	private Post FindPostById(String id) {
		Optional<Post> result = postRepository.findOne(id);
		//TODO: IMPLEMENT EXCEPTION AND ERROR HANDLING
		return result.get();
	}
	

}
