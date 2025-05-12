package com.workintech.twitter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workintech.twitter.controller.UserController;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User user;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setEmail("test@example.com");
    }

    @Test
    void getUserById_ShouldReturnUser() throws Exception {
        when(userService.finById(1L)).thenReturn(user);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"));
                //.andExpect(jsonPath("$.username").value("testUser"));

        verify(userService, times(1)).finById(1L);
    }

    @Test
    void getUserByUsername_ShouldReturnUser() throws Exception {
        when(userService.findByUsername("testUser")).thenReturn(user);

        mockMvc.perform(get("/users/username/testUser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"));

        verify(userService, times(1)).findByUsername("testUser");
    }

    @Test
    void getUserByEmail_ShouldReturnUser() throws Exception {
        when(userService.findByEmail("test@example.com")).thenReturn(user);

        mockMvc.perform(get("/users/email/test@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"));
        // .andExpect(jsonPath("$.username").value("testUser"));

        verify(userService, times(1)).findByEmail("test@example.com");
    }

    @Test
    void createUser_ShouldReturnCreatedUser() throws Exception {
        when(userService.save(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("test@example.com"));
        //   .andExpect(jsonPath("$.username").value("testUser"));

        verify(userService, times(1)).save(any(User.class));
    }
}