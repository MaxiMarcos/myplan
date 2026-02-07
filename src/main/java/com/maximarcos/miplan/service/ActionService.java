package com.maximarcos.miplan.service;

import com.maximarcos.miplan.dto.action.ActionRequestDto;
import com.maximarcos.miplan.dto.action.ActionResponseDto;
import com.maximarcos.miplan.entity.Action;
import com.maximarcos.miplan.mapper.ActionMapper;
import com.maximarcos.miplan.repository.ActionRepository;
import com.maximarcos.miplan.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ActionService {


    private final ActionRepository actionRepository;
    private final ActionMapper actionMapper;
    private final PlanRepository planRepository;

    public ActionService(ActionRepository actionRepository, ActionMapper actionMapper, PlanRepository planRepository) {
        this.actionRepository = actionRepository;
        this.actionMapper = actionMapper;
        this.planRepository = planRepository;
    }

    public List<Action> findAll() {
        return actionRepository.findAll();
    }

    public Optional<Action> findById(Long id) {
        return actionRepository.findById(id);
    }

    public ResponseEntity<ActionResponseDto> save(ActionRequestDto request) {
        Action action = actionMapper.toEntity(request);
        if (request.planId() != null) {
            planRepository.findById(request.planId()).ifPresent(action::setPlan);
        }
        actionRepository.save(action);
        return ResponseEntity.ok(actionMapper.toResponse(action));
    }

    public void deleteById(Long id) {

        Action action = this.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (action != null) {
            actionRepository.deleteById(id);
        }
    }

    public ResponseEntity<ActionResponseDto> updateAction(Long id, ActionRequestDto request) {

        Action action = this.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        action.setTitle(request.title());
        action.setDescription(request.description());

        Action savedAction = actionRepository.save(action);

        return ResponseEntity.ok(actionMapper.toResponse(savedAction));
    }
}
