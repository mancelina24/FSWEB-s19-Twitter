package com.workintech.twitter.service;

import com.workintech.twitter.entity.User;

import java.util.Optional;

public interface UserService {

User finById(Long id);
User findByUsername(String username);
User findByEmail(String email);
User save(User user);

}
