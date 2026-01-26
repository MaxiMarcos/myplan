package com.maximarcos.miplan.service;

import com.maximarcos.miplan.entity.Action;
import com.maximarcos.miplan.repository.ActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActionService {

    @Autowired
    private ActionRepository actionRepository;

    public List<Action> findAll() {
        return actionRepository.findAll();
    }

    public Optional<Action> findById(Long id) {
        return actionRepository.findById(id);
    }

    public Action save(Action action) {
        return actionRepository.save(action);
    }

    public void deleteById(Long id) {
        actionRepository.deleteById(id);
    }
}
