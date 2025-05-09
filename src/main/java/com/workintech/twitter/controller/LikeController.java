package com.workintech.twitter.controller;


import com.workintech.twitter.entity.User;
import com.workintech.twitter.service.LikeService;
import com.workintech.twitter.service.TweetService;
import com.workintech.twitter.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@AllArgsConstructor
@Validated
@RestController
@RequestMapping("/like")
public class LikeController {
    @Autowired
    private LikeService likeService;
    private UserService userService;
    private TweetService tweetService;




    @PostMapping("/{tweetId}")
    @ResponseStatus(HttpStatus.OK)
    public void likeTweet(@PathVariable Long tweetId, @AuthenticationPrincipal User user){
        likeService.likeTweet(tweetId,user);
    }

    @DeleteMapping("/{tweetId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)

    public void unlikeTweet(@PathVariable Long tweetId, @AuthenticationPrincipal User user){
        likeService.unlikeTweet(tweetId,user);
    }
}
