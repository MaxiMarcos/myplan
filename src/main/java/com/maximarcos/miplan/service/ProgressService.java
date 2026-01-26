package com.maximarcos.miplan.service;

import com.maximarcos.miplan.entity.Progress;
import com.maximarcos.miplan.repository.ProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProgressService {

    @Autowired
    private ProgressRepository progressRepository;

    public List<Progress> findAll() {
        return progressRepository.findAll();
    }

    public Optional<Progress> findById(Long id) {
        return progressRepository.findById(id);
    }

    public Progress save(Progress progress) {
        return progressRepository.save(progress);
    }

    public void deleteById(Long id) {
        progressRepository.deleteById(id);
    }
}
