package ru.inno.projects.models;


import lombok.Data;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long userId;
    private String username;
    private String password;
    private boolean active;
    private String email;
    private String phoneNumber;

    @ManyToMany(mappedBy = "users")
    private Set<Event> events;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
}
