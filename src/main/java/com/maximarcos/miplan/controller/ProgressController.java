package com.maximarcos.miplan.controller;

import com.maximarcos.miplan.dto.ProgressDto;
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
    public List<ProgressDto> getAllProgress() {
        return progressMapper.toListDto(progressService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Progress> getProgressById(@PathVariable Long id) {
        Optional<Progress> progress = progressService.findById(id);
        return progress.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Progress createProgress(@RequestBody Progress progress) {
        return progressService.save(progress);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Progress> updateProgress(@PathVariable Long id, @RequestBody ProgressDto progressDto) {
        Optional<Progress> progressOptional = progressService.findById(id);
        if (progressOptional.isPresent()) {
            Progress existingProgress = progressOptional.get();
            Progress updatedProgress = progressMapper.toEntity(progressDto);
            updatedProgress.setId(existingProgress.getId()); // Ensure the ID remains the same
            return ResponseEntity.ok(progressService.save(updatedProgress));
        } else {
            return ResponseEntity.notFound().build();
        }
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
