package com.maximarcos.miplan.entity;

import com.maximarcos.miplan.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.util.Enumeration;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToMany
    private Action action;
    @OneToMany
    private Progress progress;
    @OneToMany
    private Comment comment;
    private Status status;

}
