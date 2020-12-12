package ru.inno.projects.models;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long eventId;

    private String eventName;

    private LocalDateTime createDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "events_users", joinColumns = @JoinColumn(name = "eventId"),
            inverseJoinColumns = @JoinColumn(name = "userId"))

    private Set<User> users;
}
