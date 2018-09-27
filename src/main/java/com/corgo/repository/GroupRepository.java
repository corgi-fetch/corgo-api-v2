package com.corgo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

import com.corgo.model.Group;

public interface GroupRepository extends Repository<Group, String> {
	List<Group> findByUsersUserId(String userId);
	void delete(Group deleted);
    List<Group> findAll();
    Optional<Group> findById(String id);
    Group save(Group saved);
}
