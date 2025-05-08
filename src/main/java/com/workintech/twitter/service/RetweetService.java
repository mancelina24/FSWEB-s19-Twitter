package com.workintech.twitter.service;

import com.workintech.twitter.entity.Retweet;
import com.workintech.twitter.entity.User;

import java.util.List;
import java.util.Optional;

public interface RetweetService {


    void retweet(Long tweetId, User user);
    void undoRetweet(Long tweetId, User user);
    List<Retweet> findAll();
    Retweet findById(Long id);
}
