package com.workintech.twitter.dto;

import com.workintech.twitter.entity.User;

public record UserResponseDto(Long id, String email, String username) {
    public static UserResponseDto fromUser(User user) {
        return new UserResponseDto(user.getId(), user.getEmail(), user.getUsername());
    }
}