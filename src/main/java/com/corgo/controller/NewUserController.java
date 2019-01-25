package com.corgo.controller;


import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.oauth2.provider.OAuth2Authentication;
//import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
//import org.springframework.social.facebook.api.Facebook;
//import org.springframework.social.facebook.api.User;
//import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.corgo.DTO.UserDTO;
import com.corgo.service.UserService;

@RestController
@RequestMapping("/api/newuser#_=_")
public class NewUserController {
	
	@Autowired
	UserService service;
	
	NewUserController(UserService service) {
		this.service = service;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	UserDTO newCreate() {
		System.out.println("We are in this get right here");
		return null;
	}
	
	@RequestMapping(method = RequestMethod.POST)	
	UserDTO create(@RequestParam("userId") String userId, @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestParam("email") String email) {
		System.out.println("We are creating a new user right now");
		
		UserDTO partialUser = new UserDTO();
		partialUser.setUserId(userId);
		partialUser.setName(firstName + " " + lastName);
		partialUser.setEmail(email);
		
		
		//System.out.println(partial);
		return service.create(partialUser);
	}

}
