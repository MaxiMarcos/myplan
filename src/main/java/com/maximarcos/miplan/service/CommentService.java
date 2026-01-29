package com.maximarcos.miplan.service;

import com.maximarcos.miplan.dto.comment.CommentRequestDto;
import com.maximarcos.miplan.dto.comment.CommentResponseDto;
import com.maximarcos.miplan.entity.Comment;
import com.maximarcos.miplan.mapper.CommentMapper;
import com.maximarcos.miplan.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public CommentService(CommentRepository commentRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    public ResponseEntity<?> findAll() {

        return ResponseEntity.ok(commentMapper.toListDto(commentRepository.findAll()));
    }

    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }

    public ResponseEntity<?> save(CommentRequestDto request) {

        Comment comment = commentMapper.toEntity(request);
        commentRepository.save(comment);
        return ResponseEntity.ok(commentMapper.toResponse(comment));
    }

    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }
}
