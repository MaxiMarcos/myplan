package com.maximarcos.miplan.entity;

import com.maximarcos.miplan.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.util.Enumeration;
import java.util.List;

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
    @OneToMany(mappedBy="plan")
    private List<Action> action;
    @OneToMany(mappedBy="plan")
    private List<Progress> progress;
    @ManyToOne
    private User user;
    @Enumerated(EnumType.STRING)
    private Status status;

}
