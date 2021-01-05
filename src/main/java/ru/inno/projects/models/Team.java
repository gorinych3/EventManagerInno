package ru.inno.projects.models;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long teamId;

    private String teamName;

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "team_id")
    private Set<User> teamUsers = new HashSet<>();

//    @OneToMany
//    @JoinColumn(name = "team_id")
//    private Set<PlayAction> playActions = new HashSet<>();

}
