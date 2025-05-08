package com.workintech.twitter.repository;

import com.workintech.twitter.entity.Retweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RetweetRepository extends JpaRepository<Retweet, Long> {
    void deleteByUserIdAndTweetId(Long userId, Long tweetId);
}
