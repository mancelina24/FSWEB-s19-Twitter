package com.workintech.twitter.controller;


import com.workintech.twitter.entity.Comment;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private CommentService commentService;

    @Autowired

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{tweetId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Comment addComment(@PathVariable Long tweetId, @RequestBody Comment comment, @AuthenticationPrincipal User user){
        return commentService.save(comment, tweetId, user);
    }

    @PutMapping("/{commentId}/tweet/{tweetId}")
    @ResponseStatus(HttpStatus.OK)
    public Comment updateComment(@PathVariable Long commentId,
                                 @PathVariable Long tweetId,
                                 @RequestBody Comment comment,
                                 @AuthenticationPrincipal User user) {
        return commentService.update(commentId, comment, tweetId, user);
    }

    @GetMapping("/tweet/{tweetId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Comment> getCommentsByTweetId(@PathVariable Long tweetId) {
        return commentService.findByTweetId(tweetId);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable Long commentId,
                              @AuthenticationPrincipal User user) {
        commentService.delete(commentId);
    }
}
