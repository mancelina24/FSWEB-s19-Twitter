package com.workintech.twitter;

import com.workintech.twitter.controller.RetweetController;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.service.RetweetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;

import static org.mockito.Mockito.*;

public class RetweetControllerTest {

    @InjectMocks
    private RetweetController retweetController;

    @Mock
    private RetweetService retweetService;

    @Mock
    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRetweet_Success() {
        // Arrange
        Long tweetId = 1L;

        // Act
        retweetController.retweet(tweetId, user);

        // Assert
        verify(retweetService, times(1)).retweet(tweetId, user);
    }

    @Test
    public void testUndoRetweet_Success() {
        // Arrange
        Long tweetId = 1L;

        // Act
        retweetController.undoRetweet(tweetId, user);

        // Assert
        verify(retweetService, times(1)).undoRetweet(tweetId, user);
    }
}