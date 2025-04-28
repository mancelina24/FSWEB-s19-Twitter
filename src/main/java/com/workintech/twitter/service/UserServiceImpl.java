package com.workintech.twitter.service;

import com.workintech.twitter.entity.User;
import com.workintech.twitter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

@Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findById(Long userId) {
        return null;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public User register(User user) {
        return null;
    }
}
