package ru.inno.projects.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "events", schema = "PUBLIC")
public class Event {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long eventId;

    private String eventName;

    private LocalDateTime createDate;

    @ManyToMany(fetch = FetchType.LAZY, cascade =
            {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "events_users",
            joinColumns = {@JoinColumn(name = "event_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<User> users = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "action_id")
    private Action action;

    @ManyToOne
    @JoinColumn(name = "owner_user_id", nullable = false)
    private User ownerUser;

    @OneToMany(mappedBy = "event")
    private Set<Invitation> invitations;

    public Event(String eventName, LocalDateTime createDate) {
        this.eventName = eventName;
        this.createDate = createDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return eventId == event.eventId
                && Objects.equals(eventName, event.eventName)
                && Objects.equals(createDate, event.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, eventName, createDate);
    }

    public long getEventId() {
        return eventId;
    }
}
