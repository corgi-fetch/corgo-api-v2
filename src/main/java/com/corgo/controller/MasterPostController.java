package com.corgo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.security.Principal;

import com.corgo.service.*;
import com.corgo.DTO.*;

@RestController
@RequestMapping("/api/master/post")
public class MasterPostController {
	
	private final PostService service;
	
	@Autowired
	MasterPostController (PostService service) {
		this.service = service;
		
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	PostDTO create(@RequestBody @Valid PostDTO postEntry) {
		return service.create(postEntry);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	List<PostStubDTO> findAll() {
		return service.findAll();
	}	
	
	@RequestMapping(value= "{id}", method = RequestMethod.GET)
	PostDTO findById(@PathVariable("id") String id) {
		return service.findById(id);
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	PostDTO update(@RequestBody @Valid PostDTO postEntry) {
		return service.update(postEntry, false);
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	@ResponseBody
	PostDTO delete(@PathVariable("id") String id) {
		return service.delete(id);
	}

}
