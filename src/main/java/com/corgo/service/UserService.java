package com.corgo.service;

import java.util.List;
import com.corgo.DTO.*;

public interface UserService {
	UserDTO create(UserDTO user);
    UserDTO delete(String id);
    List<UserDTO> findAll();
    UserDTO findById(String id);
    UserDTO findByUserId(String userId);
    UserDTO update(UserDTO user);
}
