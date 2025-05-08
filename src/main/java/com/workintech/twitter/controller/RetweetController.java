package com.workintech.twitter.controller;


import com.workintech.twitter.entity.User;
import com.workintech.twitter.service.RetweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/retweets")
public class RetweetController {

    private RetweetService retweetService;

    @Autowired
    public RetweetController(RetweetService retweetService) {
        this.retweetService = retweetService;
    }

    @PostMapping("/{tweetId}")
    @ResponseStatus(HttpStatus.OK)
    public void retweet(@PathVariable Long tweetId,
                        @AuthenticationPrincipal User user) {
        retweetService.retweet(tweetId, user);
    }

    @DeleteMapping("/{tweetId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void undoRetweet(@PathVariable Long tweetId,
                            @AuthenticationPrincipal User user) {
        retweetService.undoRetweet(tweetId, user);
    }

}
