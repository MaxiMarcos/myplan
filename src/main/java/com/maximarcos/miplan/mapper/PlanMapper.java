package com.maximarcos.miplan.mapper;

import com.maximarcos.miplan.dto.plan.PlanRequestDto;
import com.maximarcos.miplan.dto.plan.PlanResponseDto;
import com.maximarcos.miplan.entity.Action;
import com.maximarcos.miplan.entity.Plan;
import com.maximarcos.miplan.entity.Progress;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PlanMapper {

    public PlanResponseDto toResponseDto(Plan plan) {
        List<Long> actionIds = plan.getAction() != null
                ? plan.getAction().stream().map(Action::getId).collect(Collectors.toList())
                : Collections.emptyList();
        List<Long> progressIds = plan.getProgress() != null
                ? plan.getProgress().stream().map(Progress::getId).collect(Collectors.toList())
                : Collections.emptyList();
        return new PlanResponseDto(
                plan.getId(),
                actionIds,
                progressIds,
                plan.getStatus(),
                plan.getTitle(),
                plan.getDescription());
    }

    public Plan toEntity(PlanRequestDto planRequestDto) {
        return Plan.builder()
                .status(planRequestDto.status())
                .title(planRequestDto.title())
                .description(planRequestDto.description())
                .build();
    }

    public List<PlanResponseDto> toListResponseDto(List<Plan> plans) {
        return plans.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }
}
