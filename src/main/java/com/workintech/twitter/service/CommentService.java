package com.workintech.twitter.service;

import com.workintech.twitter.entity.Comment;
import com.workintech.twitter.entity.User;

import java.util.Optional;

public interface CommentService {

    Comment createComment(String text, Long tweetId, User user);
    Optional<Comment> findById(Long id);
    Comment updateComment(Long id, String text, User currentUser);
    void deleteComment(Long id, User currentUser, Long tweetOwnerId);
}
