package com.corgo.transformer;

import java.util.List;

import com.corgo.DTO.PostStubDTO;
import com.corgo.model.Post;

public interface PostStubTransformer {
	public PostStubDTO ConvertPostToPostStubDTO(Post model);
	public List<PostStubDTO> ConvertListOfPostsToPostStubDTO(List<Post> listPost);
	public Post ConvertPostStubDTOToPost(PostStubDTO dto);
	public List<Post> ConvertListOfPostStubDTOToPost(List<PostStubDTO> listPost);
}

