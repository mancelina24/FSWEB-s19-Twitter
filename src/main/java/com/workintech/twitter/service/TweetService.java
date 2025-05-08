package com.workintech.twitter.service;

import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;

import java.util.List;
import java.util.Optional;

public interface TweetService {
    List<Tweet> findAll();
    Tweet findById(Long id);
    List<Tweet> findByUserId(Long userid);
    List<Tweet> findAllByUsername(String username);
    Tweet save(Tweet tweet);
    Tweet replaceOrCreate(Long id, Tweet tweet);
    Tweet update(Long tweetId, Tweet tweet);
    void delete(Long id);

}
