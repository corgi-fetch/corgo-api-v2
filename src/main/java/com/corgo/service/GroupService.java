package com.corgo.service;

import java.util.List;

import com.corgo.DTO.GroupDTO;
import com.corgo.DTO.UserDTO;
import com.corgo.model.Post;

public interface GroupService {
	GroupDTO create(GroupDTO post);
	GroupDTO delete(String id);
    List<GroupDTO> findAll(String userId);
    GroupDTO findById(String id);
    GroupDTO update(GroupDTO post);
    GroupDTO addUser(UserDTO user);
    GroupDTO updateWithNewPost(Post persisted, String groupId);
    GroupDTO updateWithExistingPost(Post persisted, String groupId); 
}
