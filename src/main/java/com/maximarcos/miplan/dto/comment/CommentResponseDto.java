package com.maximarcos.miplan.dto.comment;

import java.time.LocalDateTime;

public record CommentResponseDto(Long id, String text, LocalDateTime createdAt, Long userId, Long postId) {}
