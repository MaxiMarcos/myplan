package com.maximarcos.miplan.dto.post;

import com.maximarcos.miplan.enums.Category;

import java.time.LocalDateTime;

public record PostResponseDto(Long id, String title, int like, String content, LocalDateTime createdAt, LocalDateTime updatedAt, Long authorId, Category category) {}
