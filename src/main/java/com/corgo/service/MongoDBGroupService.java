package com.corgo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.corgo.DTO.GroupDTO;
import com.corgo.DTO.GroupStubDTO;
import com.corgo.DTO.PostDTO;
import com.corgo.DTO.PostStubDTO;
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
		
		
		return groupTransformer.ConvertGroupToGroupDTO(persisted);
		
	}
	
	@Override
	public GroupDTO updateWithNewPost(Post persisted, String groupId) {
		//Get Group from groupId
		GroupDTO group = findById(groupId);
		
		//Get currentPosts from the group and add persisted post to it
		List<PostStubDTO> currentPosts = group.getPosts();
		currentPosts.add(postStubTransformer.ConvertPostToPostStubDTO(persisted));
		
		//Set group currentPosts
		group.setPosts(currentPosts);
		
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
		Group found = groupRepository.findOne(id).get();
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

}
