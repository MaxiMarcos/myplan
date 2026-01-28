package com.maximarcos.miplan.dto.plan;

import com.maximarcos.miplan.entity.Action;
import com.maximarcos.miplan.entity.Progress;
import com.maximarcos.miplan.entity.User;
import com.maximarcos.miplan.enums.Status;

import java.util.List;


public record PlanRequestDto(List<Action> action, List<Progress> progress, User user, Status status) {
}
