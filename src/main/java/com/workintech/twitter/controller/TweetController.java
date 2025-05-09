package com.workintech.twitter.controller;

import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.service.TweetService;
import com.workintech.twitter.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tweet")
public class TweetController {

    private TweetService tweetService;
    private UserService userService;

    @Autowired
    public TweetController(TweetService tweetService, UserService userService) {
        this.tweetService = tweetService;
        this.userService = userService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Tweet> findAll(){
        return tweetService.findAll();
    }


  // 1. Tweeti ID ile bulma
    @GetMapping("/{id}")
    public Tweet getTweetById(@Positive @PathVariable Long id){
        return tweetService.findById(id);
    }


    // 2. Kullanıcı ID'sine göre tweetleri listeleme
    @GetMapping("/user/{userId}")
    public List<Tweet> getTweetsByUserId(@Positive @PathVariable Long userId){
        return tweetService.findByUserId(userId);
    }

    // 3. Kullanıcı adına göre tweetleri listeleme
    @GetMapping("/username/{username}")
    public List<Tweet> getTweetsByUsername(@PathVariable String username){
        return tweetService.findAllByUsername(username);
    }
    // 4. Tweet oluşturma
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Tweet createTweet(@RequestBody Tweet tweet){
        return tweetService.save(tweet);
    }

    // 5. Tweet güncelleme
    @PutMapping("/{id}")
    public Tweet updateTweet(@PathVariable Long id, @RequestBody Tweet tweet){

        return tweetService.update(id, tweet);
    }

    // 6. Tweet silme
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)//204 status
    public void deleteTweet(@Positive @PathVariable("id") Long id){ //JSON, Json convert
        tweetService.delete(id);
    }
}
