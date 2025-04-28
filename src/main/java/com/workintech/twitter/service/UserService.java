package com.workintech.twitter.service;

import com.workintech.twitter.entity.User;

import java.util.Optional;

public interface UserService {

    User findById(Long userId);
    Optional<User> findByUsername(String username);
    User register(User user);
}
