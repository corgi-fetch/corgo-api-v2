package com.corgo.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.corgo.DTO.GroupDTO;
import com.corgo.DTO.PostDTO;
import com.corgo.DTO.PushMessage;
import com.corgo.DTO.PushTickets;
import com.corgo.DTO.UserDTO;
import com.corgo.model.PushToken;
import com.corgo.service.*;

@RestController
@RequestMapping("/api/group")
public class GroupController {
	private final GroupService groupService;
	
	@Autowired
	GroupController (GroupService groupService) {
		this.groupService = groupService;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	GroupDTO create(@RequestHeader("Authorization") String userIdToken, @RequestBody @Valid GroupDTO groupEntry) {
		System.out.println("in group create controller");
		String userId = "";
		try {
		    DecodedJWT jwt = JWT.decode(userIdToken);
		    Claim userIdClaim = jwt.getClaim("userId");
		    userId = userIdClaim.asString();
		} catch (JWTDecodeException exception){
		    //Invalid token
			userId = "Invalid";
			throw exception;
		}
		GroupDTO created = groupService.create(groupEntry);
		

		return created;
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseBody
	GroupDTO delete(@RequestHeader("Authorization") String userIdToken, @PathVariable("id") String id) {
		String userId = "";
		try {
		    DecodedJWT jwt = JWT.decode(userIdToken);
		    Claim userIdClaim = jwt.getClaim("userId");
		    userId = userIdClaim.asString();
		} catch (JWTDecodeException exception){
		    //Invalid token
			userId = "Invalid";
			throw exception;
		}
		GroupDTO created = groupService.delete(id);
		return created;
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	GroupDTO findById(@RequestHeader("Authorization") String userIdToken, @PathVariable("id") String id) {
		String userId = "";
		try {
		    DecodedJWT jwt = JWT.decode(userIdToken);
		    Claim userIdClaim = jwt.getClaim("userId");
		    userId = userIdClaim.asString();
		} catch (JWTDecodeException exception){
		    //Invalid token
			userId = "Invalid";
			throw exception;
		}
		return groupService.findById(id);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	List<GroupDTO> findAll(@RequestHeader("Authorization") String userIdToken, @ModelAttribute Principal principal) {
		//System.out.println(principal.getName());
		System.out.println(userIdToken);
		String userId = "";
		try {
		    DecodedJWT jwt = JWT.decode(userIdToken);
		    Claim userIdClaim = jwt.getClaim("userId");
		    userId = userIdClaim.asString();
		} catch (JWTDecodeException exception){
		    //Invalid token
			userId = "Invalid";
			throw exception;
		}
		System.out.println("we are in find all " + groupService.findAll());
		return groupService.findAll();
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	GroupDTO update(@RequestHeader("Authorization") String userIdToken, @RequestBody @Valid GroupDTO postEntry) {
		String userId = "";
		try {
		    DecodedJWT jwt = JWT.decode(userIdToken);
		    Claim userIdClaim = jwt.getClaim("userId");
		    userId = userIdClaim.asString();
		} catch (JWTDecodeException exception){
		    //Invalid token
			userId = "Invalid";
			throw exception;
		}
		return groupService.update(postEntry);
	}
	
	
	
	@RequestMapping(method = RequestMethod.PUT)
	GroupDTO addUsers(@RequestHeader("Authorization") String userIdToken, @PathVariable("id") String id, @RequestBody @Valid UserDTO user) {
		String userId = "";
		try {
		    DecodedJWT jwt = JWT.decode(userIdToken);
		    Claim userIdClaim = jwt.getClaim("userId");
		    userId = userIdClaim.asString();
		} catch (JWTDecodeException exception){
		    //Invalid token
			userId = "Invalid";
			throw exception;
		}
		GroupDTO group = groupService.findById(id);
		return groupService.addUser(user);
	}

}
