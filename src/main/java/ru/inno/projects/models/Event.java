package ru.inno.projects.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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

    public Event() {
    }

    public Event(String eventName, LocalDateTime createDate) {
        this.eventName = eventName;
        this.createDate = createDate;
    }

    @ManyToMany
    @JoinTable(
            name = "events_users",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "event_id")}
    )
    private Set<User> users = new HashSet<>();

    // Много приглашений может соответствовать одному событию
//    @OneToMany(mappedBy="event")
//    private Set<Invitation> invitations;

    @OneToMany(mappedBy = "event")
    //@OneToMany(mappedBy="event")
    private List<Member> members;

    public long getEventId() {
        return eventId;
    }
}
