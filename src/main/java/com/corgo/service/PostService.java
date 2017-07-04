package com.corgo.service;

import java.util.List;
import com.corgo.DTO.*;

public interface PostService {
	PostDTO create(PostDTO post);
    PostDTO delete(String id);
    List<PostDTO> findAll();
    PostDTO findById(String id);
    PostDTO update(PostDTO post);
}
