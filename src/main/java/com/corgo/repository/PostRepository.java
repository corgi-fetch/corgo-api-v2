package com.corgo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

import com.corgo.model.*;

public interface PostRepository extends Repository<Post, String> {
    void delete(Post deleted);
    List<Post> findAll();
    Optional<Post> findOne(String id);
    Post save(Post saved);
}
