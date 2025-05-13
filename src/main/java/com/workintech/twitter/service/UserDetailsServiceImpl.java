package com.workintech.twitter.service;
import com.workintech.twitter.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;



    // Spring Security için zorunlu override
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { //loadUserByUsername() metodu, kullanıcıyı veritabanında arar.
        return userRepository.findByEmail(username) // bizde bu alan aslında username -> email (User entityde getUsername email olarak tanımlandı
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found via given email: " + username)); //UsernameNotFoundException tanımlı bir exception
    }
}
