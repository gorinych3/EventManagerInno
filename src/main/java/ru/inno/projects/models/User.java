package ru.inno.projects.models;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name="event_user")
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long Id;
    private String username;
}
