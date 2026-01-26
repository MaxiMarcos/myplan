package com.maximarcos.miplan.dto;

import com.maximarcos.miplan.entity.Action;
import com.maximarcos.miplan.entity.Comment;
import com.maximarcos.miplan.entity.Progress;
import com.maximarcos.miplan.enums.Status;


public record PlanDto(Action action, Progress progress, Comment comment, Status status) {
}
