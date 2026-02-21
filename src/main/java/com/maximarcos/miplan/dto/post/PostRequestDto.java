package com.maximarcos.miplan.dto.post;

import com.maximarcos.miplan.enums.Category;

public record PostRequestDto(String title, int like, String content, Long authorId, Category category) {}
