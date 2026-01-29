package com.maximarcos.miplan.dto.progress;

import java.time.LocalDateTime;

public record ProgressResponseDto(Long id, String description, LocalDateTime date, Long planId) {}
