package com.workintech.twitter.repository;

import com.workintech.twitter.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}


/*    @Query("SELECT u FROM User u WHERE u.email =:email")
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.username =:username")
    Optional<User> findByUserName(String username);*/