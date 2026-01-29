package com.maximarcos.miplan.mapper;

import com.maximarcos.miplan.dto.progress.ProgressRequestDto;
import com.maximarcos.miplan.dto.progress.ProgressResponseDto;
import com.maximarcos.miplan.entity.Progress;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProgressMapper {

    public ProgressResponseDto toResponse(Progress progress) {
        return new ProgressResponseDto(progress.getId(), progress.getDescription(), progress.getDate(), progress.getPlan().getId());
    }

    public Progress toEntity(ProgressRequestDto progressRequestDto) {
        return Progress.builder()
                .description(progressRequestDto.description())
                .date(progressRequestDto.date())
                .build();
    }

    public List<ProgressResponseDto> toListDto(List<Progress> progress) {
        return progress.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}