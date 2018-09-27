package com.corgo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

import com.corgo.model.*;

public interface UserRepository extends Repository<User, String> {
    
    void delete(User deleted);
    List<User> findAll();
    Optional<User> findById(String id);
    User save(User saved);
    Optional<User> findByUserId(String userId);


}
