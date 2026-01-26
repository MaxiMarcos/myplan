package com.maximarcos.miplan.controller;

import com.maximarcos.miplan.dto.ActionDto;
import com.maximarcos.miplan.entity.Action;
import com.maximarcos.miplan.mapper.ActionMapper;
import com.maximarcos.miplan.service.ActionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/actions")
public class ActionController {

    private final ActionService actionService;
    private final ActionMapper actionMapper;

    public ActionController(ActionService actionService, ActionMapper actionMapper) {
        this.actionService = actionService;
        this.actionMapper = actionMapper;
    }

    @GetMapping
    public List<ActionDto> getAllActions() {
        return actionMapper.toListDto(actionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Action> getActionById(@PathVariable Long id) {
        Optional<Action> action = actionService.findById(id);
        return action.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Action createAction(@RequestBody Action action) {
        return actionService.save(action);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Action> updateAction(@PathVariable Long id, @RequestBody ActionDto actionDto) {
        Optional<Action> actionOptional = actionService.findById(id);
        if (actionOptional.isPresent()) {
            Action existingAction = actionOptional.get();
            Action updatedAction = actionMapper.toEntity(actionDto);
            updatedAction.setId(existingAction.getId()); // Ensure the ID remains the same
            return ResponseEntity.ok(actionService.save(updatedAction));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAction(@PathVariable Long id) {
        Optional<Action> action = actionService.findById(id);
        if (action.isPresent()) {
            actionService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
