package com.workintech.twitter.dto;

import com.workintech.twitter.entity.User;

public record LoginResponseDto(String message, User user) {}
