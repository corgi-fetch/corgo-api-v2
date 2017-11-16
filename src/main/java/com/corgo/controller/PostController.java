package com.corgo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import com.corgo.service.*;
import com.corgo.transformer.UserTransformer;
import com.corgo.DTO.*;

@RestController
@RequestMapping("/api/{userId}/post")
public class PostController {
	
	private final PostService postService;
	private final UserService userService;
	private final UserTransformer userTransformer;
	
	@Autowired
	PostController (@Lazy PostService postService, UserService userService, UserTransformer userTransformer) {
		this.postService = postService;
		this.userService = userService;
		this.userTransformer = userTransformer;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	PostDTO create(@PathVariable("userId") String userId, @RequestBody @Valid PostDTO postEntry) {
		System.out.println("POST create");
		PostDTO created = postService.create(postEntry);		
		return created;
	}
	
	@RequestMapping(value="{id}", method = RequestMethod.DELETE)
	@ResponseBody
	PostDTO delete(@PathVariable("userId") String userId, @PathVariable("id") String id) {
		return postService.delete(id);
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	PostDTO findById(@PathVariable("userId") String userId, @PathVariable("id") String id) {
		return postService.findById(id);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	List<PostDTO> findAll(@PathVariable("userId") String userId, @ModelAttribute Principal principal) {
		System.out.println(principal.getName());
		return postService.findAll();
	}
	
//	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
//	PostDTO update(@PathVariable("userId") String userId, @PathVariable("id") String id, @RequestBody @Valid PostDTO postEntry) {
//		System.out.println("we r here update");
//		return postService.update(postEntry);
//	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	PostDTO addInterestedQueue(@PathVariable("userId") String userId, @PathVariable("id") String id, @RequestBody @Valid UserDTO interestedUser) {
		System.out.println("we r here");
		PostDTO post = postService.findById(id);
		List<UserStubDTO> interestedQueue = post.getInterestedQueue();
		System.out.println(interestedQueue);
		if(interestedQueue == null) {
			System.out.println("inside null check");
			interestedQueue = new ArrayList<>();
		}
		UserStubDTO stub = userTransformer.ConvertUserDTOToUserStubDTO(interestedUser);
		interestedQueue.add(stub);
		post.setInterestedQueue(interestedQueue);
		System.out.println(post.getInterestedQueue().size());
		return postService.update(post);
	}
	
	
}
