package com.workintech.twitter.service;

import com.workintech.twitter.entity.Like;
import com.workintech.twitter.entity.User;

import java.util.List;
import java.util.Optional;

public interface LikeService {

    void likeTweet(Long tweetId, User user);
    void unlikeTweet(Long tweetId, User user);
    boolean existsByUserIdAndTweetId(Long userId, Long tweetId);
    Optional<Like> findByTweetIdAndUserId(Long tweetId, Long userId);
    List<Like> findAll();
    Like findById(Long id);
}
