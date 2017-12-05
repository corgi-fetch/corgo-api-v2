package com.corgo.transformer;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.corgo.DTO.GroupDTO;
import com.corgo.model.Group;
import com.corgo.model.Post;
import com.corgo.repository.GroupRepository;

@Component
public class GroupTransformerImpl implements GroupTransformer {
	
	private final UserTransformer userTransformer;
	private final PostTransformer postTransformer;
	private final GroupRepository groupRepository;
	
	@Autowired
	GroupTransformerImpl(@Lazy UserTransformer userTransformer, PostTransformer postTransformer, GroupRepository groupRepository) {
		this.userTransformer = userTransformer;
		this.postTransformer = postTransformer;
		this.groupRepository = groupRepository;
	}
	
	public GroupDTO ConvertGroupToGroupDTO(Group model) { 
		System.out.println("before transform " + model.getPosts().toString());
		
		GroupDTO dto = new GroupDTO();
		dto.setId(model.getId());
		dto.setDescription(model.getDescription());
		dto.setInvited(model.getInvited());
		dto.setName(model.getName());
		dto.setPosts(postTransformer.ConvertListOfPostsToPostDTO(model.getPosts()));
		dto.setUsers(userTransformer.ConvertListOfUsersToUserStubDTO(model.getUsers()));
		
		System.out.println("after transform " + dto.getPosts().toString());
		
		return dto;
	}
	public List<GroupDTO> ConvertListOfGroupToGroupDTO(List<Group> model) { 
		if (model == null || model.size() == 0) {
			return new ArrayList<GroupDTO>();
		} else {
			return model.stream()
					.map(this::ConvertGroupToGroupDTO)
					.collect(toList());
		}
	}
	
	public Group ConvertGroupDTOToGroup(GroupDTO model) { 
		Group toReturn = groupRepository.findOne(model.getId()).get();
		toReturn.setInvited(model.getInvited());
		toReturn.setDescription(model.getDescription());
		toReturn.setName(model.getName());
		toReturn.setPosts(postTransformer.ConvertListOfPostDTOToPost(model.getPosts()));
		toReturn.setUsers(userTransformer.ConvertListOfUserStubDTOToUser(model.getUsers()));
		return toReturn;
	}
	
	public List<Group> ConvertListOfGroupDTOToGroup(List<GroupDTO> model) {  
		if (model == null || model.size() == 0) {
			return new ArrayList<Group>();
		} else {
			return model.stream()
					.map(this::ConvertGroupDTOToGroup)
					.collect(toList());
		}
	}
}
