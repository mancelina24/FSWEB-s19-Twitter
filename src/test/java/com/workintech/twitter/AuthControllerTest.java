package com.workintech.twitter;
import com.workintech.twitter.controller.AuthController;
import com.workintech.twitter.dto.LoginRequestDto;
import com.workintech.twitter.dto.LoginResponseDto;
import com.workintech.twitter.dto.RegisterRequestDto;
import com.workintech.twitter.dto.RegisterResponseDto;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthenticationService authenticationService;
    @Mock
    private UserDetailsService userDetailsService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Authentication authentication;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegister_Success() {
        // Arrange
        RegisterRequestDto requestDto = new RegisterRequestDto("test@example.com", "password", "testuser");
        User mockUser = new User();
        mockUser.setEmail("test@example.com");

        when(authenticationService.register("test@example.com", "password", "testuser")).thenReturn(mockUser);

        // Act
        RegisterResponseDto response = authController.register(requestDto);

        // Assert
        assertNotNull(response);
        assertEquals("test@example.com", response.email());
        assertEquals("User registration successful.", response.message());
    }

    @Test
    public void testLogin_Success() {
        // Arrange
        LoginRequestDto requestDto = new LoginRequestDto("test@example.com", "password");
        UserDetails mockUserDetails = mock(UserDetails.class);

        when(userDetailsService.loadUserByUsername("test@example.com")).thenReturn(mockUserDetails);
        when(mockUserDetails.getPassword()).thenReturn("encodedPassword");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);

        // Act
        ResponseEntity<LoginResponseDto> response = authController.login(requestDto);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Login successful: test@example.com", response.getBody().message());
    }

    @Test
    public void testLogin_InvalidCredentials() {
        // Arrange
        LoginRequestDto requestDto = new LoginRequestDto("wrong@example.com", "wrongpass");
        when(userDetailsService.loadUserByUsername("wrong@example.com")).thenThrow(new RuntimeException("User not found"));

        // Act
        ResponseEntity<LoginResponseDto> response = authController.login(requestDto);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid credentials", response.getBody().message());
    }
}