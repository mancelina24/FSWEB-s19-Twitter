package com.workintech.twitter.dto;

public record LikesResponseDto(String token, UserResponseDto user) {
}


    /*    Long likeId, Boolean createdAt, Long likeUserId,
        String likeUserName, String likeUserEmail,
        Long tweetId, String tweetText, String tweetUserName, String tweetUserEmail*/