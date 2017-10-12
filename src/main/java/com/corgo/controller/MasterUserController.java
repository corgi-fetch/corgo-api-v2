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
import com.corgo.DTO.*;

@RestController
@RequestMapping("/api/master/user")
public class MasterUserController {
	
	private final UserService service;
	
	@Autowired
	MasterUserController (UserService service) {
		this.service = service;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	UserDTO create(@RequestBody @Valid UserDTO postEntry) {
		return service.create(postEntry);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	List<UserDTO> findAll(@ModelAttribute Principal principal) {
		System.out.println(principal);
		return service.findAll();
	}
	
	@RequestMapping(value= "{id}", method = RequestMethod.GET)
	UserDTO findById(@PathVariable("id") String id) {
		return service.findById(id);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	UserDTO update(@RequestBody @Valid UserDTO postEntry) {
		return service.update(postEntry);
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	@ResponseBody
	UserDTO delete(@PathVariable("id") String id) {
		return service.delete(id);
	}
}
