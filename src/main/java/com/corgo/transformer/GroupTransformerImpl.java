package com.corgo.transformer;

import static java.util.stream.Collectors.toList;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.corgo.DTO.GroupDTO;
import com.corgo.DTO.GroupStubDTO;
import com.corgo.DTO.PostDTO;
import com.corgo.model.Group;
import com.corgo.model.GroupStub;
import com.corgo.model.Post;
import com.corgo.repository.GroupRepository;

@Component
public class GroupTransformerImpl implements GroupTransformer {
	
	private final UserTransformer userTransformer;
	private final PostStubTransformer postStubTransformer;
//	private final PostTransformer postTransformer;
	private final GroupRepository groupRepository;
	
	@Autowired
	GroupTransformerImpl(@Lazy UserTransformer userTransformer, PostStubTransformer postStubTransformer, GroupRepository groupRepository, PostTransformer postTransformer) {
		this.userTransformer = userTransformer;
		this.postStubTransformer = postStubTransformer;
		this.groupRepository = groupRepository;
//		this.postTransformer = postTransformer;
	}
	
	public GroupDTO ConvertGroupToGroupDTO(Group model) { 
//		System.out.println("before transform " + model.getId());
		if (model.getPosts().size() > 0 ) {
			System.out.println("this is model.posts " + model.getPosts().get(0).getId());
		}
		GroupDTO dto = new GroupDTO();
		dto.setId(model.getId());
		dto.setDescription(model.getDescription());
		dto.setInvited(model.getInvited());
		dto.setTitle(model.getTitle());
		dto.setPosts(postStubTransformer.ConvertListOfPostStubsToPostStubDTO(model.getPosts()));
		dto.setUsers(userTransformer.ConvertListOfUserStubsToUserStubDTO(model.getUsers()));
		
//		System.out.println("after transform " + dto.getPosts().get(1).getState());
		
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
		Group toReturn = groupRepository.findById(model.getId()).get();
		toReturn.setInvited(model.getInvited());
		toReturn.setDescription(model.getDescription());
		toReturn.setTitle(model.getTitle());
		toReturn.setPosts(postStubTransformer.ConvertListOfPostStubDTOToPostStubs(model.getPosts()));
		toReturn.setUsers(userTransformer.ConvertListOfUserStubDTOToUserStubs(model.getUsers()));
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

	@Override
	public List<GroupStubDTO> ConvertListOfGroupStubToGroupStubDTO(List<GroupStub> stub) {
		if (stub == null || stub.size() == 0) {
			return new ArrayList<GroupStubDTO>();
		} else {
			return stub.stream()
					.map(this::ConvertGroupStubToGroupStubDTO)
					.collect(toList());
		}
		
	}

	@Override
	public List<GroupStub> ConvertListOfGroupStubDTOToGroupStub(List<GroupStubDTO> stubDTO) {
		if (stubDTO == null || stubDTO.size() == 0) {
			return new ArrayList<GroupStub>();
		} else {
			return stubDTO.stream()
					.map(this::ConvertGroupStubDTOToGroupStub)
					.collect(toList());
		}
	}

	@Override
	public GroupStub ConvertGroupStubDTOToGroupStub(GroupStubDTO stub) {
		GroupStub toReturn = new GroupStub();
		toReturn.setDescription(stub.getDescription());
		toReturn.setId(stub.getId());
		toReturn.setTitle(stub.getTitle());
		toReturn.setUsers(userTransformer.ConvertListOfUserStubDTOToUserStubs(stub.getUsers()));
		return toReturn;
	}

	@Override
	public GroupStubDTO ConvertGroupStubToGroupStubDTO(GroupStub stubDTO) {
		GroupStubDTO toReturn = new GroupStubDTO();
		toReturn.setDescription(stubDTO.getDescription());
		toReturn.setId(stubDTO.getId());
		toReturn.setTitle(stubDTO.getTitle());
		toReturn.setUsers(userTransformer.ConvertListOfUserStubsToUserStubDTO(stubDTO.getUsers()));
		return toReturn;
	}

	@Override
	public GroupStubDTO ConvertGroupDTOToGroupStubDTO(GroupDTO dto) {
		GroupStubDTO toReturn = new GroupStubDTO();
		toReturn.setDescription(dto.getDescription());
		toReturn.setId(dto.getId());
		toReturn.setTitle(dto.getTitle());
		toReturn.setUsers(dto.getUsers());
		return toReturn;
	}
	
}
