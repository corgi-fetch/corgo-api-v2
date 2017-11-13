package com.corgo.service;

import java.util.List;

import com.corgo.DTO.GroupDTO;
import com.corgo.DTO.UserDTO;

public interface GroupService {
	GroupDTO create(GroupDTO post);
	GroupDTO delete(String id);
    List<GroupDTO> findAll();
    GroupDTO findById(String id);
    GroupDTO update(GroupDTO post);
    GroupDTO addUser(UserDTO user);
}
