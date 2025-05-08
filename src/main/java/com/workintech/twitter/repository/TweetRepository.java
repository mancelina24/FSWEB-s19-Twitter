package com.workintech.twitter.repository;

import com.workintech.twitter.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
//Tweet: Bu repository'nin yönetmekle sorumlu olduğu Entity sınıfıdır.
//Long: Bu entity'nin primary key (id) alanının veri tipidir.
public interface TweetRepository extends JpaRepository<Tweet, Long> {

    @Query("SELECT t FROM Tweet t WHERE t.user.username = :username")
    List<Tweet> findAllByUsername(@Param("username") String username);

    @Query("SELECT t FROM Tweet t WHERE t.user.id = :userId")
    List<Tweet> findByUserId(@Param("userId") Long userId);
}
