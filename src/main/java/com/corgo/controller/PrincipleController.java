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

import java.security.Principal;
import java.util.List;

import com.corgo.service.*;
import com.corgo.DTO.*;

@RestController
@RequestMapping("/api/master/principal")
public class PrincipleController {
	
	@RequestMapping(method = RequestMethod.GET)
	public String user(Principal principal) {
		return principal.getName();
	}
}
