package com.corgo.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

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

import com.corgo.DTO.GroupDTO;
import com.corgo.DTO.PostDTO;
import com.corgo.DTO.UserDTO;
import com.corgo.service.*;

@RestController
@RequestMapping("/api/{userId}/group")
public class GroupController {
	private final GroupService groupService;
	
	@Autowired
	GroupController (GroupService groupService) {
		this.groupService = groupService;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	GroupDTO create(@PathVariable("userId") String userId, @RequestBody @Valid GroupDTO groupEntry) {
		GroupDTO created = groupService.create(groupEntry);
		return created;
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseBody
	GroupDTO delete(@PathVariable("userId") String userId, @PathVariable("id") String id) {
		GroupDTO created = groupService.delete(id);
		return created;
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	GroupDTO findById(@PathVariable("userId") String userId, @PathVariable("id") String id) {
		return groupService.findById(id);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	List<GroupDTO> findAll(@PathVariable("userId") String userId, @ModelAttribute Principal principal) {
		System.out.println(principal.getName());
		return groupService.findAll();
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	GroupDTO update(@PathVariable("userId") String userId, @RequestBody @Valid GroupDTO postEntry) {
		return groupService.update(postEntry);
	}
	
	
	
	@RequestMapping(method = RequestMethod.PUT)
	GroupDTO addUsers(@PathVariable("userId") String userId, @PathVariable("id") String id, @RequestBody @Valid UserDTO user) {
		GroupDTO group = groupService.findById(id);
		return groupService.addUser(user);
	}
	

	
	
}
