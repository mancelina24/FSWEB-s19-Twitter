package com.workintech.twitter.service;

import com.workintech.twitter.entity.Role;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.exception.TwitterException;
import com.workintech.twitter.repository.RoleRepository;
import com.workintech.twitter.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthenticationService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

@Autowired
    public AuthenticationService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Transactional
    public User register (String email, String password, String username){
        if (userRepository.findByEmail(email).isPresent()) {
            throw new TwitterException("E-posta zaten kayıtlı.", HttpStatus.BAD_REQUEST);
        }

        if (userRepository.findByUsername(username).isPresent()) {
            throw new TwitterException("Kullanıcı adı zaten kayıtlı.", HttpStatus.BAD_REQUEST);
        }

        String encodedPassword = passwordEncoder.encode(password);
        Role userRole = roleRepository.findByAuthority("USER")
                .orElseThrow(() -> new TwitterException("Sistemde 'USER' rolü bulunamadı.", HttpStatus.INTERNAL_SERVER_ERROR));

        User user = new User();
        user.setEmail(email);
        user.setPassword(encodedPassword);
        user.setUsername(username);
        user.getRoles().add(userRole);

        return userRepository.save(user);
    }

}
