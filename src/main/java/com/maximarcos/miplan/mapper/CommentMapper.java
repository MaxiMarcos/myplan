package com.maximarcos.miplan.mapper;

import com.maximarcos.miplan.dto.comment.CommentDto;
import com.maximarcos.miplan.dto.comment.CommentResponseDto;
import com.maximarcos.miplan.entity.Comment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentMapper {

    public CommentResponseDto toDto(Comment comment) {
        return new CommentDto(comment.getText(), comment.getUser(), comment.getCreatedAt());
    }

    public Comment toEntity(CommentDto commentDto) {
        return Comment.builder()
                .text(commentDto.text())
                .user(commentDto.user())
                .createdAt(commentDto.createdAt())
                .build();
    }

    public List<CommentResponseDto> toListDto(List<Comment> comments) {
        return comments.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}