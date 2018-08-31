package com.corgo.service;

import java.util.List;
import com.corgo.DTO.*;
import com.corgo.model.Post;

public interface UserService {
	UserDTO create(UserDTO user);
    UserDTO delete(String id);
    List<UserDTO> findAll();
    UserDTO findById(String id);
    UserDTO findByUserId(String userId);
    UserDTO update(UserDTO user);
    UserDTO updateWithNewPost(PostDTO post, String userId);
    UserDTO updateWithExistingPost(Post persisted, String groupId); 

}
