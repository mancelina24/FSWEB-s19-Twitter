package com.workintech.twitter.service;

import com.workintech.twitter.entity.Like;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.exception.TwitterException;
import com.workintech.twitter.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LikesServiceImpl implements LikeService{

    private final LikeRepository likeRepository;
    private final TweetService tweetService; // Tweet var mı kontrolü için


    @Autowired
    public LikesServiceImpl(LikeRepository likeRepository, TweetService tweetService) {
        this.likeRepository = likeRepository;
        this.tweetService = tweetService;
    }


    @Override
    public void likeTweet(Long tweetId, User user) {
        boolean alreadyLiked=existsByUserIdAndTweetId(user.getId(), tweetId);
        if(alreadyLiked){
            throw new TwitterException("Tweet already liked by user", HttpStatus.BAD_REQUEST);
        }

        Tweet tweet=tweetService.findById(tweetId);
        Like like=new Like();
        like.setTweet(tweet);
        like.setUser(user);
        like.setCreatedAt(LocalDateTime.now());
        likeRepository.save(like);
    }

    @Override
    public void unlikeTweet(Long tweetId, User user) {
    Like like=findByTweetIdAndUserId(tweetId,user.getId())
            .orElseThrow(()->new TwitterException("Like not found for tweet and user", HttpStatus.NOT_FOUND));
    }

    @Override
    public boolean existsByUserIdAndTweetId(Long userId, Long tweetId) {
        return likeRepository.existsByUserIdAndTweetId(userId,tweetId);
    }

    @Override
    public Optional<Like> findByTweetIdAndUserId(Long tweetId, Long userId) {
        return Optional.empty();
    }

    @Override
    public List<Like> findAll() {
        return likeRepository.findAll();
    }

    @Override
    public Like findById(Long id) {
        return likeRepository.findById(id)
                .orElseThrow(()->new TwitterException("Like not found with id: " + id, HttpStatus.NOT_FOUND));

    }
}
