package com.maximarcos.miplan.controller;

import com.maximarcos.miplan.dto.PlanDto;
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
    public List<PlanDto> getAllPlans() {
        return planMapper.toListDto(planService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plan> getPlanById(@PathVariable Long id) {
        Optional<Plan> plan = planService.findById(id);
        return plan.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Plan createPlan(@RequestBody Plan plan) {
        return planService.save(plan);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Plan> updatePlan(@PathVariable Long id, @RequestBody PlanDto planDto) {
        Optional<Plan> planOptional = planService.findById(id);
        if (planOptional.isPresent()) {
            Plan existingPlan = planOptional.get();
            Plan updatedPlan = planMapper.toEntity(planDto);
            updatedPlan.setId(existingPlan.getId()); // Ensure the ID remains the same
            return ResponseEntity.ok(planService.save(updatedPlan));
        } else {
            return ResponseEntity.notFound().build();
        }
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
