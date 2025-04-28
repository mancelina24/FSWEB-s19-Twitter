package com.workintech.twitter.service;

import com.workintech.twitter.entity.User;

public interface LikeService {
    void likeTweet(Long tweetId, User user);
    void unlikeTweet(Long tweetId, User user);
}
