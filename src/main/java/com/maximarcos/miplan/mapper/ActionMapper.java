package com.maximarcos.miplan.mapper;

import com.maximarcos.miplan.dto.ActionDto;
import com.maximarcos.miplan.entity.Action;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ActionMapper {

    public ActionDto toDto(Action action) {
        return new ActionDto(action.getTitle(), action.getDescription());
    }

    public Action toEntity(ActionDto actionDto) {
        return Action.builder()
                .title(actionDto.title())
                .description(actionDto.description())
                .build();
    }

    public List<ActionDto> toListDto(List<Action> actions) {
        return actions.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}