package com.corgo.transformer;

import java.util.List;

import com.corgo.DTO.GroupDTO;
import com.corgo.DTO.GroupStubDTO;
import com.corgo.DTO.PostDTO;
import com.corgo.model.Group;
import com.corgo.model.GroupStub;
import com.corgo.model.Post;

public interface GroupTransformer {
	public GroupDTO ConvertGroupToGroupDTO(Group model);
	public List<GroupDTO> ConvertListOfGroupToGroupDTO(List<Group> model);
	
	public Group ConvertGroupDTOToGroup(GroupDTO model);
	public List<Group> ConvertListOfGroupDTOToGroup(List<GroupDTO> model);
	
	public List<GroupStubDTO> ConvertListOfGroupStubToGroupStubDTO(List<GroupStub> stub);
	public List<GroupStub> ConvertListOfGroupStubDTOToGroupStub(List<GroupStubDTO> stubDTO);
	
	public GroupStub ConvertGroupStubDTOToGroupStub(GroupStubDTO stub);
	public GroupStubDTO ConvertGroupStubToGroupStubDTO(GroupStub stubDTO);
	
	public GroupStubDTO ConvertGroupDTOToGroupStubDTO(GroupDTO dto);
}
