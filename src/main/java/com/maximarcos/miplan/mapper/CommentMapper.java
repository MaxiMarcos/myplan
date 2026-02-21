package com.maximarcos.miplan.mapper;

import com.maximarcos.miplan.dto.comment.CommentRequestDto;
import com.maximarcos.miplan.dto.comment.CommentResponseDto;
import com.maximarcos.miplan.entity.Comment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentMapper {

    public CommentResponseDto toResponse(Comment comment) {
        Long userId = comment.getUser() != null ? comment.getUser().getId() : null;
        Long postId = comment.getPost() != null ? comment.getPost().getId() : null;
        return new CommentResponseDto(
                comment.getId(),
                comment.getText(),
                comment.getCreatedAt(),
                userId,
                postId
        );
    }

    public Comment toEntity(CommentRequestDto request) {
        return Comment.builder()
                .text(request.text())
                .build();
    }

    public List<CommentResponseDto> toListDto(List<Comment> comments) {
        return comments.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
