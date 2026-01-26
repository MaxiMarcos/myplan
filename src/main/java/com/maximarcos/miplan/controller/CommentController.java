package com.maximarcos.miplan.controller;

import com.maximarcos.miplan.dto.CommentDto;
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
    private final CommentMapper commentMapper;

    public CommentController(CommentService commentService, CommentMapper commentMapper) {
        this.commentService = commentService;
        this.commentMapper = commentMapper;
    }

    @GetMapping
    public List<CommentDto> getAllComments() {
        return commentMapper.toListDto(commentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long id) {
        Optional<Comment> comment = commentService.findById(id);
        return comment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Comment createComment(@RequestBody Comment comment) {
        return commentService.save(comment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody CommentDto commentDto) {
        Optional<Comment> commentOptional = commentService.findById(id);
        if (commentOptional.isPresent()) {
            Comment existingComment = commentOptional.get();
            Comment updatedComment = commentMapper.toEntity(commentDto);
            // Assuming Comment entity has an ID field, which is not explicitly defined in the provided Comment.java
            // If Comment entity uses 'text' as ID, then: updatedComment.setText(existingComment.getText());
            // For now, we'll assume a 'setId' method exists or handle it based on actual ID field.
            // Since the original Comment.java had @Id on 'text', we'll use that.
            updatedComment.setText(existingComment.getText()); // Assuming 'text' is the ID
            return ResponseEntity.ok(commentService.save(updatedComment));
        } else {
            return ResponseEntity.notFound().build();
        }
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
