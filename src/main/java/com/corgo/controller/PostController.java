package com.corgo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.corgo.service.*;
import com.corgo.transformer.UserTransformer;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.corgo.DTO.*;

@RestController
@RequestMapping("/api/{userId}/post")
public class PostController {
	
	private final PostService postService;
	
	@Autowired
	PostController (@Lazy PostService postService) {
		this.postService = postService;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	PostDTO create(@PathVariable("userId") String userId, @RequestBody @Valid PostDTO postEntry) {
		System.out.println("POST create " + postEntry.getGroupId());
		
		//Creating a Post
		PostDTO created = postService.create(postEntry);		
		return created;
	}
	
//	@RequestMapping(value="{id}", method = RequestMethod.DELETE)
//	@ResponseBody
//	PostDTO delete(@PathVariable("userId") String userId, @PathVariable("id") String id) {
//		return postService.delete(id);
//	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	PostDTO findById(@PathVariable("userId") String userId, @PathVariable("id") String id) {
		return postService.findById(id);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	List<PostStubDTO> findAll(@PathVariable("userId") String userId, @ModelAttribute Principal principal) {
//		System.out.println(principal.getName());
		return postService.findAll();
	}
	
//	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
//	PostDTO update(@PathVariable("userId") String userId, @PathVariable("id") String id, @RequestBody @Valid PostDTO postEntry) {
//		System.out.println("we r here update");
//		return postService.update(postEntry);
//	}
	
	
	//Adding to Interested Queue
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	PostDTO addInterestedQueue(@PathVariable("userId") String userId, @PathVariable("id") String id, @RequestBody @Valid UserStubDTO interestedUser) {
		System.out.println("we r here");
		PostDTO post = postService.findById(id);
		List<UserStubDTO> interestedQueue = post.getInterestedQueue();
		System.out.println(interestedQueue);
		if(interestedQueue == null) {
			System.out.println("inside null check");
			interestedQueue = new ArrayList<>();
		}
		
		interestedQueue.add(interestedUser);
		post.setInterestedQueue(interestedQueue);
		System.out.println(post.getInterestedQueue().size());
		
		// Push notification!
		String url = "https://exp.host/--/api/v2/push/send";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		RestTemplate restTemplate = new RestTemplate();
		List<PushMessage> listMsg = new ArrayList<>();
		
		
		PushMessage msg = new PushMessage();
		
		msg.setTo(post.getOwner().getPushToken());
		msg.setTitle(interestedUser.getName() + " marked themselves as interested for your post " + post.getTitle());
		msg.setBody("Feel free to accept them as requester!");
		msg.setChannelId("corgo-notifications");
		listMsg.add(msg);
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		objectMapper.setSerializationInclusion(Include.NON_EMPTY); 
		String msgJsonList = "";
		try {
			msgJsonList = objectMapper.writeValueAsString(listMsg);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("msgJsonList " + msgJsonList);
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(msgJsonList, headers);
		PushTickets tickets = restTemplate.postForEntity(url, httpEntity, PushTickets.class).getBody();
		
		return postService.update(post, false);
	}
	
	
	// Selecting Interested User!
	@RequestMapping(value = "{id}", method = RequestMethod.POST)
	PostDTO selectUser(@PathVariable("userId") String userId, @PathVariable("id") String id, @RequestBody @Valid UserStubDTO interestedUser) {
		System.out.println("we r here");
		PostDTO post = postService.findById(id);
		post.setResponderUserId(interestedUser);
		post.setInterestedQueue(new ArrayList<>());
		post.setState(2);
		
		// Push notification!
		String url = "https://exp.host/--/api/v2/push/send";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		RestTemplate restTemplate = new RestTemplate();
		List<PushMessage> listMsg = new ArrayList<>();
		
		
		PushMessage msg = new PushMessage();
		
		msg.setTo(interestedUser.getPushToken());
		msg.setTitle("Yay! " + post.getOwner().getName() + " selected you for their post " + post.getTitle());
		msg.setBody("Thanks for being willing to help " + post.getOwner().getName() + " out!");
		msg.setChannelId("corgo-notifications");
		listMsg.add(msg);
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		objectMapper.setSerializationInclusion(Include.NON_EMPTY); 
		String msgJsonList = "";
		try {
			msgJsonList = objectMapper.writeValueAsString(listMsg);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("msgJsonList " + msgJsonList);
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(msgJsonList, headers);
		PushTickets tickets = restTemplate.postForEntity(url, httpEntity, PushTickets.class).getBody();
				
				
//		System.out.println(post.getInterestedQueue().size());
		return postService.update(post, false);
	}
	
	
	
	
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	PostDTO removeInterestedQueue(@PathVariable("userId") String userId, @PathVariable("id") String id, @RequestBody @Valid UserStubDTO interestedUser) {
		System.out.println("we r here");
		PostDTO post = postService.findById(id);
		List<UserStubDTO> interestedQueue = post.getInterestedQueue();
		System.out.println(interestedQueue);
		if(interestedQueue == null) {
			System.out.println("inside null check");
			interestedQueue = new ArrayList<>();
		}
		
		System.out.println("before remove" + interestedQueue.size());
		System.out.println(interestedQueue.indexOf(interestedUser));
		System.out.println(interestedQueue.get(0).getUserId());
		interestedQueue.remove(interestedUser);
		System.out.println("after remove" + interestedQueue.size());
		post.setInterestedQueue(interestedQueue);
		System.out.println(post.getInterestedQueue().size());
		
		
		// Push notification!
		String url = "https://exp.host/--/api/v2/push/send";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		RestTemplate restTemplate = new RestTemplate();
		List<PushMessage> listMsg = new ArrayList<>();
		
		
		PushMessage msg = new PushMessage();
		
		msg.setTo(post.getOwner().getPushToken());
		msg.setTitle(interestedUser.getName() + " removed themselves as interested for your post " + post.getTitle());
		msg.setBody("Don't worry, there's more fish in the sea!");
		msg.setChannelId("corgo-notifications");
		msg.setPriority("high");
		listMsg.add(msg);
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		objectMapper.setSerializationInclusion(Include.NON_EMPTY); 
		String msgJsonList = "";
		try {
			msgJsonList = objectMapper.writeValueAsString(listMsg);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("msgJsonList " + msgJsonList);
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(msgJsonList, headers);
		PushTickets tickets = restTemplate.postForEntity(url, httpEntity, PushTickets.class).getBody();
		
		return postService.update(post, false);
	}
}
