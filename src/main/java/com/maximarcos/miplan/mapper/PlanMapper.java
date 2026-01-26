package com.maximarcos.miplan.mapper;

import com.maximarcos.miplan.dto.PlanDto;
import com.maximarcos.miplan.entity.Plan;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PlanMapper {

    public PlanDto toDto(Plan plan) {
        return new PlanDto(plan.getAction(), plan.getProgress(), plan.getComment(), plan.getStatus());
    }

    public Plan toEntity(PlanDto planDto) {

        Plan plan = Plan.builder()
                .comment(planDto.comment())
                .action(planDto.action())
                .status(planDto.status())
                .progress(planDto.progress())
                .build();
        return plan;
    }

    public List<PlanDto> toListDto(List<Plan> plans) {
        return plans.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
