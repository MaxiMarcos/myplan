package com.maximarcos.miplan.mapper;

import com.maximarcos.miplan.dto.post.PostRequestDto;
import com.maximarcos.miplan.dto.post.PostResponseDto;
import com.maximarcos.miplan.entity.Post;
import com.maximarcos.miplan.enums.Category;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostMapper {

    public PostResponseDto toResponseDto(Post post) {
        Long authorId = post.getAuthor() != null ? post.getAuthor().getId() : null;
        Category category = post.getCategory();
        return new PostResponseDto(
                post.getId(),
                post.getTitle() != null ? post.getTitle() : "",
                post.getLike(),
                post.getContent() != null ? post.getContent() : "",
                post.getCreatedAt(),
                post.getUpdatedAt(),
                authorId,
                category
        );
    }

    public Post toEntity(PostRequestDto request) {
        return Post.builder()
                .title(request.title())
                .like(request.like())
                .content(request.content())
                .category(request.category())
                .build();
    }

    public List<PostResponseDto> toListResponseDto(List<Post> posts) {
        return posts.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }
}
