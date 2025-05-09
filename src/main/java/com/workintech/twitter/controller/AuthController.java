package com.workintech.twitter.controller;

import com.workintech.twitter.dto.LoginRequestDto;
import com.workintech.twitter.dto.LoginResponseDto;
import com.workintech.twitter.dto.RegisterResponseDto;
import com.workintech.twitter.dto.RegisterRequestDto;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.service.AuthenticationService;
import lombok.AllArgsConstructor;
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

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;
    private UserDetailsService userDetailsService;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;


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
public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginDto){
        try {
            // Kullanıcıyı veritabanından alıyoruz
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginDto.email());

            // AuthenticationManager üzerinden kimlik doğrulaması yapıyoruz
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.email(),
                            loginDto.password()
                    )
            );

            // Eğer şifre doğruysa, başarılı giriş mesajı döndürüyoruz
            if (passwordEncoder.matches(loginDto.password(), userDetails.getPassword())) {


                return ResponseEntity.status(HttpStatus.OK)
                        .body(new LoginResponseDto("Login successful: " + loginDto.email()));
            }
        } catch (Exception e) {
            // Eğer kimlik doğrulama hatalıysa, hata mesajı dönüyoruz
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponseDto("Invalid credentials"));
        }

        return null; // Hata durumu için null dönüyoruz (burada geliştirme yapılabilir)
    }
}
