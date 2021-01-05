package ru.inno.projects.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "events")
@EqualsAndHashCode(exclude = {"users"})
public class Event {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long eventId;

    private String eventName;

    private LocalDateTime createDate;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "events_users",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "event_id")}
    )
    private Set<User> users = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "action_id")
    private Action action;

    //пробуем просто one2many без всяких уточнений
    @OneToMany/*(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})*/
    @JoinColumn(name = "event_id")
    private Set<Team> teams = new HashSet<>();

//    @OneToMany
//    @JoinColumn(name = "event_id")
//    private Set<PlayAction> playActions = new HashSet<>();
}
