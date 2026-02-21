package com.maximarcos.miplan.service;

import com.maximarcos.miplan.dto.comment.CommentRequestDto;
import com.maximarcos.miplan.dto.comment.CommentResponseDto;
import com.maximarcos.miplan.entity.Comment;
import com.maximarcos.miplan.entity.Post;
import com.maximarcos.miplan.entity.User;
import com.maximarcos.miplan.mapper.CommentMapper;
import com.maximarcos.miplan.repository.CommentRepository;
import com.maximarcos.miplan.repository.PostRepository;
import com.maximarcos.miplan.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, CommentMapper commentMapper,
                          UserRepository userRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public List<CommentResponseDto> findAll() {
        return commentMapper.toListDto(commentRepository.findAll());
    }

    public List<CommentResponseDto> findByPostId(Long postId) {
        return commentMapper.toListDto(commentRepository.findByPost_IdOrderByCreatedAtAsc(postId));
    }

    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }

    public ResponseEntity<CommentResponseDto> save(CommentRequestDto request) {
        Comment comment = commentMapper.toEntity(request);
        if (request.userId() != null) {
            userRepository.findById(request.userId()).ifPresent(comment::setUser);
        }
        if (request.postId() != null) {
            postRepository.findById(request.postId()).ifPresent(comment::setPost);
        }
        commentRepository.save(comment);
        return ResponseEntity.ok(commentMapper.toResponse(comment));
    }

    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }
}
