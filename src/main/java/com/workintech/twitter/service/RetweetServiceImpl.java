package com.workintech.twitter.service;

import com.workintech.twitter.entity.Retweet;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.exception.TwitterException;
import com.workintech.twitter.repository.RetweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RetweetServiceImpl implements RetweetService {

    private final RetweetRepository retweetRepository;
    private final TweetService tweetService;

    @Autowired
    public RetweetServiceImpl(RetweetRepository retweetRepository, TweetService tweetService) {
        this.retweetRepository = retweetRepository;
        this.tweetService = tweetService;
    }

    @Override
    public void retweet(Long tweetId, User user) {
        boolean alreadyRetweeted = retweetRepository
                .findAll()
                .stream()
                .anyMatch(r -> r.getTweet().getId().equals(tweetId) && r.getUser().getId().equals(user.getId()));
        if (alreadyRetweeted) {
            throw new TwitterException("You already retweeted this tweet.", HttpStatus.BAD_REQUEST);
        }

            Tweet tweet = tweetService.findById(tweetId);
            Retweet retweet = new Retweet();
            retweet.setTweet(tweet);
            retweet.setUser(user);
            retweet.setRetweetedAt(LocalDateTime.now());

        }

    @Override
    public void undoRetweet(Long tweetId, User user) {

    }

    @Override
    public List<Retweet> findAll() {
        return retweetRepository.findAll();
    }

    @Override
    public Retweet findById(Long id) {
        return retweetRepository.findById(id).orElseThrow(() -> new TwitterException("Retweet not found with id: " + id, HttpStatus.NOT_FOUND));
    }
}