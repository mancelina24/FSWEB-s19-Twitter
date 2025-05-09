package com.workintech.twitter.service;

import com.workintech.twitter.entity.Role;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.exception.TwitterException;
import com.workintech.twitter.repository.RoleRepository;
import com.workintech.twitter.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
@AllArgsConstructor
@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Transactional
    public User register (String email, String password, String username){
        //bu email sistemde var mı kontrol edelim
        if (userRepository.findByEmail(email).isPresent()) {
            throw new TwitterException("E-posta zaten kayıtlı.", HttpStatus.CONFLICT);
        }
/*  yukarıdaki yöntem yerine aşağıdaki de kullanılabilir
      Optional<User> userOptional=userRepository.findByEmail(email);
        if (userOptional.isPresent())
            throw new TwitterException("Email already register",HttpStatus.CONFLICT);*/

        if (userRepository.findByUsername(username).isPresent()) {
            throw new TwitterException("Kullanıcı adı zaten kayıtlı.", HttpStatus.CONFLICT);
        }

        String encodedPassword = passwordEncoder.encode(password);//çünkü password' ü encode etmek istiyoruz
/*        Role userRole = roleRepository.findByAuthority("USER") //kullanıcıya role ataması yapıyoruz
                .orElseThrow(() -> new TwitterException("Sistemde 'USER' rolü bulunamadı.", HttpStatus.INTERNAL_SERVER_ERROR));*/

        Optional<Role> userRole=roleRepository.findByAuthority("USER");
        if(userRole.isEmpty()){ //eğer role veritabanında yaratılmamış ise  hemen yaratıyoruz
            Role role=new Role();
            role.setAuthority("USER");
            userRole=Optional.of(roleRepository.save(role));
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(encodedPassword);
        user.setUsername(username);
        user.setRoles(Set.of(userRole.get()));
        //user.getRoles().add(userRole);
        return userRepository.save(user);
    }

}
