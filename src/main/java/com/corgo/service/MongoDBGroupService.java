package com.corgo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.corgo.DTO.GroupDTO;
import com.corgo.DTO.GroupStubDTO;
import com.corgo.DTO.PostDTO;
import com.corgo.DTO.PostStubDTO;
import com.corgo.DTO.PushMessage;
import com.corgo.DTO.PushTickets;
import com.corgo.DTO.UserDTO;
import com.corgo.DTO.UserStubDTO;
import com.corgo.model.Group;
import com.corgo.model.Post;
import com.corgo.repository.GroupRepository;
import com.corgo.repository.PostRepository;
import com.corgo.transformer.GroupTransformer;
import com.corgo.transformer.PostStubTransformer;
import com.corgo.transformer.PostTransformer;
import com.corgo.transformer.UserTransformer;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MongoDBGroupService implements GroupService {

	private final PostRepository postRepository;
	private final UserTransformer userTransformer;
	private final PostTransformer postTransformer;
	private final UserService userService;
	private final GroupRepository groupRepository;
	private final GroupTransformer groupTransformer;
	private final PostStubTransformer postStubTransformer;
	
	
	@Autowired
    MongoDBGroupService(PostRepository postRepository, UserTransformer userTransformer, PostTransformer postTransformer, UserService userService, GroupRepository groupRepository, GroupTransformer groupTransformer, PostStubTransformer postStubTransformer) {
        this.postRepository = postRepository;
        this.userTransformer = userTransformer;
        this.postTransformer = postTransformer;
        this.groupRepository = groupRepository;
        this.userService = userService;
        this.groupTransformer = groupTransformer;
        this.postStubTransformer = postStubTransformer;
    }
	
	@Override
	public GroupDTO create(GroupDTO group) {
		// TODO Auto-generated method stub
		System.out.println("are we here");
		
		System.out.println("group dto " + group.getUsers().get(0).getUserId());
		
		Group persisted = new Group();
		persisted.setDescription(group.getDescription());
		persisted.setId(group.getId());
		persisted.setInvited(group.getInvited());
		persisted.setTitle(group.getTitle());
		persisted.setPosts(postStubTransformer.ConvertListOfPostStubDTOToPostStubs(group.getPosts()));
		persisted.setUsers(userTransformer.ConvertListOfUserStubDTOToUserStubs(group.getUsers()));
		
		System.out.println("prior save " + persisted.toString());
		persisted = groupRepository.save(persisted);
		
		System.out.println("after save " + persisted.toString());
		
		GroupDTO groupUpdated = groupTransformer.ConvertGroupToGroupDTO(persisted);
		
		for (UserStubDTO u : group.getUsers()) {
			System.out.println("this is a userId " + u.getUserId());
			UserDTO dto = userService.findByUserId(u.getUserId());
			List<GroupStubDTO> groups = dto.getGroups();
			if (groups != null) {
				System.out.println("are we in this one null check");
				groups.add(groupTransformer.ConvertGroupDTOToGroupStubDTO(groupUpdated));
			} else {
				System.out.println("are we in this other one null check");
				groups = new ArrayList<>();
				groups.add(groupTransformer.ConvertGroupDTOToGroupStubDTO(groupUpdated));
			}
			dto.setGroups(groups);
			System.out.println("dto that is saved " + groups);
			userService.update(dto);
		}
		
		// Push notification!
		String url = "https://exp.host/--/api/v2/push/send";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		// Testing Push Notifications
		RestTemplate restTemplate = new RestTemplate();
		List<PushMessage> listMsg = new ArrayList<>();
		
		for (UserStubDTO user : group.getUsers()) {
			PushMessage msg = new PushMessage();
			
			msg.setTo(user.getPushToken());
			msg.setTitle("You've been added to " + group.getTitle());
			msg.setBody("Start posting and answering requests!");
			msg.setChannelId("corgo-notifications");
			listMsg.add(msg);
		}
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

	
		return groupTransformer.ConvertGroupToGroupDTO(persisted);
		
	}
	
	@Override
	public GroupDTO updateWithNewPost(Post persisted, String groupId) {
		//Get Group from groupId
		GroupDTO group = findById(groupId);
		
		//Get currentPosts from the group and add persisted post to it
		List<PostStubDTO> currentPosts = group.getPosts();
		currentPosts.add(postStubTransformer.ConvertPostToPostStubDTO(persisted));
		
		System.out.println("persisted in the grouptransformer update with new post " + persisted.getState());
		//Set group currentPosts
		group.setPosts(currentPosts);
		
		//Update groups
		group = update(group);
		
		
		// Push notification!
		String url = "https://exp.host/--/api/v2/push/send";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		// Testing Push Notifications
		RestTemplate restTemplate = new RestTemplate();
		List<PushMessage> listMsg = new ArrayList<>();
		
		for (UserStubDTO user : group.getUsers()) {
			PushMessage msg = new PushMessage();
			
			msg.setTo(user.getPushToken());
			msg.setTitle("Request posted in " + group.getTitle());
			msg.setBody(persisted.getOwner().getName() + " posted a request!");
			msg.setChannelId("corgo-notifications");
			listMsg.add(msg);
		}
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
		
		System.out.println("tickets here " + tickets);
		return group;
	}
	
	@Override
	public GroupDTO updateWithExistingPost(Post persisted, String groupId) {
		//Get Group from groupId
		GroupDTO group = findById(groupId);
		
		//Get currentPosts from the group and add persisted post to it
		List<PostStubDTO> currentPosts = group.getPosts();
		List<PostStubDTO> updatedList = findAndReplace(currentPosts, postStubTransformer.ConvertPostToPostStubDTO(persisted));
		
		
		
		// System.out.println("persisted in the grouptransformer update with new post " + persisted.getState());
		//Set group currentPosts
		group.setPosts(updatedList);
		
		//Update groups
		group = update(group);
		

		
		return group;
	}
	
	

	@Override
	public GroupDTO delete(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GroupDTO> findAll() {
		// TODO Auto-generated method stub
		List<Group> listDTO = groupRepository.findAll();
		System.out.println("we are in group find all " + listDTO);
		return groupTransformer.ConvertListOfGroupToGroupDTO(listDTO);
	}

	@Override
	public GroupDTO findById(String id) {
		// TODO Auto-generated method stub
		Group found = groupRepository.findById(id).get();
		return groupTransformer.ConvertGroupToGroupDTO(found);
	}

	@Override
	public GroupDTO update(GroupDTO group) {
		// TODO Auto-generated method stub
		Group updated = groupRepository.save(groupTransformer.ConvertGroupDTOToGroup(group));
		System.out.println("after save in update " + updated.getPosts().toString());
		return groupTransformer.ConvertGroupToGroupDTO(updated);
	}
	
	@Override
	public GroupDTO addUser(UserDTO user) {
		return null;
	}
	
	List<PostStubDTO> findAndReplace(List<PostStubDTO> listStub, PostStubDTO stubDTO) {
		List<PostStubDTO> toReturn = new ArrayList<>();
	    for(PostStubDTO stub : listStub) {
	    	PostStubDTO toAdd = new PostStubDTO();
	        if(stub.getId().equals(stubDTO.getId())) {
	        	toAdd.setDate(stubDTO.getDate());
	        	toAdd.setDescription(stubDTO.getDescription());
	        	toAdd.setGroupId(stubDTO.getGroupId());
	        	toAdd.setId(stubDTO.getId());
	        	toAdd.setOwner(stubDTO.getOwner());
	        	toAdd.setPayment(stubDTO.getPayment());
	        	toAdd.setState(stubDTO.getState());
	        	toAdd.setTitle(stubDTO.getTitle());
	        } else {
	        	toAdd.setDate(stub.getDate());
	        	toAdd.setDescription(stub.getDescription());
	        	toAdd.setGroupId(stub.getGroupId());
	        	toAdd.setId(stub.getId());
	        	toAdd.setOwner(stub.getOwner());
	        	toAdd.setPayment(stub.getPayment());
	        	toAdd.setState(stub.getState());
	        	toAdd.setTitle(stub.getTitle());
	        }
	        
	        toReturn.add(toAdd);
	    }
	    
	    return toReturn;
	}

}
