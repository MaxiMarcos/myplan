package com.maximarcos.miplan.controller;

import com.maximarcos.miplan.dto.comment.CommentResponseDto;
import com.maximarcos.miplan.dto.plan.PlanRequestDto;
import com.maximarcos.miplan.dto.post.PostRequestDto;
import com.maximarcos.miplan.dto.post.PostResponseDto;
import com.maximarcos.miplan.entity.Post;
import com.maximarcos.miplan.enums.Category;
import com.maximarcos.miplan.mapper.PostMapper;
import com.maximarcos.miplan.service.CommentService;
import com.maximarcos.miplan.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;

    public PostController(PostService postService, PostMapper postMapper) {
        this.postService = postService;
        this.postMapper = postMapper;
    }

    @GetMapping
    public List<PostResponseDto> getAllPosts() {
        return postMapper.toListResponseDto(postService.findAll());
    }

    @GetMapping("/categories")
    public Category[] getCategories() {
        return Category.values();
    }

    @GetMapping("/category/{category}")
    public List<PostResponseDto> findByCategory(@PathVariable Category category) {
        return postMapper.toListResponseDto(postService.findByCategory(category));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable Long id) {
        Optional<Post> post = postService.findById(id);
        return post
                .map(p -> ResponseEntity.ok(postMapper.toResponseDto(p)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostRequestDto request) {
        return postService.save(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable Long id, @RequestBody PostRequestDto request) {
        return postService.updateById(id, request);
    }

    @PatchMapping("/{id}/likes")
    public ResponseEntity<?> updateLikes(@PathVariable Long id, @RequestBody boolean like) {
        return postService.updateLikes(id, like);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        Optional<Post> post = postService.findById(id);
        if (post.isPresent()) {
            postService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
