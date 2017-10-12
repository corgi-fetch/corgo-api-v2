package com.corgo.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;

import com.corgo.service.*;
import com.corgo.DTO.*;

@RestController
@RequestMapping("/api/{userId}/post")
public class PostController {
	
	private final PostService postService;
	private final UserService userService;
	
	@Autowired
	PostController (PostService postService, UserService userService) {
		this.postService = postService;
		this.userService = userService;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	PostDTO create(@PathVariable("userId") String userId, @RequestBody @Valid PostDTO postEntry) {
		PostDTO created = postService.create(postEntry);
		List<PostDTO> userPosts = userService.findByUserId(userId).getCurrentPosts();
		userPosts.add(created);
		UserDTO currentUser = userService.findByUserId(userId);
		currentUser.setCurrentPosts(userPosts);
		userService.update(currentUser);
		return created;
	}
	
	@RequestMapping(value="{id}", method = RequestMethod.DELETE)
	@ResponseBody
	PostDTO delete(@PathVariable("userId") String userId, @PathVariable("id") String id) {
		if (postService.findById(id).isServiceGiven() && postService.findById(id).isServiceReceived()) {
			return null;
		} else {
			PostDTO toDelete = postService.findById(id);
			List<PostDTO> userPosts = userService.findByUserId(userId).getPostHistory();
			userPosts.remove(postService.findById(id));
		}
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
	
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	PostDTO update(@PathVariable("userId") String userId, @RequestBody @Valid PostDTO postEntry) {
		return postService.update(postEntry);
	}
	
	
}
