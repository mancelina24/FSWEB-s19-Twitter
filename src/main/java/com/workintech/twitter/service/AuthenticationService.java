package com.workintech.twitter.service;

import com.workintech.twitter.entity.Role;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.repository.RoleRepository;
import com.workintech.twitter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public User register (String email, String password,String username){
        Optional<User> optionalUser=userRepository.findByEmail(email);
        if(optionalUser.isPresent()){
            throw new RuntimeException("Username already exist");
        }
        String encodedPassword=passwordEncoder.encode(password);
        Optional<Role> optionalRole=roleRepository.findByAuthority("USER");
        List<Role> roles=new ArrayList<>();

        if(optionalRole.isPresent()){
            Role userRole=optionalRole.get();
            roles.add(userRole);
        } else {
            throw new RuntimeException("There is not this role in role table");
        }


        User user=new User();
        user.setEmail(email);
        user.setPassword(encodedPassword);
        user.setUsername(username);
        //user.setRoles(roles);
        return userRepository.save(user);

    }
}
