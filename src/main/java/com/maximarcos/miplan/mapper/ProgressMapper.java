package com.maximarcos.miplan.mapper;

import com.maximarcos.miplan.dto.ProgressDto;
import com.maximarcos.miplan.entity.Progress;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProgressMapper {

    public ProgressDto toDto(Progress progress) {
        return new ProgressDto(progress.getDescription(), progress.getDate());
    }

    public Progress toEntity(ProgressDto progressDto) {
        return Progress.builder()
                .description(progressDto.description())
                .date(progressDto.date())
                .build();
    }

    public List<ProgressDto> toListDto(List<Progress> progresses) {
        return progresses.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}