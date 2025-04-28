package com.workintech.twitter.service;

import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;

import java.util.List;
import java.util.Optional;

public interface TweetService {

    Tweet createTweet(String content, User user);
    List<Tweet> findByUserId(Long userId);
    Optional<Tweet> findById(Long id);
    Tweet updateTweet(Long id, String content, User currentUser);
    void deleteTweet(Long id, User currentUser);
}
