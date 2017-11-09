package com.corgo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.corgo.DTO.GroupDTO;
import com.corgo.repository.PostRepository;
import com.corgo.transformer.PostTransformer;
import com.corgo.transformer.UserTransformer;

@Service
public class MongoDBGroupService implements GroupService {

	private final PostRepository postRepository;
	private final UserTransformer userTransformer;
	private final PostTransformer postTransformer;
	private final UserService userService;
	
	
	@Autowired
    MongoDBGroupService(PostRepository postRepository, UserTransformer userTransformer, PostTransformer postTransformer, UserService userService) {
        this.postRepository = postRepository;
        this.userTransformer = userTransformer;
        this.postTransformer = postTransformer;
        this.userService = userService;
    }
	
	@Override
	public GroupDTO create(GroupDTO post) {
		// TODO Auto-generated method stub
		return null;
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

}
