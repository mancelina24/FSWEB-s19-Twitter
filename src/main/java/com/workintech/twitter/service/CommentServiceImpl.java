package com.workintech.twitter.service;

import com.workintech.twitter.entity.Comment;
import com.workintech.twitter.exception.TwitterException;
import com.workintech.twitter.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class CommentServiceImpl implements CommentService {

private CommentRepository commentRepository;
private TweetService tweetService;
private UserService userService;

@Autowired
    public CommentServiceImpl(CommentRepository commentRepository, TweetService tweetService, UserService userService) {
        this.commentRepository = commentRepository;
        this.tweetService = tweetService;
        this.userService = userService;
    }



    @Override
    public Comment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(()->new TwitterException("Comment not found with id: " + id,HttpStatus.NOT_FOUND));
    }

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public List<Comment> findByTweetId(Long tweetId) {
        return commentRepository.findByTweetId(tweetId);
    }

    @Override
    public List<Comment> findByUserId(Long userId) {
        return commentRepository.findByUserId(userId);
    }
    @Override
    public Comment save(Comment comment) {
    comment.setCreatedAt(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    @Override
    public Comment update(Long id, Comment updatedComment) {
        Comment existingComment=findById(id);
        existingComment.setText(updatedComment.getText());
        existingComment.setUpdatedAt(LocalDateTime.now());
        return commentRepository.save(existingComment);
    }

    @Override
    public void delete(Long id) {
        Comment comment=findById(id);
        commentRepository.delete(comment);
    }
}
