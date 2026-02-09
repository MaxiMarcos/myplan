package com.maximarcos.miplan.service;

import com.maximarcos.miplan.dto.post.PostRequestDto;
import com.maximarcos.miplan.dto.post.PostResponseDto;
import com.maximarcos.miplan.entity.Post;
import com.maximarcos.miplan.entity.User;
import com.maximarcos.miplan.enums.Category;
import com.maximarcos.miplan.mapper.PostMapper;
import com.maximarcos.miplan.repository.PostRepository;
import com.maximarcos.miplan.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, PostMapper postMapper, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.userRepository = userRepository;
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    public ResponseEntity<PostResponseDto> save(PostRequestDto request) {
        Post post = postMapper.toEntity(request);
        if (request.authorId() != null) {
            userRepository.findById(request.authorId())
                    .ifPresent(post::setAuthor);
        }
        postRepository.save(post);
        return ResponseEntity.ok(postMapper.toResponseDto(post));
    }

    public ResponseEntity<PostResponseDto> updateById(Long id, PostRequestDto request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        post.setTitle(request.title());
        post.setContent(request.content());
        post.setCategory(request.category());

        if (request.authorId() != null) {
            userRepository.findById(request.authorId())
                    .ifPresent(post::setAuthor);
        }

        Post updatedPost = postRepository.save(post);
        return ResponseEntity.ok(postMapper.toResponseDto(updatedPost));
    }

    public ResponseEntity<?> updateLikes(Long id, boolean like) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (like) {
            post.setLike(post.getLike() + 1);
        } else {
            post.setLike(Math.max(0, post.getLike() - 1));
        }
        post = postRepository.save(post);
        return ResponseEntity.ok(postMapper.toResponseDto(post));
    }

    public void deleteById(Long id) {
        postRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        postRepository.deleteById(id);
    }

    public List<Post> findByCategory(Category category) {

        List<Post> posts = postRepository.findByCategory(category);
        return posts;
    }

}
