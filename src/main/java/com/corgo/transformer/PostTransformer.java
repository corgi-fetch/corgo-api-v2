package com.corgo.transformer;

import java.util.List;

import com.corgo.DTO.PostDTO;
import com.corgo.model.Post;

public interface PostTransformer {
	public PostDTO ConvertPostToPostDTO(Post model);
	public List<PostDTO> ConvertListOfPostsToPostDTO(List<Post> listPost);
	public Post ConvertPostDTOToPost(PostDTO postDTO);
	public List<Post> ConvertListOfPostDTOToPost(List<PostDTO> listPostDTO);
}
