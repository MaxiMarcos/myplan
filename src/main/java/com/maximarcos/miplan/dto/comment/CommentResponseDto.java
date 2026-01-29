package com.maximarcos.miplan.dto.comment;

import com.maximarcos.miplan.entity.User;

import java.time.LocalDateTime;

public record CommentResponseDto(String text, Long userId, LocalDateTime createdAt) {}
