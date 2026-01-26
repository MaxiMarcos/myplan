package com.maximarcos.miplan.dto;

import com.maximarcos.miplan.entity.User;

import java.time.LocalDateTime;

public record CommentDto(String text, User user, LocalDateTime createdAt) {
}