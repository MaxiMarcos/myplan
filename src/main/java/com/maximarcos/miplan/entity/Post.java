package com.maximarcos.miplan.entity;

import com.maximarcos.miplan.enums.Category;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(name = "`like`")
    private int like;
    @Column(length = 5000)
    private String content;

    @Column(name = "created_on")
    private LocalDateTime createdAt;

    @Column(name = "updated_on")
    private LocalDateTime updatedAt;

    @ManyToOne
    private User author;

    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
