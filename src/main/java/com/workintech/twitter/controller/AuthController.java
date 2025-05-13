package com.workintech.twitter.controller;

import com.workintech.twitter.dto.LoginRequestDto;
import com.workintech.twitter.dto.LoginResponseDto;
import com.workintech.twitter.dto.RegisterResponseDto;
import com.workintech.twitter.dto.RegisterRequestDto;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.service.AuthenticationService;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {


    private final AuthenticationService authenticationService;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthController( AuthenticationService authenticationService, UserDetailsService userDetailsService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {

        this.authenticationService = authenticationService;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    // Kullanıcı kaydını gerçekleştiriyoruz
    @PostMapping("/register")
    public RegisterResponseDto register(@Validated @RequestBody RegisterRequestDto registerDto) {
        User user = authenticationService.register(
                registerDto.getEmail(),
                registerDto.getPassword(),
                registerDto.getUsername());

        return new RegisterResponseDto(registerDto.getEmail(), "User registration successful.");
        //user.getEmail() de yazabilirdin.
    }


    // Kullanıcı giriş işlemi yapar
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginDto) {
        try {

            // AuthenticationManager üzerinden kimlik doğrulaması yapıyoruz
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.email(),
                            loginDto.password()
                    )
            );

            // Authentication başarılıysa, kullanıcı detaylarını al
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();


            // Kullanıcıyı DB'den al (dilersen DTO'ya eklemek için)
            User user = authenticationService.getUserByEmail(loginDto.email());

            // Giriş başarılı, token ve user dön
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new LoginResponseDto("Login successful", user));

        } catch (Exception e) {
            System.out.println("Login hatası: " + e.getMessage());
            e.printStackTrace(); // Hatanın ne olduğunu logla
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponseDto("Invalid credentials", null));
        }
    }
}
