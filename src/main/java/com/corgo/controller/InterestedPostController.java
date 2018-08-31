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
@RequestMapping("/api/{userId}/interestedpost")
public class InterestedPostController {
	
	private final PostService postService;
	private final UserService userService;
	private final UserTransformer userTransformer;
	
	@Autowired
	InterestedPostController (@Lazy PostService postService, UserService userService, UserTransformer userTransformer) {
		this.postService = postService;
		this.userService = userService;
		this.userTransformer = userTransformer;
	}
	
	
//	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
//	PostDTO update(@PathVariable("userId") String userId, @PathVariable("id") String id, @RequestBody @Valid PostDTO postEntry) {
//		System.out.println("we r here update");
//		return postService.update(postEntry);
//	}
	
//	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
//	PostDTO addSelectedUserId(@PathVariable("userId") String userId, @PathVariable("id") String id, @RequestBody @Valid UserDTO selectedUser) {
//		PostDTO post = postService.findById(id);
//		post.setSelectedUserId(selectedUser.getUserId());
//		return postService.update(post);
//	}
	
//	@RequestMapping(value = "{id}", method = RequestMethod.POST)
//	PostDTO addResponderUserId(@PathVariable("userId") String userId, @PathVariable("id") String id, @RequestBody @Valid UserDTO responder) {
//		PostDTO post = postService.findById(id);
//		post.setResponderUserId(responder.getUserId());
//		return postService.update(post);
//	}
//	
//	@RequestMapping(value = "{id}", method = RequestMethod.GET)
//	PostDTO addServiceRecieved(@PathVariable("userId") String userId, @PathVariable("id") String id) {
//		PostDTO post = postService.findById(id);
//		post.setServiceReceived(true);
//		return postService.update(post);
//	}
//	
//	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
//	PostDTO addServiceGiven(@PathVariable("userId") String userId, @PathVariable("id") String id) {
//		PostDTO post = postService.findById(id);
//		post.setServiceGiven(true);
//		return postService.update(post);
//	}
	
	
}

