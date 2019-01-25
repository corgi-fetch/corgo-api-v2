package com.corgo.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.social.facebook.api.User;


 
@RestController
@RequestMapping("/api/login/{accessToken}")
public class LoginController {
	
	@RequestMapping(method = RequestMethod.GET)
	String facebookLogin(@PathVariable("accessToken") String accessToken) {
		Facebook facebook = new FacebookTemplate(accessToken);
		String [] fields = { "id", "email",  "first_name", "last_name" };
		User userProfile = facebook.fetchObject("me", User.class, fields);
		String token;
		try {
		    Algorithm algorithm = Algorithm.HMAC256("secret");
		    token = JWT.create()
		        .withClaim("userId", userProfile.getId())
		        .sign(algorithm);
		    
		    return token;
		    
		} catch (JWTCreationException exception){
		    //Invalid Signing configuration / Couldn't convert Claims.
			System.out.println(exception.getMessage());
			return exception.getMessage();
		}

	}
	
	@RequestMapping(method = RequestMethod.POST)
	String validateToken(@PathVariable("accessToken") String accessToken) {
		
		String userId;
		try {
		    DecodedJWT jwt = JWT.decode(accessToken);
		    Claim userIdClaim = jwt.getClaim("userId");
		    userId = userIdClaim.asString();
		} catch (JWTDecodeException exception){
		    //Invalid token
			userId = "Invalid";
		}
		return userId;
	}
}
