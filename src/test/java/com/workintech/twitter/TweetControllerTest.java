package com.workintech.twitter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workintech.twitter.controller.TweetController;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.service.TweetService;
import com.workintech.twitter.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TweetControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TweetService tweetService;

    @Mock
    private UserService userService;

    @InjectMocks
    private TweetController tweetController;

    private Tweet tweet1;
    private Tweet tweet2;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(tweetController).build();

        tweet1 = new Tweet();
        tweet1.setId(1L);
        tweet1.setContent("Test tweet 1");

        tweet2 = new Tweet();
        tweet2.setId(2L);
        tweet2.setContent("Test tweet 2");
    }

    @Test
    void findAll_ShouldReturnTweets() throws Exception {
        List<Tweet> tweets = Arrays.asList(tweet1, tweet2);
        when(tweetService.findAll()).thenReturn(tweets);

        mockMvc.perform(get("/tweet"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].content").value("Test tweet 1"));

        verify(tweetService, times(1)).findAll();
    }

    @Test
    void getTweetById_ShouldReturnTweet() throws Exception {
        when(tweetService.findById(1L)).thenReturn(tweet1);

        mockMvc.perform(get("/tweet/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Test tweet 1"));

        verify(tweetService, times(1)).findById(1L);
    }

    @Test
    void getTweetsByUserId_ShouldReturnList() throws Exception {
        when(tweetService.findByUserId(1L)).thenReturn(Collections.singletonList(tweet1));

        mockMvc.perform(get("/tweet/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].content").value("Test tweet 1"));

        verify(tweetService, times(1)).findByUserId(1L);
    }

    @Test
    void getTweetsByUsername_ShouldReturnList() throws Exception {
        when(tweetService.findAllByUsername("user")).thenReturn(Arrays.asList(tweet1, tweet2));

        mockMvc.perform(get("/tweet/username/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));

        verify(tweetService, times(1)).findAllByUsername("user");
    }

    @Test
    void createTweet_ShouldReturnCreatedTweet() throws Exception {
        when(tweetService.save(any(Tweet.class))).thenReturn(tweet1);

        mockMvc.perform(post("/tweet/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tweet1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.content").value("Test tweet 1"));

        verify(tweetService, times(1)).save(any(Tweet.class));
    }

    @Test
    void updateTweet_ShouldReturnUpdatedTweet() throws Exception {
        when(tweetService.update(eq(1L), any(Tweet.class))).thenReturn(tweet1);

        mockMvc.perform(put("/tweet/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tweet1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Test tweet 1"));

        verify(tweetService, times(1)).update(eq(1L), any(Tweet.class));
    }

    @Test
    void deleteTweet_ShouldReturnNoContent() throws Exception {
        doNothing().when(tweetService).delete(1L);

        mockMvc.perform(delete("/tweet/1"))
                .andExpect(status().isNoContent());

        verify(tweetService, times(1)).delete(1L);
    }
}