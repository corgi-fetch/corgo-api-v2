package com.corgo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.corgo.DTO.GroupDTO;
import com.corgo.DTO.UserDTO;
import com.corgo.model.Group;
import com.corgo.repository.GroupRepository;
import com.corgo.repository.PostRepository;
import com.corgo.transformer.GroupTransformer;
import com.corgo.transformer.PostTransformer;
import com.corgo.transformer.UserTransformer;

@Service
public class MongoDBGroupService implements GroupService {

	private final PostRepository postRepository;
	private final UserTransformer userTransformer;
	private final PostTransformer postTransformer;
	private final UserService userService;
	private final GroupRepository groupRepository;
	private final GroupTransformer groupTransformer;
	
	
	@Autowired
    MongoDBGroupService(PostRepository postRepository, UserTransformer userTransformer, PostTransformer postTransformer, UserService userService, GroupRepository groupRepository, GroupTransformer groupTransformer) {
        this.postRepository = postRepository;
        this.userTransformer = userTransformer;
        this.postTransformer = postTransformer;
        this.groupRepository = groupRepository;
        this.userService = userService;
        this.groupTransformer = groupTransformer;
    }
	
	@Override
	public GroupDTO create(GroupDTO group) {
		// TODO Auto-generated method stub
		Group persisted = new Group();
		persisted.setDescription(group.getDescription());
		persisted.setId(group.getId());
		persisted.setInvited(group.getInvited());
		persisted.setName(group.getName());
		persisted.setPosts(postTransformer.ConvertListOfPostDTOToPost(group.getPosts()));
		persisted.setUsers(userTransformer.ConvertListOfUserDTOToUser(group.getUsers()));
		
		persisted = groupRepository.save(persisted);
		
		return groupTransformer.ConvertGroupToGroupDTO(persisted);
		
	}

	@Override
	public GroupDTO delete(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GroupDTO> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GroupDTO findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GroupDTO update(GroupDTO post) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public GroupDTO addUser(UserDTO user) {
		return null;
	}

}
