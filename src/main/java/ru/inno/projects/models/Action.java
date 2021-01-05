package ru.inno.projects.models;

import lombok.Data;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "actions")
public class Action {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long actionId;

    private String actionName;

    private String description;

    private int teams;

    private int playersOnTeam;

    @OneToOne(mappedBy = "action")
    private Event event;
}
