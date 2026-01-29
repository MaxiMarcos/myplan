package com.maximarcos.miplan.controller;

import com.maximarcos.miplan.dto.plan.PlanRequestDto;
import com.maximarcos.miplan.dto.plan.PlanResponseDto;
import com.maximarcos.miplan.entity.Plan;
import com.maximarcos.miplan.mapper.PlanMapper;
import com.maximarcos.miplan.service.PlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/plans")
public class PlanController {

    private final PlanService planService;
    private final PlanMapper planMapper;

    public PlanController(PlanService planService, PlanMapper planMapper) {
        this.planService = planService;
        this.planMapper = planMapper;
    }

    @GetMapping
    public List<PlanResponseDto> getAllPlans() {
        return planMapper.toListResponseDto(planService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plan> getPlanById(@PathVariable Long id) {
        Optional<Plan> plan = planService.findById(id);
        return plan.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createPlan(@RequestBody PlanRequestDto request) {
        return planService.save(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePlan(@PathVariable Long id, @RequestBody PlanRequestDto request) {
        return planService.updateById(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlan(@PathVariable Long id) {
        Optional<Plan> plan = planService.findById(id);
        if (plan.isPresent()) {
            planService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
