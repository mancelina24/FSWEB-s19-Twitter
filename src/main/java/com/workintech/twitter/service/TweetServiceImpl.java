package com.workintech.twitter.service;


import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.exception.TwitterException;
import com.workintech.twitter.repository.TweetRepository;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TweetServiceImpl implements TweetService {

//aşağıda final dersen @AllArgsConstructor eklemen lazım
    private TweetRepository tweetRepository;
    private UserService userService;

    @Autowired

    public TweetServiceImpl(TweetRepository tweetRepository, UserService userService) {
        this.tweetRepository = tweetRepository;
        this.userService = userService;
    }

    @Override
    public List<Tweet> findAll() {
        return tweetRepository.findAll();
    }

    @Override
    public Tweet findById(Long id) {
        return tweetRepository.findById(id)
                .orElseThrow(()->new TwitterException("Tweet not found with id:"+id, HttpStatus.NOT_FOUND));
    }

    @Override
    public List<Tweet> findByUserId(Long userid) {
        List<Tweet> userTweets = tweetRepository.findByUserId(userid);
        if(!userTweets.isEmpty())
        {
            return userTweets;
        }
        throw new TwitterException ("This user hasn't any tweet", HttpStatus.NOT_FOUND);
    }

    @Override
    public List<Tweet> findAllByUsername(String username) {

        return tweetRepository.findAllByUsername(username);
    }

    @Override
    public Tweet save(Tweet tweet) {
        tweet.setCreatedAt(LocalDateTime.now());

        return tweetRepository.save(tweet);
    }

    @Override
    public Tweet replaceOrCreate(Long id, Tweet tweet) {
        Optional<Tweet> optionalTweet=tweetRepository.findById(id); // id yoksa yeni bir tweet create eder
        if(optionalTweet.isPresent()){
            tweet.setId(id);
            return tweetRepository.save(tweet);
        }
    return  tweetRepository.save(tweet);
    }


    @Override
    public Tweet update(Long tweetId, Tweet tweet) {
        Tweet existingTweet=findById(tweetId); // varsa bul, yoksa exception fırlat //id olmak zorunda çünkü o id deki ilgili field update edilecek

        existingTweet.setContent(tweet.getContent());
        existingTweet.setUpdatedAt (LocalDateTime.now());
/*if(existingTweet.getContent() !=null){
    existingTweet.setContent(tweet.getContent());
}*/
        return tweetRepository.save(existingTweet);

    }

    @Override
    public void delete(Long id) {
        Tweet tweet=findById(id);// varsa bul, yoksa exception fırlat
        tweetRepository.delete(tweet);

    }
}



