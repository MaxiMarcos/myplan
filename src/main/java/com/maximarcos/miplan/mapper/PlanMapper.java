package com.maximarcos.miplan.mapper;

import com.maximarcos.miplan.dto.plan.planRequestDto;
import com.maximarcos.miplan.entity.Plan;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PlanMapper {

    public planRequestDto toDto(Plan plan) {
        return new planRequestDto(plan.getAction(), plan.getProgress(),plan.getUser(), plan.getStatus());
    }

    public Plan toEntity(planRequestDto planRequestDto) {

        Plan plan = Plan.builder()
                .action(planRequestDto.action())
                .status(planRequestDto.status())
                .progress(planRequestDto.progress())
                .build();
        return plan;
    }

    public List<planRequestDto> toListDto(List<Plan> plans) {
        return plans.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
