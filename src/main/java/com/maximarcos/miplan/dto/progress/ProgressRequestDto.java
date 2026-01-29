package com.maximarcos.miplan.dto.progress;

import java.time.LocalDateTime;

public record ProgressRequestDto(String description, LocalDateTime date, Long planId) {
}