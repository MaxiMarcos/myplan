package com.maximarcos.miplan.controller;

import com.maximarcos.miplan.dto.action.ActionRequestDto;
import com.maximarcos.miplan.dto.action.ActionResponseDto;
import com.maximarcos.miplan.entity.Action;
import com.maximarcos.miplan.mapper.ActionMapper;
import com.maximarcos.miplan.service.ActionService;
import com.maximarcos.miplan.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/actions")
public class ActionController {

    private final ActionService actionService;
    private final ActionMapper actionMapper;

    public ActionController(ActionService actionService, ActionMapper actionMapper, UserService userService) {
        this.actionService = actionService;
        this.actionMapper = actionMapper;
    }

    @GetMapping
    public List<ActionResponseDto> getAllActions() {
        return actionMapper.toListDto(actionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Action> getActionById(@PathVariable Long id) {
        Optional<Action> action = actionService.findById(id);
        return action.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ActionResponseDto> createAction(@RequestBody ActionRequestDto request) {

        return actionService.save(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActionResponseDto> updateAction(@PathVariable Long id, @RequestBody ActionRequestDto actionRequestDto) {

        return actionService.updateAction(id, actionRequestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteAction(@PathVariable Long id) {

        actionService.deleteById(id);
    }
}
