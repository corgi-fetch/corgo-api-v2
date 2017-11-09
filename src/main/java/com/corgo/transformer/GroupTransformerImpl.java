package com.corgo.transformer;

import java.util.List;

import org.springframework.stereotype.Component;

import com.corgo.DTO.GroupDTO;
import com.corgo.model.Group;

@Component
public class GroupTransformerImpl implements GroupTransformer {
	public GroupDTO ConvertGroupToGroupDTO(Group model) { return null; }
	public List<GroupDTO> ConvertListOfGroupToGroupDTO(List<Group> model) { return null; }
	
	public Group ConvertGroupDTOToGroup(GroupDTO model) { return null; }
	public List<Group> ConvertListOfGroupDTOToGroup(List<GroupDTO> model) { return null; }
}
