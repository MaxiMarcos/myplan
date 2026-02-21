package com.maximarcos.miplan.repository;

import com.maximarcos.miplan.entity.Post;
import com.maximarcos.miplan.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    public List<Post> findByCategory (Category category);

}
