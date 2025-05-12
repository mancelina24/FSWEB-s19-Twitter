package com.workintech.twitter;
import com.workintech.twitter.controller.LikeController;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.service.LikeService;
import com.workintech.twitter.service.TweetService;
import com.workintech.twitter.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class LikeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LikeService likeService;

    @Mock
    private UserService userService;

    @Mock
    private TweetService tweetService;

    @InjectMocks
    private LikeController likeController;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(likeController).build();

        // Kullanıcı nesnesi hazırlanıyor

        user = new User();
        user.setId(1L);// Burada ID atanıyor
        user.setUsername("testUser");
        user.setEmail("test@example.com");
        // Simule edilmiş bir Authentication set ediliyor

        // Mock edilen kullanıcıyı SecurityContext'e ekleyin
        SecurityContextHolder.getContext().setAuthentication(
                new TestingAuthenticationToken(user, null)
        );

    }

    @Test
    void likeTweet_ShouldReturnOk() throws Exception {
        Long tweetId = 1L;

        // "likeTweet" metodu test sırasında bir şey döndürmemeli  // Service mock davranışı  // Mock LikeService kullanımı
        doNothing().when(likeService).likeTweet(tweetId, user);


        // MockMvc kullanılarak POST isteği simüle ediliyor   // POST isteği sırasında kullanıcı doğru bağlanıyor    // MockMvc ile istek gönderiliyor ve kullanıcı doğru bağlanıyor
        mockMvc.perform(post("/like/{tweetId}", tweetId)
                        .principal(new TestingAuthenticationToken(user, null))) // Kullanıcı doğru set ediliyor
                .andExpect(status().isOk());

// Durum kodu 200 OK olmalı


        // "likeTweet" metodu gerçekten çağrıldı mı?
        verify(likeService, times(1)).likeTweet(tweetId, user);

    }

    @Test
    void unlikeTweet_ShouldReturnNoContent() throws Exception {
        Long tweetId = 1L;

        doNothing().when(likeService).unlikeTweet(tweetId, user);

        mockMvc.perform(delete("/like/{tweetId}", tweetId)
                        .requestAttr("user", user)) // Simüle edilen user
                .andExpect(status().isNoContent());

        verify(likeService, times(1)).unlikeTweet(tweetId, user);
    }
}
