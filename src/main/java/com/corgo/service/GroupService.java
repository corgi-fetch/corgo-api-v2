package com.corgo.service;

import java.util.List;

import com.corgo.DTO.GroupDTO;

public interface GroupService {
	GroupDTO create(GroupDTO post);
	GroupDTO delete(String id);
    List<GroupDTO> findAll();
    GroupDTO findById(String id);
    GroupDTO update(GroupDTO post);
}