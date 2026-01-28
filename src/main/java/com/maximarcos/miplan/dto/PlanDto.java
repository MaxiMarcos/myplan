package com.maximarcos.miplan.dto;

import com.maximarcos.miplan.entity.Action;
import com.maximarcos.miplan.entity.Comment;
import com.maximarcos.miplan.entity.Progress;
import com.maximarcos.miplan.enums.Status;

import java.util.List;


public record PlanDto(List<Action> action, List<Progress> progress, List<Comment> comment, Status status) {
}
