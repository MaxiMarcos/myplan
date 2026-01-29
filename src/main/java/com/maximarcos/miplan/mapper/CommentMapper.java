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
        return new CommentResponseDto(comment.getText(), comment.getUser().getId(), comment.getCreatedAt());
    }

    public Comment toEntity(CommentRequestDto commentRequestDto) {
        return Comment.builder()
                .text(commentRequestDto.text())
                .createdAt(commentRequestDto.createdAt())
                .build();
    }

    public List<CommentResponseDto> toListDto(List<Comment> comments) {
        return comments.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}