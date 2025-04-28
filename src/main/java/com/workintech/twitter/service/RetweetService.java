package com.workintech.twitter.service;

import com.workintech.twitter.entity.Retweet;
import com.workintech.twitter.entity.User;

import java.util.Optional;

public interface RetweetService {

    void retweet(Long tweetId, User user);
    void unretweet(Long retweetId, User currentUser);
    Optional<Retweet> findById(Long id);
}
