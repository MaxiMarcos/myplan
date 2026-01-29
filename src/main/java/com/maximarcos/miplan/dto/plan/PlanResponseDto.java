package com.maximarcos.miplan.dto.plan;

import com.maximarcos.miplan.enums.Status;

import java.util.List;

public record PlanResponseDto(Long id, List<Long> actionsId, List<Long> progressId, Status status, String title, String description) {}
