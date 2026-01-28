package com.maximarcos.miplan.dto.comment;

import com.maximarcos.miplan.entity.User;

import java.time.LocalDateTime;

public record CommentRequestDto(String text, User user, LocalDateTime createdAt) {
}