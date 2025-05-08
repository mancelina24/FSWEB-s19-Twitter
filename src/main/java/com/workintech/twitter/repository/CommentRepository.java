package com.workintech.twitter.repository;

import com.workintech.twitter.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByTweetId(Long tweetId);
    List<Comment> findByUserId(Long userId);
}
