package com.maximarcos.miplan.service;

import com.maximarcos.miplan.dto.plan.PlanRequestDto;
import com.maximarcos.miplan.dto.plan.PlanResponseDto;
import com.maximarcos.miplan.entity.Action;
import com.maximarcos.miplan.entity.Plan;
import com.maximarcos.miplan.entity.Progress;
import com.maximarcos.miplan.entity.User;
import com.maximarcos.miplan.mapper.PlanMapper;
import com.maximarcos.miplan.repository.ActionRepository;
import com.maximarcos.miplan.repository.PlanRepository;
import com.maximarcos.miplan.repository.ProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class PlanService {

    private final PlanMapper planMapper;
    private final PlanRepository planRepository;
    private final ActionRepository actionRepository;
    private final ProgressRepository progressRepository;

    public PlanService(PlanMapper planMapper, PlanRepository planRepository, ActionRepository actionRepository, ProgressRepository progressRepository) {
        this.planMapper = planMapper;
        this.planRepository = planRepository;
        this.actionRepository = actionRepository;
        this.progressRepository = progressRepository;
    }

    public List<Plan> findAll() {
        return planRepository.findAll();
    }

    public Optional<Plan> findById(Long id) {
        return planRepository.findById(id);
    }

    public ResponseEntity<?> save(PlanRequestDto request) {

        Plan plan = planMapper.toEntity(request);
        planRepository.save(plan);
        return ResponseEntity.ok(planMapper.toResponseDto(plan));
    }

    public void deleteById(Long id) {
        planRepository.deleteById(id);
    }

    public ResponseEntity<?> updateById(Long id, PlanRequestDto request) {

        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));


        plan.setTitle(request.title());
        plan.setDescription(request.description());
        plan.setStatus(request.status());

        if (request.actionId() != null) {
            List<Action> actions = actionRepository.findAllById(request.actionId());
            plan.setAction(actions);
        }

        if (request.progressId() != null) {
            List<Progress> progress = progressRepository.findAllById(request.progressId());
            plan.setProgress(progress);
        }

        Plan updatedPlan = planRepository.save(plan);

        return ResponseEntity.ok(planMapper.toResponseDto(updatedPlan));
    }



}
