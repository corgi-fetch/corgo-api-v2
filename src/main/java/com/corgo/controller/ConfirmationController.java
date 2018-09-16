package com.corgo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.corgo.DTO.PostDTO;
import com.corgo.DTO.UserStubDTO;
import com.corgo.service.PostService;

@RestController
@RequestMapping("/api/{userId}/confirmation")
public class ConfirmationController {
	private final PostService postService;

	@Autowired
	ConfirmationController (@Lazy PostService postService) {
		this.postService = postService;
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.POST)
	PostDTO serviceReceived(@PathVariable("userId") String userId, @PathVariable("id") String id) {
		System.out.println("we r here");
		PostDTO post = postService.findById(id);
		post.setServiceReceived(false);
		post.setServiceGiven(false);
		post.setState(3);
		return postService.update(post, false);
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	PostDTO serviceGiven(@PathVariable("userId") String userId, @PathVariable("id") String id) {
		System.out.println("we r here");
		PostDTO post = postService.findById(id);
		post.setServiceGiven(true);
		return postService.update(post, false);
	}
}
