package com.maximarcos.miplan.mapper;

import com.maximarcos.miplan.dto.action.ActionRequestDto;
import com.maximarcos.miplan.dto.action.ActionResponseDto;
import com.maximarcos.miplan.entity.Action;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ActionMapper {

    public Action toEntity(ActionRequestDto request) {
        return Action.builder()
                .title(request.title())
                .description(request.description())
                .build();
    }

    public ActionResponseDto toResponse(Action action) {
        Long planId = action.getPlan() != null ? action.getPlan().getId() : null;
        return new ActionResponseDto(
                action.getId(),
                action.getTitle(),
                action.getDescription(),
                planId
        );
    }

    public List<ActionResponseDto> toListDto(List<Action> actions) {
        return actions.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}