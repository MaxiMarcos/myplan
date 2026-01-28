package com.maximarcos.miplan.service;

import com.maximarcos.miplan.entity.Plan;
import com.maximarcos.miplan.enums.Visibility;
import com.maximarcos.miplan.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanService {

    @Autowired
    private PlanRepository planRepository;

    public List<Plan> findAll() {
        return planRepository.findAll();
    }

    public Optional<Plan> findById(Long id) {
        return planRepository.findById(id);
    }

    public Optional<Plan> findByVisibilityAndUser(Visibility visibility, Long userId) {return planRepository.findByVisibilityAndUserId(Visibility.PUBLIC, userId); }

    public Plan save(Plan plan) {
        return planRepository.save(plan);
    }

    public void deleteById(Long id) {
        planRepository.deleteById(id);
    }
}
