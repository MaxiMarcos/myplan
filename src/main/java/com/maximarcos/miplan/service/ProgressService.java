package com.maximarcos.miplan.service;

import com.maximarcos.miplan.dto.progress.ProgressRequestDto;
import com.maximarcos.miplan.dto.progress.ProgressResponseDto;
import com.maximarcos.miplan.entity.Progress;
import com.maximarcos.miplan.mapper.ProgressMapper;
import com.maximarcos.miplan.repository.ProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProgressService {

    private final ProgressRepository progressRepository;
    private final ProgressMapper progressMapper;

    public ProgressService(ProgressRepository progressRepository, ProgressMapper progressMapper) {
        this.progressRepository = progressRepository;
        this.progressMapper = progressMapper;
    }

    public ResponseEntity<?> findAll() {

        List<Progress> progress = progressRepository.findAll();

        return ResponseEntity.ok(progressMapper.toListDto(progress));
    }

    public Optional<Progress> findById(Long id) {
        return progressRepository.findById(id);
    }

    public ResponseEntity<?> save(ProgressRequestDto request) {

        Progress progress = progressMapper.toEntity(request);
        progressRepository.save(progress);
        return ResponseEntity.ok(progressMapper.toResponse(progress));
    }

    public void deleteById(Long id) {
        progressRepository.deleteById(id);
    }
}
