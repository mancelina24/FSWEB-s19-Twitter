package com.workintech.twitter.controller;

import com.workintech.twitter.dto.LoginRequest;
import com.workintech.twitter.dto.LoginResponse;
import com.workintech.twitter.dto.RegisterResponse;
import com.workintech.twitter.dto.RegistrationUser;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthenticationService authenticationService;
    private UserDetailsService userDetailsService;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;


    @Autowired

    public AuthController(AuthenticationService authenticationService, UserDetailsService userDetailsService,
                          AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.authenticationService = authenticationService;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    // Kullanıcı kaydını gerçekleştirir
    @PostMapping("/register")
    public RegisterResponse register(@RequestBody RegistrationUser userDto) {
        User user = authenticationService.register(
                userDto.email(),
                userDto.password(),
                userDto.username());


        return new RegisterResponse(user.getUsername(), "User registration successful.");
    }

    // Kullanıcı giriş işlemi yapar
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        try {
            // Kullanıcıyı veritabanından alıyoruz
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.email());

            // AuthenticationManager üzerinden kimlik doğrulaması yapıyoruz
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.email(),
                            loginRequest.password()
                    )
            );

            // Eğer şifre doğruysa, başarılı giriş mesajı döndürüyoruz
            if (passwordEncoder.matches(loginRequest.password(), userDetails.getPassword())) {


                return ResponseEntity.status(HttpStatus.OK)
                        .body(new LoginResponse("Login successful: " + loginRequest.email()));
            }
        } catch (Exception e) {
            // Eğer kimlik doğrulama hatalıysa, hata mesajı dönüyoruz
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse("Invalid credentials"));
        }

        return null; // Hata durumu için null dönüyoruz (burada geliştirme yapılabilir)
    }
}
