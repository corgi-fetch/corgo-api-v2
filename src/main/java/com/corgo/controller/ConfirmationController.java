package com.corgo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.corgo.DTO.GroupDTO;
import com.corgo.DTO.PostDTO;
import com.corgo.DTO.PostStubDTO;
import com.corgo.DTO.PushMessage;
import com.corgo.DTO.PushTickets;
import com.corgo.DTO.UserDTO;
import com.corgo.DTO.UserStubDTO;
import com.corgo.model.UserStub;
import com.corgo.service.GroupService;
import com.corgo.service.PostService;
import com.corgo.service.UserService;
import com.corgo.transformer.PostStubTransformer;
import com.corgo.transformer.PostTransformer;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/{userId}/confirmation")
public class ConfirmationController {
	private final PostService postService;
	private final UserService userService;
	private final PostTransformer postTransformer;
	private final PostStubTransformer postStubTransformer;
	private final GroupService groupService;

	@Autowired
	ConfirmationController (@Lazy PostService postService, UserService userService, 
			PostTransformer postTransformer, PostStubTransformer postStubTransformer, GroupService groupService) {
		this.postService = postService;
		this.userService = userService;
		this.postTransformer = postTransformer;
		this.postStubTransformer = postStubTransformer;
		this.groupService = groupService;
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.POST)
	PostDTO serviceReceived(@PathVariable("userId") String userId, @PathVariable("id") String id) {
		System.out.println("we r here");
		PostDTO post = postService.findById(id);
		
		if (post.getState() == 2) {
		
			post.setServiceReceived(false);
			post.setServiceGiven(false);
			post.setState(3);
			
			UserDTO responder = userService.findByUserId(post.getResponderUserId().getUserId());
			
			// Push notification!
			String url = "https://exp.host/--/api/v2/push/send";
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	
			RestTemplate restTemplate = new RestTemplate();
			List<PushMessage> listMsg = new ArrayList<>();
			
			
			PushMessage msg = new PushMessage();
			
			msg.setTo(responder.getPushToken());
			msg.setTitle(post.getOwner().getName() + " has confirmed the completion of " + post.getTitle());
			msg.setBody("Congratulations! You're about to get paid!");
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
		} else if (post.getState() == 3) {
			// Payment received!
			System.out.println("POST GET STATE 3 for PAYMENT RECEIVED");
			
			post.setServiceReceived(false);
			post.setServiceGiven(false);
			post.setState(4);
			
			UserDTO responder = userService.findByUserId(post.getResponderUserId().getUserId());
			UserDTO owner = userService.findByUserId(post.getOwner().getUserId());
			GroupDTO group =  groupService.findById(post.getGroupId());
			
			
			// Push notification!
			String url = "https://exp.host/--/api/v2/push/send";
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	
			RestTemplate restTemplate = new RestTemplate();
			List<PushMessage> listMsg = new ArrayList<>();
			
			
			PushMessage msg = new PushMessage();
			
			msg.setTo(responder.getPushToken());
			msg.setTitle(post.getOwner().getName() + " has confirmed your payment for " + post.getTitle());
			msg.setBody("Congratulations on a job well done!");
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
			
			// Update user objects
			PostStubDTO currentPost = postStubTransformer.ConvertPostToPostStubDTO(postTransformer.ConvertPostDTOToPost(post));
			
			List<PostStubDTO> postHistoryOwner = owner.getPostHistory();
			postHistoryOwner.add(currentPost);
			owner.setPostHistory(postHistoryOwner);
			List<PostStubDTO> postHistoryResponder = responder.getPostHistory();
			postHistoryResponder.add(currentPost);
			responder.setPostHistory(postHistoryResponder);
			
			// Remove it from Group object
			List<PostStubDTO> groupPosts = group.getPosts();
			groupPosts.remove(currentPost);
			group.setPosts(groupPosts);
			
			// Update users and groups
			userService.update(owner);
			userService.update(responder);
			groupService.update(group);
			
			
		}
		
		return postService.update(post, false);
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	PostDTO serviceGiven(@PathVariable("userId") String userId, @PathVariable("id") String id) {
		System.out.println("we r here");
		PostDTO post = postService.findById(id);
		post.setServiceGiven(true);
		
		if (post.getState() == 2) {
		
			UserDTO responder = userService.findByUserId(post.getResponderUserId().getUserId());
			
			// Push notification!
			String url = "https://exp.host/--/api/v2/push/send";
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	
			RestTemplate restTemplate = new RestTemplate();
			List<PushMessage> listMsg = new ArrayList<>();
			
			
			PushMessage msg = new PushMessage();
			
			msg.setTo(post.getOwner().getPushToken());
			msg.setTitle(responder.getName() + " has completed your request " + post.getTitle());
			msg.setBody("Confirm that the job has been completed as soon as possible!");
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
		} else if (post.getState() == 3) {
			// Payment given
			
			System.out.println("POST GET STATE 3 for PAYMENT GIVEN");
			UserDTO responder = userService.findByUserId(post.getResponderUserId().getUserId());
			
			// Push notification!
			String url = "https://exp.host/--/api/v2/push/send";
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	
			RestTemplate restTemplate = new RestTemplate();
			List<PushMessage> listMsg = new ArrayList<>();
			
			
			PushMessage msg = new PushMessage();
			
			msg.setTo(post.getResponderUserId().getPushToken());
			msg.setTitle(post.getOwner().getName() + " has venmoed you for completion of " + post.getTitle());
			msg.setBody("Confirm that you've received the payment!");
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
		}
		return postService.update(post, false);
	}
}
