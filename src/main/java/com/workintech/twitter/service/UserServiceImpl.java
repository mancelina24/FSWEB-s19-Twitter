package com.workintech.twitter.service;

import com.workintech.twitter.entity.User;
import com.workintech.twitter.exception.TwitterException;
import com.workintech.twitter.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User finById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(()->new TwitterException("User not found with id: " + id, HttpStatus.NOT_FOUND));
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(()-> new TwitterException("User not found with username: " + username, HttpStatus.NOT_FOUND));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new TwitterException("Email not found with username: " + email, HttpStatus.NOT_FOUND));
    }

    @Override
    public User save(User user) {
        user.setRegistrationDate(LocalDateTime.now());
        return userRepository.save(user);
    }
}

