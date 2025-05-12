package com.workintech.twitter;
import com.workintech.twitter.controller.CommentController;
import com.workintech.twitter.entity.Comment;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CommentControllerTest {

  /*  @InjectMocks
    private CommentController commentController;

    @Mock
    private CommentService commentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddComment_Success() {
        // Arrange
        Comment mockComment = new Comment();
        mockComment.setText("Nice tweet!");
        User user = new User();
        Long tweetId = 1L;

        when(commentService.save(mockComment)).thenReturn(mockComment);

        // Act
        Comment result = commentController.addComment(tweetId, mockComment, user);

        // Assert
        assertNotNull(result);
        assertEquals("Nice tweet!", result.getText());
        verify(commentService, times(1)).save(mockComment);
    }

    @Test
    public void testGetCommentsByTweetId_Success() {
        // Arrange
        Long tweetId = 1L;
        Comment comment1 = new Comment();
        comment1.setText("First comment");
        Comment comment2 = new Comment();
        comment2.setText("Second comment");

        List<Comment> commentList = Arrays.asList(comment1, comment2);

        when(commentService.findByTweetId(tweetId)).thenReturn(commentList);

        // Act
        List<Comment> result = commentController.getCommentsByTweetId(tweetId);

        // Assert
        assertEquals(2, result.size());
        assertEquals("First comment", result.get(0).getText());
        verify(commentService, times(1)).findByTweetId(tweetId);
    }

    @Test
    public void testDeleteComment_Success() {
        // Arrange
        Long commentId = 1L;
        User user = new User();

        // Act
        commentController.deleteComment(commentId, user);

        // Assert
        verify(commentService, times(1)).delete(commentId);
    }*/
}