package com.maximarcos.miplan.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;

    private String password;

    @OneToMany(mappedBy = "user")
    private List<Plan> plan;
    @OneToMany(mappedBy = "user")
    private List<Comment> comment;
    @OneToMany(mappedBy = "user")
    private List<Action> action;
    @OneToMany(mappedBy = "author")
    private List<Post> posts;
}
