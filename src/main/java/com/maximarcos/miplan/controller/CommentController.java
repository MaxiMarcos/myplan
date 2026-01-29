package com.maximarcos.miplan.controller;

import com.maximarcos.miplan.dto.comment.CommentRequestDto;
import com.maximarcos.miplan.dto.comment.CommentResponseDto;
import com.maximarcos.miplan.entity.Comment;
import com.maximarcos.miplan.mapper.CommentMapper;
import com.maximarcos.miplan.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService, CommentMapper commentMapper) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<?> getAllComments() {
        return commentService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long id) {
        Optional<Comment> comment = commentService.findById(id);
        return comment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createComment(@RequestBody CommentRequestDto request) {
        return commentService.save(request);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        Optional<Comment> comment = commentService.findById(id);
        if (comment.isPresent()) {
            commentService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
