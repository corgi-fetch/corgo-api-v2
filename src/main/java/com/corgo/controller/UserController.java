package com.corgo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.corgo.transformer.UserTransformer;
import com.corgo.DTO.*;

@RestController
@RequestMapping("/api/{userId}/user")
public class UserController {
	
	private final UserService service;
	private final UserTransformer userTransformer;
	
	@Autowired
	UserController (UserService service, UserTransformer userTransformer) {
		this.userTransformer = userTransformer;
		this.service = service;
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseBody
	UserDTO delete(@PathVariable("userId") String userId) {
		return service.delete(userId);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	UserDTO findByUserId(@PathVariable("userId") String userId, @ModelAttribute Principal principal) {
//		System.out.println("we here");
//		System.out.println(userId);
//		System.out.println(principal.getName());
//		if (!userId.equals(principal.getName().toString())) {
//			return null;
//		}
		
		return service.findByUserId(userId);
	}
	
	
	
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	UserDTO update(@PathVariable("userId") String userId, @RequestBody @Valid UserDTO postEntry) {
		return service.update(postEntry);
	}
	
	@RequestMapping(value = "{otherUserId}", method = RequestMethod.GET)
	UserStubDTO findOtherById(@PathVariable("userId") String userId, @PathVariable("otherUserId") String otherUserId) {
		return userTransformer.ConvertUserDTOToUserStubDTO(service.findByUserId(otherUserId));
	}
}
