package com.workintech.twitter.service;

import com.workintech.twitter.entity.Comment;
import com.workintech.twitter.entity.User;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    Comment save(Comment comment);
    Comment findById(Long id);
    List<Comment> findAll();
    List<Comment> findByTweetId(Long tweetId);
    List<Comment> findByUserId(Long userId);
    Comment update(Long id, Comment comment);
    void delete(Long id);
}
