package com.maximarcos.miplan.controller;

import com.maximarcos.miplan.dto.progress.ProgressRequestDto;
import com.maximarcos.miplan.dto.progress.ProgressResponseDto;
import com.maximarcos.miplan.entity.Progress;
import com.maximarcos.miplan.mapper.ProgressMapper;
import com.maximarcos.miplan.service.ProgressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/progress")
public class ProgressController {

    private final ProgressService progressService;
    private final ProgressMapper progressMapper;

    public ProgressController(ProgressService progressService, ProgressMapper progressMapper) {
        this.progressService = progressService;
        this.progressMapper = progressMapper;
    }

    @GetMapping
    public ResponseEntity<?> getAllProgress() {
        return progressService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProgressById(@PathVariable Long id) {
        Optional<Progress> progress = progressService.findById(id);
        return progress.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createProgress(@RequestBody ProgressRequestDto request) {
        return progressService.save(request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgress(@PathVariable Long id) {
        Optional<Progress> progress = progressService.findById(id);
        if (progress.isPresent()) {
            progressService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
