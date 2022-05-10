package com.project.ezoo.repository;

import com.project.ezoo.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);

    List<User> findByUsernameContainingIgnoreCase(String name);
}
