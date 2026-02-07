package com.maximarcos.miplan.repository;

import com.maximarcos.miplan.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPost_IdOrderByCreatedAtAsc(Long postId);
}
