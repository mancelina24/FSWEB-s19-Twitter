package com.workintech.twitter.dto;

public record LikesResponse(Long likeId, Boolean createdAt, Long likeUserId,
                            String likeUserName, String likeUserEmail,
                            Long tweetId, String tweetText, String tweetUserName, String tweetUserEmail) {
}
